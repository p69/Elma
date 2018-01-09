package com.example.hearthstoneexplorer.domain

import com.beust.klaxon.*

fun cardTypeFromJson(str: String?) = when (str) {
    CardType.Enchantment.description -> CardType.Enchantment
    CardType.Hero.description -> CardType.Hero
    CardType.HeroPower.description -> CardType.HeroPower
    CardType.Minion.description -> CardType.Minion
    CardType.Spell.description -> CardType.Spell
    CardType.Weapon.description -> CardType.Weapon
    else -> throw IllegalArgumentException("Unknown card type $str")
}

val cardTypeConverter = lazy<Converter<CardType>> {
    object : Converter<CardType> {
        override fun toJson(value: CardType): String? = """{"type" : "${value.description}""""

        override fun fromJson(jv: JsonValue) = cardTypeFromJson(jv.string)
    }
}

fun cardRarityFromJson(str: String?) = when (str) {
    CardRarity.Common.description -> CardRarity.Common
    CardRarity.Free.description -> CardRarity.Free
    CardRarity.Rare.description -> CardRarity.Rare
    CardRarity.Epic.description -> CardRarity.Epic
    CardRarity.Legendary.description -> CardRarity.Legendary
    else -> throw IllegalArgumentException("Unknown card rarity $str")
}

val cardRarityConverter = lazy<Converter<CardRarity>> {
    object : Converter<CardRarity> {
        override fun toJson(value: CardRarity): String? = """{"rarity" : "${value.description}""""

        override fun fromJson(jv: JsonValue) = cardRarityFromJson(jv.string)
    }
}

fun playerClassFromJson(str: String?) = when (str) {
    PlayerClass.DeathKnight.name -> PlayerClass.DeathKnight
    PlayerClass.Dream.name -> PlayerClass.Dream
    PlayerClass.Neutral.name -> PlayerClass.Neutral
    PlayerClass.Druid.name -> PlayerClass.Druid
    PlayerClass.Hunter.name -> PlayerClass.Hunter
    PlayerClass.Mage.name -> PlayerClass.Mage
    PlayerClass.Paladin.name -> PlayerClass.Paladin
    PlayerClass.Priest.name -> PlayerClass.Priest
    PlayerClass.Rogue.name -> PlayerClass.Rogue
    PlayerClass.Shaman.name -> PlayerClass.Shaman
    PlayerClass.Warlock.name -> PlayerClass.Warlock
    PlayerClass.Warrior.name -> PlayerClass.Warrior
    else -> throw IllegalArgumentException("Unknown player class $str")
}

val playerClassConverter = lazy<Converter<PlayerClass>> {
    object : Converter<PlayerClass> {
        override fun toJson(value: PlayerClass): String? = """{"playerClass" : "${value.name}""""

        override fun fromJson(jv: JsonValue) = playerClassFromJson(jv.string)
    }
}

val mechanicConverter = lazy<Converter<Mechanic>> {
    object : Converter<Mechanic> {
        override fun toJson(value: Mechanic): String? = """{"mechanic" : "${value.name}""""

        override fun fromJson(jv: JsonValue) = Mechanic(jv.objString("name"))
    }
}

fun parseCards(str: String): List<Card> {
    val klaxon = Klaxon()
            .converter(cardRarityConverter.value)
            .converter(cardTypeConverter.value)
            .converter(playerClassConverter.value)
            .converter(mechanicConverter.value)
    return klaxon.parseArray(str) ?: emptyList()
}