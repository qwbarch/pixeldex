package qwbarch.pixelmon.pixeldex.command

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import qwbarch.pixelmon.Pixeldex
import qwbarch.pixelmon.pixeldex.MessageUtils

class CheckCommand : PDCommand("check", "/${Pixeldex.INSTANCE.configHandler.commandAlias} check") {

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if (sender is EntityPlayerMP) {
            when (args.size) {
                0 -> {
                    MessageUtils.sendProgressMessage(sender, sender)
                }
                1 -> {
                    val player = server.playerList.getPlayerByUsername(args[0])
                    if (player != null) MessageUtils.sendProgressMessage(sender, player)
                    else MessageUtils.sendPlayerNotAvailable(sender, args[0])
                }
                else -> sendUsage(sender)
            }
        } else MessageUtils.sendNonPlayerMessage(sender)
    }
}