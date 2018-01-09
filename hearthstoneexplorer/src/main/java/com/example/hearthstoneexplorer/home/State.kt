package com.example.hearthstoneexplorer.home

import com.p69.elma.core.UpdateResult
import io.michaelrocks.optional.Optional


object Home {
    fun update(msg:HomeMsg, model:HomeModel): UpdateResult<HomeModel, HomeMsg> {
        return when(msg) {
            is HomeMsg.OnCardsLoaded -> UpdateResult(model.copy(cards = msg.cards))
            is HomeMsg.OnError -> UpdateResult(model.copy(error = Optional.Some(msg.error)))
            else -> UpdateResult(model)
        }
    }
}