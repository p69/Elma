package com.example.hearthstoneexplorer.domain

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonReader
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import java.io.StringReader

val cardTypeConverter = lazy<Converter<CardType>> {
    object : Converter<CardType> {
        override fun toJson(value: CardType): String? = """{"type" : "${value.description}""""

        override fun fromJson(jv: JsonValue) = when (jv.string) {
            CardType.Enchantment.description -> CardType.Enchantment
            CardType.Hero.description -> CardType.Hero
            CardType.HeroPower.description -> CardType.HeroPower
            CardType.Minion.description -> CardType.Minion
            CardType.Spell.description -> CardType.Spell
            CardType.Weapon.description -> CardType.Weapon
            else -> throw IllegalArgumentException("Unknown card type $jv")
        }
    }
}

val cardRarityConverter = lazy<Converter<CardRarity>> {
    object : Converter<CardRarity> {
        override fun toJson(value: CardRarity): String? = """{"rarity" : "${value.description}""""

        override fun fromJson(jv: JsonValue) = when (jv.string) {
            CardRarity.Common.description -> CardRarity.Common
            CardRarity.Free.description -> CardRarity.Free
            CardRarity.Rare.description -> CardRarity.Rare
            CardRarity.Epic.description -> CardRarity.Epic
            CardRarity.Legendary.description -> CardRarity.Legendary
            else -> throw IllegalArgumentException("Unknown card rarity $jv")
        }
    }
}

val playerClassConverter = lazy<Converter<PlayerClass>> {
    object : Converter<PlayerClass> {
        override fun toJson(value: PlayerClass): String? = """{"playerClass" : "${value.name}""""

        override fun fromJson(jv: JsonValue) = when (jv.string) {
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
            else -> throw IllegalArgumentException("Unknown player class $jv")
        }
    }
}

fun parseCards(str: String): List<Card> {
    val klaxon = Klaxon()
            .converter(cardTypeConverter.value)
            .converter(cardRarityConverter.value)
            .converter(playerClassConverter.value)
    return klaxon.parseArray<Card>(str) ?: emptyList()
    JsonReader(StringReader(str)).use { reader ->
        val result = arrayListOf<Card>()
        reader.beginArray {
            while (reader.hasNext()) {
                val card = klaxon.parse<Card>(reader)
                if (card != null) result.add(card)
            }
        }
        return result
    }
}