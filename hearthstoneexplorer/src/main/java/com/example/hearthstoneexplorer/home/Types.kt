package com.example.hearthstoneexplorer.home

data class HomeModel(val msg: String)

sealed class HomeMsg {
    object Back : HomeMsg()
    object Exit : HomeMsg()
    object HideVirtualKeyboard : HomeMsg()

    data class SayHi(val whom: String) : HomeMsg()
}