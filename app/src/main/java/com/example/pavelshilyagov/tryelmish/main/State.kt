package com.example.pavelshilyagov.tryelmish.main

import com.example.pavelshilyagov.tryelmish.details.Details
import com.example.pavelshilyagov.tryelmish.search.Search
import com.p69.elma.core.CmdF
import com.p69.elma.core.CmdF.map
import com.p69.elma.core.UpdateResult

object Main {
    fun update(msg: Msg, model: MainModel): UpdateResult<MainModel, Msg> {
        when {
            model.screen is Screen.Search && msg is Msg.Search -> {
                val (updatedSearchModel, searchEffects, parentEffects) = Search.update(msg.msg, model.screen.model)
                return UpdateResult(
                        model.copy(screen = Screen.Search(updatedSearchModel)),
                        CmdF.batch(searchEffects map Msg::Search, parentEffects))
            }
            model.screen is Screen.Search && msg is Msg.GoToDetails -> {
                model.backStack.push(model.screen)
                val detailsModel = Details.init(msg.details)
                val detailsScreen = Screen.Details(detailsModel)
                return UpdateResult(model.copy(screen = detailsScreen))
            }
            msg is Msg.GoBack -> {
                val fromStack = if (model.backStack.empty()) null else model.backStack.pop()
                return if (fromStack == null) {
                    UpdateResult(model, CmdF.ofMsg(Msg.Exit))
                } else {
                    return UpdateResult(model.copy(screen = fromStack))
                }
            }
        }
        return UpdateResult(model)
    }
}