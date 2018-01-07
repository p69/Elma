package com.example.hearthstoneexplorer.home

import com.p69.elma.core.UpdateResult

object Home {
    fun update(msg:HomeMsg, model:HomeModel): UpdateResult<HomeModel, HomeMsg> {
        return when(msg) {
            is HomeMsg.SayHi -> UpdateResult(model.copy(msg=msg.whom))
            else -> UpdateResult(model)
        }
    }
}