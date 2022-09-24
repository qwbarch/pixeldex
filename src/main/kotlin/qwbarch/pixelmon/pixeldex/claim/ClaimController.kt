package qwbarch.pixelmon.pixeldex.claim

import net.minecraft.entity.player.EntityPlayerMP
import qwbarch.pixelmon.pixeldex.reward.RewardLevel

interface ClaimController {

    fun setClaimed(player: EntityPlayerMP, level: RewardLevel)
    fun getClaimedLevel(player: EntityPlayerMP): RewardLevel

    fun setLoginNotified(player: EntityPlayerMP, notified: Boolean)
    fun isLoginNotified(player: EntityPlayerMP): Boolean

    fun setCatchNotified(player: EntityPlayerMP, notified: Boolean)
    fun isCatchNotified(player: EntityPlayerMP): Boolean
}