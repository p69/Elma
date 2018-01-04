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
import com.p69.elma.litho.DSL.widget


@LayoutSpec
class ScreenWithTransitionSpec {
    companion object {
        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(ctx: ComponentContext, @Prop screen: ElmaLithoView, @Prop screenKey: String): ComponentLayout {
            val layout = when (screen) {
                is ElmaLithoView.Widget -> Column.create(ctx).child(screen.builder)
                is ElmaLithoView.Layout -> Column.create(ctx).child(screen.builder)
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

fun screenWithTransition(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: ScreenWithTransition.Builder.() -> Unit): ElmaLithoView
        = widget({ ScreenWithTransition.create(ctx, defStyleAttr, defStyleRes) }, init)