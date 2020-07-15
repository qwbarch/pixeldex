package qwbarch.pixelmon.pixeldex.claim.storage.capability

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import qwbarch.pixelmon.pixeldex.reward.RewardLevel

class ClaimStorage : Capability.IStorage<ClaimData> {

    override fun readNBT(capability: Capability<ClaimData>?, instance: ClaimData?, side: EnumFacing?, nbt: NBTBase?) {
        instance!!.claimedLevel = RewardLevel.parse((nbt as NBTTagCompound).getByte("level"))
        instance.isLoginNotified = nbt.getBoolean("login")
        instance.isCatchNotified = nbt.getBoolean("catch")
    }

    override fun writeNBT(capability: Capability<ClaimData>?, instance: ClaimData?, side: EnumFacing?): NBTBase? {
        val nbt = NBTTagCompound()
        nbt.setByte("level", instance!!.claimedLevel.value)
        nbt.setBoolean("login", instance.isLoginNotified)
        nbt.setBoolean("catch", instance.isCatchNotified)
        return nbt
    }
}