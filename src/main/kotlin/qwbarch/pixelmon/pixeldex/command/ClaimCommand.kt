package qwbarch.pixelmon.pixeldex.command

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import qwbarch.pixelmon.Pixeldex
import qwbarch.pixelmon.pixeldex.MessageUtils

class ClaimCommand : PDCommand("claim", "/${Pixeldex.INSTANCE.configHandler.commandAlias} claim") {

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if (sender is EntityPlayerMP) {
            if (args.isEmpty()) {
                if (!Pixeldex.INSTANCE.rewardPresenter.reward(sender)) {
                    MessageUtils.sendMessage(sender, Pixeldex.INSTANCE.configHandler.noClaimableRewardsMessage)
                }
            } else sendUsage(sender)
        } else MessageUtils.sendNonPlayerMessage(sender)
    }
}