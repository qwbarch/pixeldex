package qwbarch.pixelmon.pixeldex

import com.pixelmonmod.pixelmon.Pixelmon
import com.pixelmonmod.pixelmon.pokedex.Pokedex
import net.minecraft.entity.player.EntityPlayerMP
import qwbarch.pixelmon.Pixeldex
import qwbarch.pixelmon.pixeldex.reward.RewardLevel
import java.text.DecimalFormat

object ProgressChecker {

    fun checkProgress(player: EntityPlayerMP): Float =
            Pixelmon.storageManager.getParty(player).pokedex.countCaught().toFloat() /
                    Pokedex.pokedexSize.toFloat() * 100f

    fun hasUnclaimedRewards(player: EntityPlayerMP): Boolean =
            RewardLevel.parse(checkProgress(player)) > Pixeldex.INSTANCE.claimController.getClaimedLevel(player)
}