package qwbarch.pixelmon.pixeldex.command

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.TextFormatting
import qwbarch.pixelmon.pixeldex.MessageUtils
import qwbarch.pixelmon.pixeldex.ProgressChecker
import java.text.DecimalFormat

class CheckCommand : PDCommand("check", "/pd check") {

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if (sender is EntityPlayerMP) {
            if (args.isEmpty()) {
                MessageUtils.sendMessage(sender, "You completed " +
                        "${TextFormatting.AQUA}${DecimalFormat("#.##")
                                .format(ProgressChecker.checkProgress(sender))}" +
                        "${TextFormatting.WHITE}% of your pok\u00e9dex!")
            } else sendUsage(sender)
        } else MessageUtils.sendNonPlayerMessage(sender)
    }
}