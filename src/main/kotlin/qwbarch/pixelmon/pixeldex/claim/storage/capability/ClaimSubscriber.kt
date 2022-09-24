package qwbarch.pixelmon.pixeldex.claim.storage.capability

import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import qwbarch.pixelmon.Pixeldex

@Mod.EventBusSubscriber
object ClaimSubscriber {

    private val CLAIM_RESOURCE = ResourceLocation(Pixeldex.MOD_ID, "claim")

    @SubscribeEvent
    @JvmStatic
    fun attachCapability(event: AttachCapabilitiesEvent<Entity>) {
        if (event.`object` is EntityPlayerMP) event.addCapability(CLAIM_RESOURCE, ClaimSerializer())
    }

    @SubscribeEvent
    @JvmStatic
    fun onPlayerClone(event: PlayerEvent.Clone) {
        //If player gets killed or transfer dimensions, tokens is copied from
        //old player to new player object
        val new = event.entityPlayer.getCapability(ClaimSerializer.CLAIM_CAPABILITY, null)!!
        val old = event.original.getCapability(ClaimSerializer.CLAIM_CAPABILITY, null)!!
        new.claimedLevel = old.claimedLevel
        new.isLoginNotified = old.isLoginNotified
    }
}