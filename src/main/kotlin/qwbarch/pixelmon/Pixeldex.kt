package qwbarch.pixelmon

import com.pixelmonmod.pixelmon.Pixelmon
import net.minecraft.server.MinecraftServer
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLServerStartingEvent
import org.apache.logging.log4j.Logger
import qwbarch.pixelmon.pixeldex.claim.ClaimController
import qwbarch.pixelmon.pixeldex.claim.storage.capability.CapabilityClaimController
import qwbarch.pixelmon.pixeldex.command.BaseCommand
import qwbarch.pixelmon.pixeldex.config.ConfigHandler
import qwbarch.pixelmon.pixeldex.config.IniConfigHandler
import qwbarch.pixelmon.pixeldex.event.PokedexNotifier
import qwbarch.pixelmon.pixeldex.reward.RewardPresenter
import java.io.File

@Mod(modid = Pixeldex.MOD_ID, name = Pixeldex.MOD_NAME, version = Pixeldex.VERSION,
        dependencies = "required-after:pixelmon", acceptableRemoteVersions = "*")
class Pixeldex {

    lateinit var logger: Logger private set
    lateinit var server: MinecraftServer private set

    lateinit var configHandler: ConfigHandler private set
    lateinit var claimController: ClaimController private set
    lateinit var rewardPresenter: RewardPresenter private set

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        logger = event.modLog!!
        logger.info("Loading v$VERSION")
        configHandler = IniConfigHandler(File(event.modConfigurationDirectory.toString() + "/$MOD_ID.ini"))
        claimController = CapabilityClaimController()
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        rewardPresenter = RewardPresenter(configHandler)
        Pixelmon.EVENT_BUS.register(PokedexNotifier::class.java)
    }

    @Mod.EventHandler
    fun serverStarting(event: FMLServerStartingEvent) {
        server = event.server
        event.registerServerCommand(BaseCommand())
    }

    companion object {
        const val MOD_ID = "pixeldex"
        const val MOD_NAME = "Pixeldex"
        const val VERSION = "1.0"

        @Mod.Instance(MOD_ID)
        lateinit var INSTANCE: Pixeldex
            private set
    }
}