package qwbarch.pixelmon.pixeldex.claim.storage.capability

import qwbarch.pixelmon.pixeldex.reward.RewardLevel

data class ClaimData(var claimedLevel: RewardLevel = RewardLevel.ZERO,
                     var isLoginNotified: Boolean = true)