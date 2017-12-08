package com.example.pavelshilyagov.tryelmish

import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Transition
import com.facebook.litho.animation.AnimatedProperties
import com.facebook.litho.animation.DimensionValue
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnCreateTransition
import com.facebook.litho.annotations.Prop
import com.p69.elma.litho.DSL.ElmaLithoView
import com.p69.elma.litho.DSL.layout.ElmaLithoLayout
import com.p69.elma.litho.DSL.widget.ElmaLithoWidgetView


@LayoutSpec
class ScreenWithTransitionSpec {
    companion object {
        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(ctx: ComponentContext, @Prop screen: ElmaLithoView, @Prop screenKey: String): ComponentLayout {
            val layout = when (screen) {
                is ElmaLithoView.Widget -> Column.create(ctx).child(screen.view.builder)
                is ElmaLithoView.Layout -> Column.create(ctx).child(screen.view.builder)
            }
            return layout.transitionKey(screenKey).build()
        }

        @JvmStatic
        @OnCreateTransition
        fun onCreateTransition(c: ComponentContext): Transition {
            return Transition
                    .create(Transition.allKeys())
                    .animate(AnimatedProperties.ALPHA)
                    .appearFrom(0f)
                    .disappearTo(0f)
                    .animate(AnimatedProperties.X)
                    .appearFrom(DimensionValue.offsetDip(c, -100))
                    .disappearTo(DimensionValue.offsetDip(c,100))
        }
    }
}

object ScreenWithTransitionDSL {
    class ScreenWithTransitionView(ctx: ComponentContext) : ElmaLithoWidgetView(ctx) {
        override val builder: ScreenWithTransition.Builder = ScreenWithTransition.create(ctx)

        fun ScreenWithTransitionView.screen(screen: ElmaLithoView) {
            builder.screen(screen)
        }

        var key: String = ""
            set(value) {
                builder.screenKey(value)
            }
    }

    fun ElmaLithoLayout.screenWithTransition(init:ScreenWithTransitionView.()->Unit) : ElmaLithoView {
        val screenContainer = ScreenWithTransitionView(ctx)
        screenContainer.init()
        val view = ElmaLithoView.Widget(screenContainer)
        this.child(view)
        return view
    }

    fun screenWithTransition(ctx: ComponentContext, init:ScreenWithTransitionView.()->Unit) : ElmaLithoView {
        val view = ScreenWithTransitionView(ctx)
        view.init()
        return ElmaLithoView.Widget(view)
    }
}