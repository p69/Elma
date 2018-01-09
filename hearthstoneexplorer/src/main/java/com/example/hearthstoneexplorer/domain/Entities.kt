package com.example.hearthstoneexplorer.domain

sealed class CardType(val description: String) {
    object Hero : CardType("Hero")
    object Minion : CardType("Minion")
    object Spell : CardType("Spell")
    object Enchantment : CardType("Enchantment")
    object Weapon : CardType("Weapon")
    object HeroPower : CardType("Hero Power")
}

sealed class CardRarity(val description: String) {
    object Free : CardRarity("Free")
    object Common : CardRarity("Common")
    object Rare : CardRarity("Rare")
    object Epic : CardRarity("Epic")
    object Legendary : CardRarity("Legendary")
}

sealed class PlayerClass(val name: String) {
    object DeathKnight : PlayerClass("Death Knight")
    object Dream : PlayerClass("Dream")
    object Neutral : PlayerClass("Neutral")
    object Druid : PlayerClass("Druid")
    object Hunter : PlayerClass("Hunter")
    object Mage : PlayerClass("Mage")
    object Paladin : PlayerClass("Paladin")
    object Priest : PlayerClass("Priest")
    object Rogue : PlayerClass("Rogue")
    object Shaman : PlayerClass("Shaman")
    object Warlock : PlayerClass("Warlock")
    object Warrior : PlayerClass("Warrior")
}

data class Card(
        val cardId: String,
        val dbfId: String,
        val name: String,
        val cardSet: String,
        val type: CardType,
        val rarity: CardRarity,
        val cost: Int,
        val attack: Int,
        val health: Int,
        val text: String,
        val flavor: String,
        val artist: String,
        val collectible: Boolean,
        val elite: Boolean,
        val race: String,
        val playerClass: PlayerClass,
        val img: String,
        val imgGold: String,
        val locale: String
        //val mechanics: List<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        if (cardId != other.cardId) return false
        if (dbfId != other.dbfId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cardId.hashCode()
        result = 31 * result + dbfId.hashCode()
        return result
    }
}
