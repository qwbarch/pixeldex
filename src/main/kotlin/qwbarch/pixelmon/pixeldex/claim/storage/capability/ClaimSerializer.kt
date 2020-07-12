package qwbarch.pixelmon.pixeldex.claim.storage.capability

import net.minecraft.nbt.NBTBase
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable

class ClaimSerializer : ICapabilitySerializable<NBTBase> {

    private var instance = CLAIM_CAPABILITY.defaultInstance

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean =
            capability === CLAIM_CAPABILITY

    override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? =
            if (hasCapability(capability, facing)) CLAIM_CAPABILITY.cast<T>(instance) else null

    override fun deserializeNBT(nbt: NBTBase?) {
        CLAIM_CAPABILITY.storage.readNBT(CLAIM_CAPABILITY, instance, null, nbt)
    }

    override fun serializeNBT(): NBTBase =
            CLAIM_CAPABILITY.storage.writeNBT(CLAIM_CAPABILITY, instance, null)!!

    companion object {
        @CapabilityInject(ClaimData::class)
        lateinit var CLAIM_CAPABILITY: Capability<ClaimData>
            private set
    }
}