package qwbarch.pixelmon.pixeldex

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.text.TextComponentString
import qwbarch.pixelmon.Pixeldex
import java.text.DecimalFormat

object MessageUtils {

    private val decimalFormat = DecimalFormat("#.##")

    fun sendProgressMessage(sender: ICommandSender, player: EntityPlayerMP) {
        sendMessage(sender,
                (if (sender === player) Pixeldex.INSTANCE.configHandler.selfPlayerProgressMessage
                else Pixeldex.INSTANCE.configHandler.otherPlayerProgressMessage
                        .replace("@playerName", player.name)
                        ).replace("@progress", decimalFormat.format(ProgressChecker.checkProgress(player)))
        )
    }

    fun sendNonPlayerMessage(sender: ICommandSender) {
        sender.sendMessage(TextComponentString("This command is for players only."))
    }

    fun sendMessage(sender: ICommandSender, message: String) {
        sender.sendMessage(TextComponentString("${Pixeldex.INSTANCE.configHandler.messagePrefix} $message"))
    }

    fun sendPlayerNotAvailable(sender: ICommandSender, player: String) {
        sendMessage(sender, Pixeldex.INSTANCE.configHandler.playerUnavailableMessage
                .replace("@playerName", player))
    }

    fun sendUnclaimedRewardsMessage(player: EntityPlayerMP) {
        sendMessage(player, Pixeldex.INSTANCE.configHandler.unclaimedRewardsMessage
                .replace("@alias", Pixeldex.INSTANCE.configHandler.commandAlias))
    }
}