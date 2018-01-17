package com.example.hearthstoneexplorer.home

import android.os.Bundle
import android.os.Parcel
import com.example.hearthstoneexplorer.KParcelable
import com.example.hearthstoneexplorer.domain.Card
import com.example.hearthstoneexplorer.parcelableCreator

data class HomeModel(
        val cards: List<Card> = emptyList(),
        val error: String = "",
        val isLoading: Boolean = false,
        val searchQuery: String = "") : KParcelable {

    constructor(parcel: Parcel) : this(
            parcel.createTypedArrayList(Card.CREATOR),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeTypedList(cards)
        dest.writeString(error)
        dest.writeByte(if (isLoading) 1 else 0)
        dest.writeString(searchQuery)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::HomeModel)
    }
}

sealed class HomeMsg {
    object Back : HomeMsg()
    object Exit : HomeMsg()
    data class SaveInstanceState(var outState: Bundle?) : HomeMsg()
    object HideVirtualKeyboard : HomeMsg()

    data class OnTextQueryChanged(val text: String) : HomeMsg()
    data class OnCardsLoaded(val cards: List<Card>) : HomeMsg()
    data class OnError(val error: String) : HomeMsg()
}