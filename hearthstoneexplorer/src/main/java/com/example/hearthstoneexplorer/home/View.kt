package com.example.hearthstoneexplorer.home

import android.graphics.Color
import android.support.v7.widget.OrientationHelper
import com.facebook.litho.ComponentContext
import com.facebook.litho.widget.LinearLayoutInfo
import com.facebook.litho.widget.RecyclerBinder
import com.p69.elma.core.Dispatch
import com.p69.elma.litho.DSL.*
import io.michaelrocks.optional.Optional
import kotlinx.coroutines.experimental.launch

object HomeUI {
    fun view(model: HomeModel, c: ComponentContext, dispatcher: Dispatch<HomeMsg>): ElmaLithoView =
            column(c) {
                children(c) {
                    editText {
                        textSizeDip(16f)
                        onTextChanged { t -> launch { dispatcher(HomeMsg.OnTextQueryChanged(t)) }}
                        heightDip(45f)
                    }
                    text {
                        textSizeDip(16f)
                        textColor(Color.RED)
                        when(model.error) {
                            is Optional.Some -> text(model.error.value.message)
                            is Optional.None -> text("")
                        }
                    }
                    listSection {
                        items {
                            repeat(5) {
                                text {
                                    textSizeDip(16f)
                                    textColor(Color.RED)
                                    text("I'm list item")
                                }
                            }
                        }
                    }
                }
            }
}