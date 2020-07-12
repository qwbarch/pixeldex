package qwbarch.pixelmon.pixeldex.config

import org.ini4j.Ini
import org.ini4j.Wini
import qwbarch.pixelmon.Pixeldex
import qwbarch.pixelmon.pixeldex.reward.RewardLevel
import java.io.File

interface ConfigHandler {

    fun getItemSeparator(): String
    fun getQuantitySeparator(): String
    fun getCommandSeparator(): String

    fun getItemRewards(level: RewardLevel): String
    fun getCommandRewards(level: RewardLevel): String
    fun getDescription(level: RewardLevel): String
}

class IniConfigHandler(file: File) : ConfigHandler {

    private val ini = Wini()

    private var itemSeparator: String
    private var quantitySeparator: String
    private var commandSeparator: String

    init {
        if (file.createNewFile()) {
            val separatorSection = ini.add(CATEGORY_SEPARATOR)!!
            separatorSection.add(KEY_ITEM_SEPARATOR, "|")
            separatorSection.putComment(KEY_ITEM_SEPARATOR, "This value is what separates each item. Default: |")
            separatorSection.add(KEY_QUANTITY_SEPARATOR, ";")
            separatorSection.putComment(KEY_QUANTITY_SEPARATOR, "This value is what separates an item's id and quantity. Default: ;")
            separatorSection.add(KEY_COMMAND_SEPARATOR, "|")
            separatorSection.putComment(KEY_COMMAND_SEPARATOR, "This value is what separates each command. Default: |")

            setReward(ini, "10", "pixelmon:great_ball;2|pixelmon:ultra_ball;1", "givemoney @player 100",
                    "1x Ultra Ball\\n2x Great Balls\\n100 Pok\u00e9Dollars")
            setReward(ini, "20", "pixelmon:quick_ball;2|pixelmon:ultra_ball;2|minecraft:diamond;5",
                    "givemoney @player 200|pokegive @player random", "2x Ultra Balls\\n2x Quick Balls\\n" +
                    "5x Diamonds\\n200 Pok\u00e9Dollars\\n1x Random Pok\u00e9mon")
            setReward(ini, "30", "pixelmon:lucky_egg;1|pixelmon:beast_ball;1",
                    "givemoney @player 300|pokegive @player random|pokegive @player random",
                    "1x Lucky Egg\\n1x Beast Ball\\n300 Pok\u00e9Dollars\\n2x Random Pok\u00e9mon")
            setReward(ini, "40", "pixelmon:exp_share;1|pixelmon:beast_ball;1",
                    "givemoney @player 400|pokegive @player random|pokegive @player random",
                    "1x Exp Share\\n1x Beast Ball\\n400 Pok\u00e9Dollars\\n2x Random Pok\u00e9mon")
            setReward(ini, "50", "pixelmon:exp_all;1|pixelmon:master_ball;1",
                    "givemoney @player 500|pokegive @player random|pokegive @player random",
                    "1x Exp All\\n1x Master Ball\\n500 Pok\u00e9Dollars\\n2x Random Pok\u00e9mon")
            setReward(ini, "60", "pixelmon:destiny_knot;1|pixelmon:master_ball;1",
                    "givemoney @player 600|pokegive @player random|pokegive @player random",
                    "1x Destiny Knot\\n1x Master Ball\\n600 Pok\u00e9Dollars\\n3x Random Pok\u00e9mon")
            setReward(ini, "70", "pixelmon:ranch_upgrade;2|pixelmon:master_ball;1",
                    "givemoney @player 700|pokegive @player random|pokegive @player random|pokegive @player random",
                    "2x Ranch Upgrades\\n1x Master Ball\\n700 Pok\u00e9Dollars\\n3x Random Pok\u00e9mon")
            setReward(ini, "80", "pixelmon:ranch_upgrade;5|pixelmon:master_ball;1",
                    "givemoney @player 800|pokegive @player random|",
                    "5x Ranch Upgrades\\n1x Master Ball\\n800 Pok\u00e9Dollars\\n3x Random Pok\u00e9mon")
            setReward(ini, "90", "pixelmon:ranch_upgrade;10|pixelmon:master_ball;1",
                    "givemoney @player 900|pokegive @player random|pokegive @player random|pokegive @player random",
                    "10x Ranch Upgrade\\n1x Master Ball\\n900 Pok\u00e9Dollars\\n3x Random Pok\u00e9mon")
            setReward(ini, "100", "pixelmon:master_ball;10|pixelmon:rare_candy;100",
                    "givemoney @player 10000", "10x Master Balls\\n100x Rare Candies\\n10000 Pok\u00e9Dollars")

            ini.store(file)
        }
        ini.load(file)

        //Load separators
        val separatorSection = ini[CATEGORY_SEPARATOR]!!
        itemSeparator = separatorSection[KEY_ITEM_SEPARATOR]!!
        quantitySeparator = separatorSection[KEY_QUANTITY_SEPARATOR]!!
        commandSeparator = separatorSection[KEY_COMMAND_SEPARATOR]!!
        Pixeldex.INSTANCE.logger.info("Separator is: ${ini[CATEGORY_SEPARATOR, KEY_ITEM_SEPARATOR]}")
    }

    private fun setReward(ini: Ini, rewardLevel: String, items: String, commands: String, description: String) {
        val section = ini.add(rewardLevel + CATEGORY_REWARD_SUFFIX)!!
        section.add(KEY_ITEMS, items)
        section.add(KEY_COMMANDS, commands)
        section.add(KEY_DESCRIPTION, description)
    }

    override fun getItemSeparator() = itemSeparator

    override fun getQuantitySeparator() = quantitySeparator

    override fun getCommandSeparator() = commandSeparator

    private fun get(level: RewardLevel, key: String): String {
        val section = ini[level.value.toString() + CATEGORY_REWARD_SUFFIX] ?: return ""
        return section[key] ?: ""
    }

    override fun getItemRewards(level: RewardLevel): String = get(level, KEY_ITEMS)

    override fun getCommandRewards(level: RewardLevel): String = get(level, KEY_COMMANDS)

    override fun getDescription(level: RewardLevel): String {
        var description = get(level, KEY_DESCRIPTION)
        if (description.isNotEmpty()) description = description.replace("\\n", "\n")
        return description
    }

    companion object {
        private const val CATEGORY_SEPARATOR = "Separators"
        private const val KEY_ITEM_SEPARATOR = "Item Separator"
        private const val KEY_QUANTITY_SEPARATOR = "Quantity Separator"
        private const val KEY_COMMAND_SEPARATOR = "Command Separator"

        private const val CATEGORY_REWARD_SUFFIX = "% Reward"
        private const val KEY_ITEMS = "Items"
        private const val KEY_COMMANDS = "Commands"
        private const val KEY_DESCRIPTION = "Description"
    }
}