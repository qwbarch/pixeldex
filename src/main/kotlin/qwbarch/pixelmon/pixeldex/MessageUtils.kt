package qwbarch.pixelmon.pixeldex

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextFormatting
import qwbarch.pixelmon.Pixeldex

object MessageUtils {

    fun sendNonPlayerMessage(sender: ICommandSender) {
        sender.sendMessage(TextComponentString("This command is for players only."))
    }

    fun sendMessage(sender: ICommandSender, message: String) {
        sender.sendMessage(TextComponentString("[${TextFormatting.AQUA}${Pixeldex.MOD_NAME}" +
                "${TextFormatting.WHITE}] $message"))
    }

    fun sendUnclaimedRewardsMessage(player: EntityPlayerMP) {
        sendMessage(player, "You have unclaimed rewards! For a list of " +
                "sub-commands, type /${Pixeldex.MOD_ID}")
    }
}