package qwbarch.pixelmon.pixeldex.config

import net.minecraft.util.text.TextFormatting
import org.ini4j.Ini
import org.ini4j.Wini
import qwbarch.pixelmon.Pixeldex
import qwbarch.pixelmon.pixeldex.reward.RewardLevel
import java.io.File

interface ConfigHandler {

    val commandAlias: String
    val messagePrefix: String

    val catchNotifEnabledMessage: String
    val catchNotifDisabledMessage: String
    val loginNotifEnabledMessage: String
    val loginNotifDisabledMessage: String
    val noClaimableRewardsMessage: String
    val invalidCommandUsageMessage: String
    val rewardDescriptionMessage: String
    val claimRewardMessage: String
    val rewardGUITitleMessage: String
    val selfPlayerProgressMessage: String
    val otherPlayerProgressMessage: String
    val playerUnavailableMessage: String
    val unclaimedRewardsMessage: String

    fun getItemSeparator(): String
    fun getQuantitySeparator(): String
    fun getCommandSeparator(): String

    fun getItemRewards(level: RewardLevel): String
    fun getCommandRewards(level: RewardLevel): String
    fun getDescription(level: RewardLevel): String

    fun reload()
}

class IniConfigHandler(private val file: File) : ConfigHandler {

    private val ini = Wini()

    private var itemSeparator: String
    private var quantitySeparator: String
    private var commandSeparator: String

    init {
        reload()

        val separatorSection = ini[CATEGORY_SEPARATOR]!!
        itemSeparator = separatorSection[KEY_ITEM_SEPARATOR]!!
        quantitySeparator = separatorSection[KEY_QUANTITY_SEPARATOR]!!
        commandSeparator = separatorSection[KEY_COMMAND_SEPARATOR]!!
        Pixeldex.INSTANCE.logger.info("Separator is: ${ini[CATEGORY_SEPARATOR, KEY_ITEM_SEPARATOR]}")
    }

    private fun setDefaultSettings() {
        if (!ini.contains(CATEGORY_SETTINGS)) {
            val section = ini.add(CATEGORY_SETTINGS)
            section.add(KEY_COMMAND_ALIAS, "pd")
            section.add(KEY_MESSAGE_PREFIX, "[${TextFormatting.AQUA}Pixeldex${TextFormatting.WHITE}]")
        }
    }

    private fun setDefaultSeparators() {
        if (!ini.contains(CATEGORY_SEPARATOR)) {
            val separatorSection = ini.add(CATEGORY_SEPARATOR)!!
            separatorSection.add(KEY_ITEM_SEPARATOR, "|")
            separatorSection.putComment(KEY_ITEM_SEPARATOR,
                    "This value is what separates each item. Default: |")
            separatorSection.add(KEY_QUANTITY_SEPARATOR, ";")
            separatorSection.putComment(KEY_QUANTITY_SEPARATOR,
                    "This value is what separates an item's id and quantity. Default: ;")
            separatorSection.add(KEY_COMMAND_SEPARATOR, "|")
            separatorSection.putComment(KEY_COMMAND_SEPARATOR,
                    "This value is what separates each command. Default: |")
        }
    }

    private fun setDefaultRewards() {
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
    }

    private fun setDefaultMessages() {
        if (!ini.contains(CATEGORY_MESSAGES)) {
            val section = ini.add(CATEGORY_MESSAGES)
            section.add(KEY_CATCH_NOTIF_ENABLED, "Catch notifications enabled.")
            section.add(KEY_CATCH_NOTIF_DISABLED, "Catch notifications disabled.")
            section.add(KEY_LOGIN_NOTIF_ENABLED, "Login notifications enabled.")
            section.add(KEY_LOGIN_NOTIF_DISABLED, "Login notifications disabled.")
            section.add(KEY_NO_CLAIMABLE_REWARDS, "You do not have any available rewards to claim.")
            section.add(KEY_INVALID_COMMAND_USAGE, "Correct usage: @usage")
            section.putComment(KEY_INVALID_COMMAND_USAGE, "@usage is a placeholder for the command usage")
            section.add(KEY_REWARD_DESCRIPTION, "Rewards for ${TextFormatting.AQUA}@rewardLevel" +
                    "${TextFormatting.WHITE}% completion:\\n@rewardDescription")
            section.putComment(KEY_REWARD_DESCRIPTION,
                    "@rewardLevel and @rewardDescription are placeholders for its respective reward information")
            section.add(KEY_CLAIM_REWARD, "Rewards for ${TextFormatting.AQUA}@rewardLevel" +
                    "${TextFormatting.WHITE}% completion claimed!")
            section.add(KEY_REWARD_GUI_TITLE, "@rewardLevel% completion rewards")
            section.add(KEY_SELF_PLAYER_PROGRESS, "You completed ${TextFormatting.AQUA}@progress" +
                    "${TextFormatting.WHITE}% of your pok\u00e9dex!")
            section.putComment(KEY_SELF_PLAYER_PROGRESS,
                    "@progress is a placeholder for the player's pokedex progress")
            section.add(KEY_OTHER_PLAYER_PROGRESS, "@playerName completed ${TextFormatting.AQUA}@progress" +
                    "${TextFormatting.WHITE}% of their pok\u00e9dex!")
            section.add(KEY_PLAYER_UNAVAILABLE, "The player @playerName is not available.")
            section.add(KEY_UNCLAIMED_REWARDS, "You have unclaimed rewards! For a list of sub-commands, type /@alias")
            section.putComment(KEY_UNCLAIMED_REWARDS, "@alias is a placeholder for the command alias")
        }
    }

