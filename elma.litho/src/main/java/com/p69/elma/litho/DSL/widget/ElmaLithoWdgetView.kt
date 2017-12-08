package com.p69.elma.litho.DSL.widget

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
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
}