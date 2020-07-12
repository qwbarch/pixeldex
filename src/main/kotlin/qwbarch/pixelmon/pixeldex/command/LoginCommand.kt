package qwbarch.pixelmon.pixeldex.command

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import qwbarch.pixelmon.Pixeldex
import qwbarch.pixelmon.pixeldex.MessageUtils

class LoginCommand : PDCommand("login", "/pd login show|hide") {

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if (sender is EntityPlayerMP) {
            if (args.size == 1) {
                when (args[0]) {
                    "show" -> {
                        Pixeldex.INSTANCE.claimController.setLoginNotified(sender, true)
                        MessageUtils.sendMessage(sender, "Login notifications enabled.")
                    }
                    "hide" -> {
                        Pixeldex.INSTANCE.claimController.setLoginNotified(sender, false)
                        MessageUtils.sendMessage(sender, "Login notifications disabled.")
                    }
                    else -> sendUsage(sender)
                }
            } else sendUsage(sender)
        } else MessageUtils.sendNonPlayerMessage(sender)
    }
}