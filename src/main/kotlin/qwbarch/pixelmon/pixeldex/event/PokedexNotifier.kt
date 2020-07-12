package qwbarch.pixelmon.pixeldex.event

import com.pixelmonmod.pixelmon.api.events.PokedexEvent
import com.pixelmonmod.pixelmon.pokedex.EnumPokedexRegisterStatus
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import qwbarch.pixelmon.Pixeldex
import qwbarch.pixelmon.pixeldex.MessageUtils
import qwbarch.pixelmon.pixeldex.ProgressChecker

object PokedexNotifier {

    @SubscribeEvent
    @JvmStatic
    fun onPokedex(event: PokedexEvent) {
        val player = Pixeldex.INSTANCE.server.playerList.getPlayerByUUID(event.uuid)
        if (event.isCausedByCapture && event.oldStatus != EnumPokedexRegisterStatus.caught &&
                event.newStatus == EnumPokedexRegisterStatus.caught &&
                ProgressChecker.hasUnclaimedRewards(player)) {
            MessageUtils.sendUnclaimedRewardsMessage(player)
        }
    }
}