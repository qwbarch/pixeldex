package qwbarch.pixelmon.pixeldex.event

import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent
import qwbarch.pixelmon.Pixeldex
import qwbarch.pixelmon.pixeldex.MessageUtils
import qwbarch.pixelmon.pixeldex.ProgressChecker

@Mod.EventBusSubscriber
object LoginNotifier {

    @SubscribeEvent
    @JvmStatic
    fun onLogin(event: PlayerEvent.PlayerLoggedInEvent) {
        val player = event.player
        if (player is EntityPlayerMP && Pixeldex.INSTANCE.claimController.isLoginNotified(player)
                && ProgressChecker.hasUnclaimedRewards(player)) {
            MessageUtils.sendUnclaimedRewardsMessage(player)
        }
    }
}