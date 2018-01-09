package com.example.hearthstoneexplorer.home

import com.example.hearthstoneexplorer.domain.*
import io.michaelrocks.optional.Optional

data class HomeModel(
        val cards: List<Card>,
        val error: Optional<Throwable> = Optional.None,
        val isLoading: Boolean = false,
        val searchQuery: String = "")

sealed class HomeMsg {
    object Back : HomeMsg()
    object Exit : HomeMsg()
    object HideVirtualKeyboard : HomeMsg()

    data class OnTextQueryChanged(val text: String) : HomeMsg()
    data class OnCardsLoaded(val cards: List<Card>) : HomeMsg()
    data class OnError(val error: Throwable) : HomeMsg()
}