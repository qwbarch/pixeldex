package qwbarch.pixelmon.pixeldex.command

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.server.command.CommandTreeBase
import qwbarch.pixelmon.Pixeldex

abstract class PDCommand(private val name: String,
                         private val usage: String,
                         private val permLevel: Int = 1) : CommandBase() {

    fun sendUsage(sender: ICommandSender) {
        sender.sendMessage(TextComponentString(Pixeldex.INSTANCE.configHandler.invalidCommandUsageMessage
                .replace("@usage", getUsage(sender))))
    }

    override fun getName(): String = name

    override fun getUsage(sender: ICommandSender): String = usage

    override fun checkPermission(server: MinecraftServer, sender: ICommandSender): Boolean {
        return sender.canUseCommand(permLevel,
                "${Pixeldex.MOD_ID}." + (if (permLevel == 1) "command" else "admin") + ".$name")
    }
}

class BaseCommand : CommandTreeBase() {

    private val aliases = arrayListOf(Pixeldex.INSTANCE.configHandler.commandAlias)

    init {
        addSubcommand(CheckCommand())
        addSubcommand(ClaimCommand())
        addSubcommand(CatchCommand())
        addSubcommand(LoginCommand())
        addSubcommand(RewardsCommand())
    }

    override fun getName(): String = Pixeldex.MOD_ID

    override fun getUsage(sender: ICommandSender): String = "/${Pixeldex.MOD_ID}"

    override fun checkPermission(server: MinecraftServer, sender: ICommandSender): Boolean {
        return sender.canUseCommand(1, "${Pixeldex.MOD_ID}.command.base")
    }

    override fun getAliases(): List<String> = aliases
}