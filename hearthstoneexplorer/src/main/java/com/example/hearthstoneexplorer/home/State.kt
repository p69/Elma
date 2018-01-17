package com.example.hearthstoneexplorer.home

import com.example.hearthstoneexplorer.domain.Card
import com.example.hearthstoneexplorer.domain.HearthstoneApi
import com.example.hearthstoneexplorer.domain.Locale
import com.example.hearthstoneexplorer.domain.parseCards
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.p69.elma.core.CmdF
import com.p69.elma.core.UpdateResult
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.withContext


object Home {
    fun update(msg: HomeMsg, model: HomeModel): UpdateResult<HomeModel, HomeMsg> {
        return when (msg) {
            is HomeMsg.OnCardsLoaded ->
                UpdateResult(model.copy(isLoading = false, cards = msg.cards.filter { it.img.isNotBlank() }))
            is HomeMsg.OnError -> UpdateResult(model.copy(isLoading = false, error = msg.error))
            is HomeMsg.OnTextQueryChanged -> {
                if (model.isLoading || msg.text.isBlank() || msg.text == model.searchQuery) {
                    UpdateResult(model)
                } else {
                    val searchCmd = CmdF.ofAsyncFunc(
                            { q -> searchByQuery(q) },
                            msg.text,
                            HomeMsg::OnCardsLoaded,
                            { exc -> HomeMsg.OnError(exc.message ?: "Unknown error") })
                    UpdateResult(model.copy(isLoading = true, searchQuery = msg.text), searchCmd)
                }
            }
            else -> UpdateResult(model)
        }
    }

    private suspend fun searchByQuery(query: String): List<Card> = withContext(CommonPool) {
        val (_, _, res) = Fuel
                .request(HearthstoneApi.search(query, locale = Locale.enUS))
                .responseString()
        return@withContext when (res) {
            is Result.Success -> parseCards(res.value)
            is Result.Failure -> throw res.error
        }
    }
}