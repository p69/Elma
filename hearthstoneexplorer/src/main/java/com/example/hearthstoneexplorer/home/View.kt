package com.example.hearthstoneexplorer.home

import com.facebook.litho.ComponentContext
import com.p69.elma.core.*
import com.p69.elma.litho.DSL.*

object HomeUI {
    fun view(model: HomeModel, c: ComponentContext, dispatcher: Dispatch<HomeMsg>): ElmaLithoView =
            column(c) {
                children(c) {
                    text {
                        textSizeDip(14f)
                        text("Hello, ${model.msg}")
                    }
                }
            }
}