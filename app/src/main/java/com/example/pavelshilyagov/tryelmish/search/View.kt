package com.example.pavelshilyagov.tryelmish.search

import com.facebook.litho.ComponentContext
import com.facebook.yoga.YogaEdge
import com.p69.elma.core.Dispatch
import com.p69.elma.litho.DSL.*
import com.p69.elma.litho.ElmaLithoView
import io.michaelrocks.optional.Optional

object SearchUI {
    fun view(model: SearchModel, ctx: ComponentContext, dispatcher: Dispatch<SearchMsg>): ElmaLithoView =
            column(ctx) {
                children(ctx) {
                    editText {
                        flexGrow(1f)
                        editable(true)
                        textSizeDip(16f)
                        text(model.searchValue)
                        onTextChanged { txt -> dispatcher(SearchMsg.OnTextChanged(txt)) }
                        column {  }
                    }
                    text {
                        text("search".toUpperCase())
                        backgroundAttr(android.R.attr.selectableItemBackground)
                        paddingDip(YogaEdge.ALL, 15f)
                        textSizeDip(18f)
                        enabled(!model.isLoading && model.searchValue.isNotEmpty())
                        onClick { dispatcher(SearchMsg.SearchByCity) }
                    }
                    text {
                        textSizeDip(14f)
                        text(createCurrentText(model))
                    }
                    when (model.current) {
                        is Optional.Some -> include(createShowDetailsButton(model.current.value, ctx, dispatcher))
                    }
                }
            }


    private fun createCurrentText(model: SearchModel): String {
        return when (model.current) {
            is Optional.Some -> {
                val current = model.current.value
                "Current temperature in ${current.location.name} (${current.location.country}) is ${current.temperature} ℃"
            }
            is Optional.None ->
                if (model.error.isNotEmpty()) {
                    "Error: ${model.error}"
                } else {
                    ""
                }
        }
    }

    private fun createShowDetailsButton(details: CurrentWeatherModel, ctx: ComponentContext, dispatcher: Dispatch<SearchMsg>): ElmaLithoView =
            text(ctx) {
                text("show details")
                textSizeDip(18f)
                onClick { dispatcher(SearchMsg.ShowDetails(details)) }
            }
}