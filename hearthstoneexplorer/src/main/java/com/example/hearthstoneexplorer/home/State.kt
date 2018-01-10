package com.example.hearthstoneexplorer.home

import com.example.hearthstoneexplorer.domain.Card
import com.example.hearthstoneexplorer.domain.HearthstoneApi
import com.example.hearthstoneexplorer.domain.Locale
import com.example.hearthstoneexplorer.domain.parseCards
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.p69.elma.core.CmdF
import com.p69.elma.core.UpdateResult
import io.michaelrocks.optional.Optional
import kotlinx.coroutines.experimental.*


object Home {
    fun update(msg:HomeMsg, model:HomeModel): UpdateResult<HomeModel, HomeMsg> {
        return when(msg) {
            is HomeMsg.OnCardsLoaded -> UpdateResult(model.copy(cards = msg.cards))
            is HomeMsg.OnError -> UpdateResult(model.copy(error = Optional.Some(msg.error)))
            is HomeMsg.OnTextQueryChanged -> {
                if (model.isLoading) {
                    UpdateResult(model)
                } else {
                    val searchCmd = CmdF.ofAsyncFunc(
                            { q -> searchByQuery(q) },
                            msg.text,
                            HomeMsg::OnCardsLoaded,
                            HomeMsg::OnError)
                    UpdateResult(model.copy(isLoading = true), searchCmd)
                }
            }
            else -> UpdateResult(model)
        }
    }

    private suspend fun searchByQuery(query: String): List<Card> = withContext(CommonPool) {
        val (_, _, res) = Fuel.request(HearthstoneApi.search(query, locale = Locale.ruRU)).responseString()
        return@withContext when (res) {
            is Result.Success -> {
                val cards = parseCards(res.value)
                cards
            }
            is Result.Failure -> throw res.error
        }
    }
}