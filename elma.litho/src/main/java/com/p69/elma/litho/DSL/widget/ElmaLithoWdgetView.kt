package com.p69.elma.litho.DSL.widget

import android.graphics.drawable.Drawable
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.reference.Reference
import com.facebook.yoga.YogaEdge
import com.p69.elma.litho.DSL.DSL
import com.p69.elma.litho.DSL.clickHandler

@DSL
abstract class ElmaLithoWidgetView(val ctx: ComponentContext) {
    abstract val builder: Component.Builder<*, *>
    var flexGrow: Float = 0f
        set(value) {
            builder.flexGrow(value)
        }

    var enabled: Boolean = true
        set(value) {
            builder.enabled(value)
        }

    var onClick: () -> Unit = {}
        set(value) {
            builder.clickHandler(value)
        }

    var backgroundAttr: Int = 0
        set(value) {
            builder.backgroundAttr(value)
        }

    var backgroundRes: Int = 0
        set(value) {
            builder.backgroundRes(value)
        }

    var background: Reference<Drawable>? = null
        set(value) {
            builder.background(value)
        }

    var backgroundDrawable: Drawable? = null
        set(value) {
            builder.background(value)
        }

    fun paddingDip(edge: YogaEdge, dip: Float) {
        builder.paddingDip(edge, dip)
    }
}