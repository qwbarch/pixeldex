package qwbarch.pixelmon.pixeldex

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextFormatting
import qwbarch.pixelmon.Pixeldex
import java.text.DecimalFormat

object MessageUtils {

    private val decimalFormat = DecimalFormat("#.##")

    fun sendProgressMessage(sender: ICommandSender, player: EntityPlayerMP) {
        var playerName: String
        var pronoun: String
        if (sender === player) {
            playerName = "You"
            pronoun = "your"
        } else {
            playerName = player.name
            pronoun = "their"
        }
        sendMessage(sender, "$playerName completed " +
                "${TextFormatting.AQUA}${decimalFormat.format(ProgressChecker.checkProgress(player))}" +
                "${TextFormatting.WHITE}% of $pronoun pok\u00e9dex")
    }

    fun sendNonPlayerMessage(sender: ICommandSender) {
        sender.sendMessage(TextComponentString("This command is for players only."))
    }

    fun sendMessage(sender: ICommandSender, message: String) {
        sender.sendMessage(TextComponentString("[${TextFormatting.AQUA}${Pixeldex.MOD_NAME}" +
                "${TextFormatting.WHITE}] $message"))
    }

    fun sendPlayerNotAvailable(sender: ICommandSender, player: String) {
        sendMessage(sender, "The player $player is not available.")
    }

    fun sendUnclaimedRewardsMessage(player: EntityPlayerMP) {
        sendMessage(player, "You have unclaimed rewards! For a list of " +
                "sub-commands, type /${Pixeldex.MOD_ID}")
    }
}