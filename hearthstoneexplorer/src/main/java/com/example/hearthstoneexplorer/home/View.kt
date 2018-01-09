package com.example.hearthstoneexplorer.home

import com.facebook.litho.ComponentContext
import com.p69.elma.core.Dispatch
import com.p69.elma.litho.DSL.ElmaLithoView
import com.p69.elma.litho.DSL.children
import com.p69.elma.litho.DSL.column
import com.p69.elma.litho.DSL.text
import io.michaelrocks.optional.*

object HomeUI {
    fun view(model: HomeModel, c: ComponentContext, dispatcher: Dispatch<HomeMsg>): ElmaLithoView =
            column(c) {
                children(c) {
                    text {
                        textSizeDip(14f)
                        when(model.error) {
                            is Optional.None -> text("Hello!")
                            is Optional.Some -> text(model.error.value.message)
                        }
                    }
                }
            }
}