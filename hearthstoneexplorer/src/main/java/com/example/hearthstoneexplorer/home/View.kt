package com.example.hearthstoneexplorer.home

import android.graphics.Color
import com.example.hearthstoneexplorer.R
import com.example.hearthstoneexplorer.widgets.picassoImage
import com.facebook.litho.ComponentContext
import com.p69.elma.core.Dispatch
import com.p69.elma.litho.DSL.*
import com.p69.elma.litho.ElmaLithoView
import io.michaelrocks.optional.Optional

object HomeUI {
    fun view(model: HomeModel,
             c: ComponentContext,
             dispatcher: Dispatch<HomeMsg>): ElmaLithoView =
            column(c) {
                flexGrow(1f)
                backgroundColor(0xf1e4ae)
                children(c) {
                    editText {
                        textSizeDip(16f)
                        onTextChangedWithThrottling(1000) { t -> dispatcher(HomeMsg.OnTextQueryChanged(t)) }
                        heightDip(45f)
                    }
                    text {
                        textSizeDip(16f)
                        textColor(Color.RED)
                        when (model.error) {
                            is Optional.Some -> text(model.error.value.message)
                            is Optional.None -> text("")
                        }
                    }
                    if (model.isLoading) {
                        progress {
                            widthDip(50f)
                            heightDip(50f)
                        }
                    } else {
                        grid(2) {
                            settings {
                                flexGrow(1f)
                                backgroundColor(Color.GRAY)
                            }
                            items {
                                for (card in model.cards) {
                                    picassoImage {
                                        imageUrl(card.img)
                                        placeholderImageRes(R.drawable.cardback_123)
                                    }
                                }
                            }
                        }
                    }
                }
            }
}