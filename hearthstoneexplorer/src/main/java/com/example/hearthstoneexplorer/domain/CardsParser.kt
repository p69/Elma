package com.example.hearthstoneexplorer.domain

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer

fun cardTypeFromString(str: String?) = when (str) {
    CardType.Enchantment.description -> CardType.Enchantment
    CardType.Hero.description -> CardType.Hero
    CardType.HeroPower.description -> CardType.HeroPower
    CardType.Minion.description -> CardType.Minion
    CardType.Spell.description -> CardType.Spell
    CardType.Weapon.description -> CardType.Weapon
    else -> throw IllegalArgumentException("Unknown card type $str")
}

fun cardRarityFromString(str: String?) = when (str) {
    CardRarity.Common.description -> CardRarity.Common
    CardRarity.Free.description -> CardRarity.Free
    CardRarity.Rare.description -> CardRarity.Rare
    CardRarity.Epic.description -> CardRarity.Epic
    CardRarity.Legendary.description -> CardRarity.Legendary
    else -> throw IllegalArgumentException("Unknown card rarity $str")
}

fun playerClassFromString(str: String?) = when (str) {
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

val cardGson = lazy<Gson> {
    val cardTypeAdapter = JsonDeserializer<CardType> { json, _, _ -> cardTypeFromString(json?.asString) }
    val cardRarityAdapter = JsonDeserializer<CardRarity> { json, _, _ -> cardRarityFromString(json?.asString) }
    val playerClassAdapter = JsonDeserializer<PlayerClass> { json, _, _ -> playerClassFromString(json?.asString) }
    val mechanicsAdapter = JsonDeserializer<Mechanic> { json, _, _ -> Mechanic(json.asJsonObject?.get("name")?.asString ?: "") }
    GsonBuilder()
            .registerTypeAdapter(CardType::class.java, cardTypeAdapter)
            .registerTypeAdapter(CardRarity::class.java, cardRarityAdapter)
            .registerTypeAdapter(PlayerClass::class.java, playerClassAdapter)
            .registerTypeAdapter(Mechanic::class.java, mechanicsAdapter)
            .create()
}

fun parseCards(str: String): List<Card> {
    val cards = cardGson.value.fromJson(str, Array<Card>::class.java)
    return cards.toList()
}