package com.example.hearthstoneexplorer.domain

import com.example.hearthstoneexplorer.HearthstoneApiKey
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.util.FuelRouting

sealed class Locale(val value: String) {
    object enUS : Locale("enUS")
    object enGB : Locale("enGB")
    object deDE : Locale("deDE")
    object esES : Locale("esES")
    object esMX : Locale("esMX")
    object frFR : Locale("frFR")
    object itIT : Locale("itIT")
    object koKR : Locale("koKR")
    object plPL : Locale("plPL")
    object ptBR : Locale("ptBR")
    object ruRU : Locale("ruRU")
    object zhCN : Locale("zhCN")
    object zhTW : Locale("zhTW")
    object jaJP : Locale("jaJP")
    object thTH : Locale("thTH")
}

sealed class HearthstoneApi : FuelRouting {
    override val basePath = "https://omgvamp-hearthstone-v1.p.mashape.com"

    class search(val query:String, val collectible: Boolean = true, val locale: Locale = Locale.enUS): HearthstoneApi()

    override val path: String
        get() {
            return when(this) {
                is search -> "cards/search/${query}"
            }
        }

    override val headers: Map<String, String>?
        get() = mapOf("X-Mashape-Key" to HearthstoneApiKey)

    override val params: List<Pair<String, Any?>>?
        get() {
            return when(this) {
                is search -> listOf("collectible" to collectible, "locale" to locale.value)
            }
        }

    override val method: Method
        get() = Method.GET
}