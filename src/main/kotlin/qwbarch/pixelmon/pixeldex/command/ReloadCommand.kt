package qwbarch.pixelmon.pixeldex.command

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer
import qwbarch.pixelmon.Pixeldex
import qwbarch.pixelmon.pixeldex.MessageUtils

class ReloadCommand : PDCommand("reload", "/${Pixeldex.INSTANCE.configHandler.commandAlias} reload", 4) {

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if (sender !is EntityPlayer) {
            if (args.isEmpty()) {
                Pixeldex.INSTANCE.configHandler.reload()
                Pixeldex.INSTANCE.rewardPresenter.reload()
                Pixeldex.INSTANCE.logger.info("Finished reloading configuration.")
            } else sendUsage(sender)
        } else MessageUtils.sendMessage(sender, "Only the server can run this command.")
    }
}