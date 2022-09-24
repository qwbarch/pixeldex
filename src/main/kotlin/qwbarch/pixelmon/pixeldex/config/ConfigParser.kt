package qwbarch.pixelmon.pixeldex.config

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import qwbarch.pixelmon.Pixeldex

object ConfigParser {

    fun parseItems(itemSeparator: String, quantitySeparator: String, unparsed: String): List<ItemStack> {
        val itemStacks: MutableList<ItemStack> = ArrayList()
        if (unparsed.isNotBlank()) {
            //Split each item entry (item-id + quantity) into separate elements
            unparsed.split(itemSeparator).forEach {
                val entry = it.split(quantitySeparator)
                //If item entry has no quantity, throw exception
                if (entry.size == 1) {
                    val errorMessage = "$it does not have a quantity set."
                    Pixeldex.INSTANCE.logger.error(errorMessage)
                    throw RuntimeException(errorMessage)
                } else {
                    itemStacks.add(ItemStack(validateItem(entry[0]), entry[1].toInt()))
                }
            }
        }
        return itemStacks
    }

    fun parseCommands(commandSeparator: String, unparsed: String): List<String> =
            if (unparsed.isBlank()) arrayListOf() else unparsed.split(commandSeparator)

    private fun validateItem(itemId: String): Item {
        val item = Item.getByNameOrId(itemId)
        if (item == null) {
            val errorMessage = "$itemId is not a valid item."
            Pixeldex.INSTANCE.logger.error(errorMessage)
            throw RuntimeException(errorMessage)
        }
        return item
    }
}