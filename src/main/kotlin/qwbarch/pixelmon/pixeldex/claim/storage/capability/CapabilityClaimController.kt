package qwbarch.pixelmon.pixeldex.claim.storage.capability

import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.common.capabilities.CapabilityManager
import qwbarch.pixelmon.pixeldex.claim.ClaimController
import qwbarch.pixelmon.pixeldex.reward.RewardLevel

class CapabilityClaimController : ClaimController {

    init {
        CapabilityManager.INSTANCE.register(ClaimData::class.java, ClaimStorage()) { ClaimData() }
    }

    override fun setClaimed(player: EntityPlayerMP, level: RewardLevel) {
        player.getCapability(ClaimSerializer.CLAIM_CAPABILITY, null)!!.claimedLevel = level
    }

    override fun getClaimedLevel(player: EntityPlayerMP): RewardLevel =
            player.getCapability(ClaimSerializer.CLAIM_CAPABILITY, null)!!.claimedLevel

    override fun setLoginNotified(player: EntityPlayerMP, notified: Boolean) {
        player.getCapability(ClaimSerializer.CLAIM_CAPABILITY, null)!!.isLoginNotified = notified
    }

    override fun isLoginNotified(player: EntityPlayerMP): Boolean =
            player.getCapability(ClaimSerializer.CLAIM_CAPABILITY, null)!!.isLoginNotified

    override fun setCatchNotified(player: EntityPlayerMP, notified: Boolean) {
        player.getCapability(ClaimSerializer.CLAIM_CAPABILITY, null)!!.isCatchNotified = notified
    }

    override fun isCatchNotified(player: EntityPlayerMP): Boolean =
            player.getCapability(ClaimSerializer.CLAIM_CAPABILITY, null)!!.isCatchNotified
}