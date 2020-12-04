package qwbarch.pixelmon.pixeldex.reward

import com.pixelmonmod.pixelmon.Pixelmon
import com.pixelmonmod.pixelmon.api.drops.CustomDropScreen
import com.pixelmonmod.pixelmon.api.enums.EnumPositionTriState
import com.pixelmonmod.pixelmon.api.events.drops.CustomDropsEvent
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import qwbarch.pixelmon.Pixeldex
import qwbarch.pixelmon.pixeldex.MessageUtils
import qwbarch.pixelmon.pixeldex.ProgressChecker
import qwbarch.pixelmon.pixeldex.config.ConfigHandler
import qwbarch.pixelmon.pixeldex.config.ConfigParser
import java.util.*
import kotlin.collections.ArrayList

class RewardPresenter(configHandler: ConfigHandler) {

    private val rewards: MutableMap<RewardLevel, RewardData> = EnumMap(RewardLevel::class.java)

    init {
        RewardLevel.values().forEach {
            if (it != RewardLevel.ZERO) {
                Pixeldex.INSTANCE.logger.info("Loading ${it.value}% rewards")
                rewards[it] = RewardData(ConfigParser.parseItems(configHandler.getItemSeparator(),
                        configHandler.getQuantitySeparator(),
                        configHandler.getItemRewards(it)),
                        ConfigParser.parseCommands(configHandler.getCommandSeparator(), configHandler.getCommandRewards(it)),
                        configHandler.getDescription(it))
            }
        }
        Pixeldex.INSTANCE.logger.info("Finished loading rewards.")
    }

    fun reward(player: EntityPlayerMP): Boolean {
        val claimedLevel = Pixeldex.INSTANCE.claimController.getClaimedLevel(player)
        if (ProgressChecker.hasUnclaimedRewards(player)) {
            val rewardLevel = claimedLevel.next()
            val reward = rewards[rewardLevel]!!
            rewardItems(player, rewardLevel, reward)
            rewardCommands(player, reward)
            Pixeldex.INSTANCE.claimController.setClaimed(player, rewardLevel)
            MessageUtils.sendMessage(player, Pixeldex.INSTANCE.configHandler.claimRewardMessage)
            return true
        }
        return false
    }

    private fun rewardItems(player: EntityPlayerMP, rewardLevel: RewardLevel, reward: RewardData) {
        if (reward.items.isNotEmpty()) {
            val dropBuilder = CustomDropScreen.builder()
            Pixelmon.EVENT_BUS.register(object {

                private val rewards: MutableList<ItemStack> = ArrayList()
                private var count = 0

                init {
                    reward.items.forEach {
                        rewards.add(it.copy()) //Must copy so that original does not become empty
                    }
                    dropBuilder.setItems(rewards)
                }

                private fun addItemToInventory(player: EntityPlayerMP, item: ItemStack) {
                    if (!player.addItemStackToInventory(item)) dropItem(player, item)
                }

                private fun dropItem(player: EntityPlayerMP, item: ItemStack) {
                    player.dropItem(item, true)
                }

                @SubscribeEvent
                fun onClickDrop(event: CustomDropsEvent.ClickDrop) {
                    addItemToInventory(event.player, reward.items[event.index])
                    count++
                    if (count - 1 == reward.items.size) {
                        Pixelmon.EVENT_BUS.unregister(this)
                    }
                }

                @SubscribeEvent
                fun onClickButton(event: CustomDropsEvent.ClickButton) {
                    when (event.position) {
                        //Drop All
                        EnumPositionTriState.LEFT -> {
                            rewards.forEach { if (!it.isEmpty) dropItem(event.player, it) }
                        }
                        //Take All
                        EnumPositionTriState.RIGHT -> {
                            rewards.forEach { if (!it.isEmpty) addItemToInventory(event.player, it) }
                        }
                    }
                    Pixelmon.EVENT_BUS.unregister(this)
                }
            })
            dropBuilder.setTitle(TextComponentString("${rewardLevel.value}% completion rewards"))
            dropBuilder.setButtonText(EnumPositionTriState.LEFT, "Drop All")
            dropBuilder.setButtonText(EnumPositionTriState.RIGHT, "Take All")
            dropBuilder.sendTo(player)
        }
    }

    private fun rewardCommands(player: EntityPlayerMP, reward: RewardData) {
        if (reward.commands.isNotEmpty()) {
            val server = player.server
            reward.commands.forEach {
                Pixeldex.INSTANCE.logger.info("Executing command: $it")
                server.commandManager.executeCommand(server, it.replace("@player", player.name))
            }
        }
    }

    fun getDescription(level: RewardLevel): String = rewards[level]?.description!!
}