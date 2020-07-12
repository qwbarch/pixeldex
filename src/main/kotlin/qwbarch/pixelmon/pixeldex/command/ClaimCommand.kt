package qwbarch.pixelmon.pixeldex.command

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import qwbarch.pixelmon.Pixeldex
import qwbarch.pixelmon.pixeldex.MessageUtils

class ClaimCommand : PDCommand("claim", "/pd claim") {

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if (sender is EntityPlayerMP) {
            if (args.isEmpty()) {
                if (!Pixeldex.INSTANCE.rewardPresenter.reward(sender)) {
                    MessageUtils.sendMessage(sender, "You do not have any available rewards to claim.")
                }
            } else sendUsage(sender)
        } else MessageUtils.sendNonPlayerMessage(sender)
    }
}