    private fun setReward(ini: Ini, rewardLevel: String, items: String, commands: String, description: String) {
        if (!ini.contains(rewardLevel + CATEGORY_REWARD_SUFFIX)) {
            val section = ini.add(rewardLevel + CATEGORY_REWARD_SUFFIX)!!
            section.add(KEY_ITEMS, items)
            section.add(KEY_COMMANDS, commands)
            section.add(KEY_DESCRIPTION, description)
        }
    }

    private fun replaceNewLines(text: String): String = text.replace("\\n", "\n")

    override val commandAlias: String get() = ini[CATEGORY_SETTINGS, KEY_COMMAND_ALIAS]
    override val messagePrefix: String get() = ini[CATEGORY_SETTINGS, KEY_MESSAGE_PREFIX]

    override val catchNotifEnabledMessage: String
        get() = replaceNewLines(ini[CATEGORY_MESSAGES, KEY_CATCH_NOTIF_ENABLED])
    override val catchNotifDisabledMessage: String
        get() = replaceNewLines(ini[CATEGORY_MESSAGES, KEY_CATCH_NOTIF_DISABLED])
    override val loginNotifEnabledMessage: String
        get() = replaceNewLines(ini[CATEGORY_MESSAGES, KEY_LOGIN_NOTIF_ENABLED])
    override val loginNotifDisabledMessage: String
        get() = replaceNewLines(ini[CATEGORY_MESSAGES, KEY_LOGIN_NOTIF_DISABLED])
    override val noClaimableRewardsMessage: String
        get() = replaceNewLines(ini[CATEGORY_MESSAGES, KEY_NO_CLAIMABLE_REWARDS])
    override val invalidCommandUsageMessage: String
        get() = replaceNewLines(ini[CATEGORY_MESSAGES, KEY_INVALID_COMMAND_USAGE])
    override val rewardDescriptionMessage: String
        get() = replaceNewLines(ini[CATEGORY_MESSAGES, KEY_REWARD_DESCRIPTION])
    override val claimRewardMessage: String
        get() = replaceNewLines(ini[CATEGORY_MESSAGES, KEY_CLAIM_REWARD])
    override val rewardGUITitleMessage: String
        get() = replaceNewLines(ini[CATEGORY_MESSAGES, KEY_REWARD_GUI_TITLE])
    override val selfPlayerProgressMessage: String
        get() = replaceNewLines(ini[CATEGORY_MESSAGES, KEY_SELF_PLAYER_PROGRESS])
    override val otherPlayerProgressMessage: String
        get() = replaceNewLines(ini[CATEGORY_MESSAGES, KEY_OTHER_PLAYER_PROGRESS])
    override val playerUnavailableMessage: String
        get() = replaceNewLines(ini[CATEGORY_MESSAGES, KEY_PLAYER_UNAVAILABLE])
    override val unclaimedRewardsMessage: String
        get() = replaceNewLines(ini[CATEGORY_MESSAGES, KEY_UNCLAIMED_REWARDS])

    override fun getItemSeparator() = itemSeparator

    override fun getQuantitySeparator() = quantitySeparator

    override fun getCommandSeparator() = commandSeparator

    private fun getReward(level: RewardLevel, key: String): String =
            ini[level.value.toString() + CATEGORY_REWARD_SUFFIX, key]

    override fun getItemRewards(level: RewardLevel): String = getReward(level, KEY_ITEMS)

    override fun getCommandRewards(level: RewardLevel): String = getReward(level, KEY_COMMANDS)

    override fun getDescription(level: RewardLevel): String = replaceNewLines(getReward(level, KEY_DESCRIPTION))

    override fun reload(): Unit {
        if (!file.createNewFile()) ini.load(file)
        setDefaultSettings()
        setDefaultSeparators()
        setDefaultRewards()
        setDefaultMessages()
        ini.store(file)
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

        private const val CATEGORY_MESSAGES = "Messages"
        private const val KEY_CATCH_NOTIF_ENABLED = "CatchNotifEnabled"
        private const val KEY_CATCH_NOTIF_DISABLED = "CatchNotifDisabled"
        private const val KEY_LOGIN_NOTIF_ENABLED = "LoginNotifEnabled"
        private const val KEY_LOGIN_NOTIF_DISABLED = "LoginNotifDisabled"
        private const val KEY_NO_CLAIMABLE_REWARDS = "NoClaimableRewards"
        private const val KEY_INVALID_COMMAND_USAGE = "InvalidCommandUsage"
        private const val KEY_REWARD_DESCRIPTION = "RewardDescription"
        private const val KEY_CLAIM_REWARD = "ClaimReward"
        private const val KEY_REWARD_GUI_TITLE = "RewardGUITitle"
        private const val KEY_SELF_PLAYER_PROGRESS = "SelfPlayerProgress"
        private const val KEY_OTHER_PLAYER_PROGRESS = "OtherPlayerProgress"
        private const val KEY_PLAYER_UNAVAILABLE = "PlayerUnavailable"
        private const val KEY_UNCLAIMED_REWARDS = "UnclaimedRewards"

        private const val CATEGORY_SETTINGS = "Settings"
        private const val KEY_COMMAND_ALIAS = "CommandAlias"
        private const val KEY_MESSAGE_PREFIX = "MessagePrefix"
    }
}