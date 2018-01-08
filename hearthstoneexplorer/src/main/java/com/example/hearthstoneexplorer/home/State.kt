package com.example.hearthstoneexplorer.home

import com.p69.elma.core.UpdateResult

object Home {
    fun update(msg:HomeMsg, model:HomeModel): UpdateResult<HomeModel, HomeMsg> {
        return when(msg) {
            is HomeMsg.OnCardsLoaded -> UpdateResult(model.copy(cards = msg.cards))
            else -> UpdateResult(model)
        }
    }
}