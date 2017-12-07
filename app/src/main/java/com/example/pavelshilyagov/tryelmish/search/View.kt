package com.example.pavelshilyagov.tryelmish.search

import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.widget.Text
import com.p69.elma.core.*
import com.p69.elma.litho.DSL.*
import com.p69.elma.litho.ElmaLithoView
import io.michaelrocks.optional.Optional
import kotlinx.coroutines.experimental.launch

object SearchUI {
    fun view(model: SearchModel, ctx: ComponentContext, dispatcher: Dispatch<SearchMsg>): ElmaLithoView =
            ElmaLithoView.ComponentLayoutView(
                    Column.create(ctx)
                            .child(com.facebook.litho.widget.EditText.create(ctx)
                                    .flexGrow(1f)
                                    .editable(true)
                                    .text(model.searchValue)
                                    .textSizeDip(16f)
                                    .textChangedEventHandler { v -> launch { dispatcher(SearchMsg.OnTextChanged(v)) } }
                                    .build()
                            )
                            .child(Text.create(ctx)
                                    .text("search")
                                    .textSizeDip(18f)
                                    .enabled(!model.isLoading && model.searchValue.isNotEmpty())
                                    .clickHandler { launch { dispatcher(SearchMsg.SearchByCity) } }
                                    .build())
                            .child(Text.create(ctx)
                                    .textSizeDip(14f)
                                    .text(createCurrentText(model)))
                            .child(createShowDetailsButton(model, ctx, dispatcher))
                            .build()
            )

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

    private fun createShowDetailsButton(model: SearchModel, ctx: ComponentContext, dispatcher: Dispatch<SearchMsg>): Component<*>? {
        return when (model.current) {
            is Optional.Some -> {
                Text.create(ctx)
                        .text("show details")
                        .textSizeDip(18f)
                        .clickHandler { launch { dispatcher(SearchMsg.ShowDetails(model.current.value)) } }
                        .build()
            }
            is Optional.None -> null
        }
    }
}