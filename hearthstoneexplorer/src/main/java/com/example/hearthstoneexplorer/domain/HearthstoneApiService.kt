package com.example.hearthstoneexplorer.domain

import com.example.hearthstoneexplorer.HearthstoneApiKey
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.util.FuelRouting

sealed class HearthstoneApi : FuelRouting {
    override val basePath = "https://omgvamp-hearthstone-v1.p.mashape.com"

    class search(val query:String): HearthstoneApi()

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
                is search -> emptyList()
            }
        }

    override val method: Method
        get() = Method.GET
}