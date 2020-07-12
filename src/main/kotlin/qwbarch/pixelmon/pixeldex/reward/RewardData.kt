package qwbarch.pixelmon.pixeldex.reward

import net.minecraft.item.ItemStack

data class RewardData(val items: List<ItemStack>, val commands: List<String>, val description: String)