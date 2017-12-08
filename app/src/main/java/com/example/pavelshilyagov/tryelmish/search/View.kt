package com.example.pavelshilyagov.tryelmish.search

import com.facebook.litho.ComponentContext
import com.facebook.yoga.YogaEdge
import com.p69.elma.core.Dispatch
import com.p69.elma.litho.DSL.ElmaLithoView
import com.p69.elma.litho.DSL.layout.columnLayout
import com.p69.elma.litho.DSL.widget.editText
import com.p69.elma.litho.DSL.widget.text
import io.michaelrocks.optional.Optional
import kotlinx.coroutines.experimental.launch

object SearchUI {
    fun view(model: SearchModel, ctx: ComponentContext, dispatcher: Dispatch<SearchMsg>): ElmaLithoView =
            columnLayout(ctx) {
                editText {
                    flexGrow = 1f
                    editable = true
                    textSizeDip = 16f
                    text = model.searchValue
                    onTextChanged = { v -> launch { dispatcher(SearchMsg.OnTextChanged(v)) } }
                }
                text {
                    text = "search".toUpperCase()
                    backgroundAttr = android.R.attr.selectableItemBackground
                    paddingDip(YogaEdge.ALL, 15f)
                    textSizeDip = 18f
                    enabled = !model.isLoading && model.searchValue.isNotEmpty()
                    onClick = { launch { dispatcher(SearchMsg.SearchByCity) } }
                }
                text {
                    textSizeDip = 14f
                    text = createCurrentText(model)
                }
                when (model.current) {
                    is Optional.Some -> child(createShowDetailsButton(model.current.value, ctx, dispatcher))
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
                text = "show details"
                textSizeDip = 18f
                onClick = { launch { dispatcher(SearchMsg.ShowDetails(details)) } }
            }
}