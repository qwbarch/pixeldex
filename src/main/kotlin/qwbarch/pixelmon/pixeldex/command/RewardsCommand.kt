package qwbarch.pixelmon.pixeldex.command

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import qwbarch.pixelmon.Pixeldex
import qwbarch.pixelmon.pixeldex.MessageUtils
import qwbarch.pixelmon.pixeldex.reward.RewardLevel

class RewardsCommand : PDCommand("rewards",
        "/${Pixeldex.INSTANCE.configHandler.commandAlias} rewards 10|20|30|40|50|60|70|80|90|100") {

    private fun sendDescriptionMessage(sender: ICommandSender, level: RewardLevel) {
        MessageUtils.sendMessage(sender, Pixeldex.INSTANCE.configHandler.rewardDescriptionMessage
                .replace("@rewardLevel", level.value.toString())
                .replace("@rewardDescription", Pixeldex.INSTANCE.rewardPresenter.getDescription(level))
        )
    }

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            if (sender is EntityPlayerMP) {
                var level = Pixeldex.INSTANCE.claimController.getClaimedLevel(sender)
                if (level != RewardLevel.HUNDRED) level = level.next()
                sendDescriptionMessage(sender, level)
            } else MessageUtils.sendNonPlayerMessage(sender)
        } else if (args.size == 1) {
            when (val rewardLevel = args[0].toInt()) {
                10, 20, 30, 40, 50, 60, 70, 80, 90, 100 -> {
                    sendDescriptionMessage(sender, RewardLevel.parse(rewardLevel.toByte()))
                }
                else -> sendUsage(sender)
            }
        } else sendUsage(sender)
    }
}