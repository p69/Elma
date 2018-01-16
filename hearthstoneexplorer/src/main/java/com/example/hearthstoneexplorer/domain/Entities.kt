package com.example.hearthstoneexplorer.domain

import android.os.Parcel
import com.example.hearthstoneexplorer.*

sealed class CardType(val description: String) : KParcelable {
    object Hero : CardType("Hero")
    object Minion : CardType("Minion")
    object Spell : CardType("Spell")
    object Enchantment : CardType("Enchantment")
    object Weapon : CardType("Weapon")
    object HeroPower : CardType("Hero Power")

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(description)
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator { parcel -> cardTypeFromString(parcel.readString()) }
    }
}

sealed class CardRarity(val description: String) : KParcelable {
    object Free : CardRarity("Free")
    object Common : CardRarity("Common")
    object Rare : CardRarity("Rare")
    object Epic : CardRarity("Epic")
    object Legendary : CardRarity("Legendary")

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(description)
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator { parcel -> cardRarityFromString(parcel.readString()) }
    }
}

sealed class PlayerClass(val name: String) : KParcelable {
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

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator { parcel -> playerClassFromString(parcel.readString()) }
    }
}

data class Mechanic(val name: String) : KParcelable {
    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
    }
    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::Mechanic)
    }
}

data class Card(
        val cardId: String = "",
        val dbfId: String = "",
        val name: String = "",
        val cardSet: String = "",
        val type: CardType = CardType.Enchantment,
        val faction: String = "",
        val rarity: CardRarity = CardRarity.Free,
        val cost: Int = 0,
        val attack: Int = 0,
        val health: Int = 0,
        val text: String = "",
        val flavor: String = "",
        val artist: String = "",
        val collectible: Boolean = false,
        val elite: Boolean = false,
        val race: String = "",
        val playerClass: PlayerClass = PlayerClass.Neutral,
        val img: String = "",
        val imgGold: String = "",
        val locale: String = Locale.enUS.value,
        val mechanics: List<Mechanic> = emptyList()
) : KParcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            CardType.CREATOR.createFromParcel(parcel),
            parcel.readString(),
            CardRarity.CREATOR.createFromParcel(parcel),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            PlayerClass.CREATOR.createFromParcel(parcel),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(Mechanic.CREATOR))

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

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(cardId)
        dest.writeString(dbfId)
        dest.writeString(name)
        dest.writeString(cardSet)
        dest.writeTypedObjectCompat(type, flags)
        dest.writeString(faction)
        dest.writeTypedObjectCompat(rarity, flags)
        dest.writeInt(cost)
        dest.writeInt(attack)
        dest.writeInt(health)
        dest.writeString(text)
        dest.writeString(flavor)
        dest.writeString(artist)
        dest.writeByte(if (collectible) 1 else 0)
        dest.writeByte(if (elite) 1 else 0)
        dest.writeString(race)
        dest.writeTypedObjectCompat(playerClass, flags)
        dest.writeString(img)
        dest.writeString(imgGold)
        dest.writeString(locale)
        dest.writeTypedList(mechanics)
    }
    companion object {
        @JvmField val CREATOR = parcelableCreator(::Card)
    }
}
