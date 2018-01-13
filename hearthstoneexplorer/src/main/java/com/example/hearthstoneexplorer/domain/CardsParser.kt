package com.example.hearthstoneexplorer.domain

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.*

fun cardTypeFromJson(str: String?) = when (str) {
    CardType.Enchantment.description -> CardType.Enchantment
    CardType.Hero.description -> CardType.Hero
    CardType.HeroPower.description -> CardType.HeroPower
    CardType.Minion.description -> CardType.Minion
    CardType.Spell.description -> CardType.Spell
    CardType.Weapon.description -> CardType.Weapon
    else -> throw IllegalArgumentException("Unknown card type $str")
}

fun cardRarityFromJson(str: String?) = when (str) {
    CardRarity.Common.description -> CardRarity.Common
    CardRarity.Free.description -> CardRarity.Free
    CardRarity.Rare.description -> CardRarity.Rare
    CardRarity.Epic.description -> CardRarity.Epic
    CardRarity.Legendary.description -> CardRarity.Legendary
    else -> throw IllegalArgumentException("Unknown card rarity $str")
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

class CardDeserializer : ResponseDeserializable<Array<Card>> {
    override fun deserialize(content: String) = Gson().fromJson(content, Array<Card>::class.java)!!
}

val cardGson = lazy<Gson> {
    val cardTypeAdapter = JsonDeserializer<CardType> { json, _, _ -> cardTypeFromJson(json?.asString) }
    val cardRarityAdapter = JsonDeserializer<CardRarity> { json, _, _ -> cardRarityFromJson(json?.asString) }
    val playerClassAdapter = JsonDeserializer<PlayerClass> { json, _, _ -> playerClassFromJson(json?.asString) }
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