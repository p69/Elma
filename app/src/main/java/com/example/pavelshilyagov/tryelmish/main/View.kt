package com.example.pavelshilyagov.tryelmish.main


import com.example.pavelshilyagov.tryelmish.details.DetailsUI
import com.example.pavelshilyagov.tryelmish.screenWithTransition
import com.example.pavelshilyagov.tryelmish.search.SearchUI
import com.facebook.litho.ComponentContext
import com.p69.elma.core.Dispatch
import com.p69.elma.litho.DSL.ElmaLithoView


object MainUI {
    fun view(model: MainModel, ctx: ComponentContext, dispatch: Dispatch<Msg>): ElmaLithoView =
            screenWithTransition(ctx) {
                val (screenView, screenKey) = createView(model.screen, ctx, dispatch)
                screen(screenView)
                screenKey(screenKey)
            }

    private fun createView(screen: Screen, ctx: ComponentContext, dispatch: Dispatch<Msg>): Pair<ElmaLithoView, String> =
            when (screen) {
                is Screen.Search -> Pair(SearchUI.view(screen.model, ctx, { msg -> dispatch(Msg.Search(msg)) }), "search-view")
                is Screen.Details -> Pair(DetailsUI.view(screen.model, ctx, { msg -> dispatch(Msg.Details(msg)) }), "details-view")
            }
}