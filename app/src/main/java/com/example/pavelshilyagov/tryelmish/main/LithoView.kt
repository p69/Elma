package com.example.pavelshilyagov.tryelmish.main

import com.example.pavelshilyagov.tryelmish.ScreenWithTransition
import com.example.pavelshilyagov.tryelmish.details.DetailsUI
import com.example.pavelshilyagov.tryelmish.search.SearchUI
import com.facebook.litho.ComponentContext
import com.facebook.litho.widget.Text
import com.p69.elma.core.*
import com.p69.elma.litho.ElmaLithoView


object MainLithoUI {
    fun view(model: MainModel, ctx: ComponentContext, dispatch: Dispatch<Msg>): ElmaLithoView {
        val (screen, key) = createView(model.screen, ctx, dispatch)
        return ElmaLithoView.ComponentView(ScreenWithTransition.create(ctx).screen(screen).screenKey(key).build())
    }

    private fun createView(screen: Screen, ctx: ComponentContext, dispatch: Dispatch<Msg>): Pair<ElmaLithoView, String> =
            when (screen) {
                is Screen.Search -> Pair(SearchUI.view(screen.model, ctx, { msg -> dispatch(Msg.Search(msg)) }), "search-view")
                is Screen.Details -> Pair(DetailsUI.view(screen.model, ctx, { msg -> dispatch(Msg.Details(msg)) }), "details-view")
                else -> Pair(ElmaLithoView.ComponentView(Text.create(ctx).text("Unknown screen").build()), "unknown-view")
            }
}