package com.p69.elma.litho.DSL.widget

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.widget.Text
import com.p69.elma.litho.DSL.ElmaLithoView
import com.p69.elma.litho.DSL.layout.ElmaLithoLayout

class TextView(ctx: ComponentContext) : ElmaLithoWidgetView(ctx) {
    private val textBuilder = Text.create(ctx)
    override val builder: Component.Builder<*, *> = textBuilder

    var text: String = ""
        set(value) {
            textBuilder.text(value)
        }

    var textSizeDip: Float = 0f
        set(value) {
            textBuilder.textSizeDip(value)
        }

    var isSingleLine: Boolean = false
        set(value) {
            textBuilder.isSingleLine(value)
        }

    var maxLines: Int = 0
        set(value) {
            textBuilder.maxLines(value)
        }
}

fun ElmaLithoLayout.text(init: TextView.()->Unit) : ElmaLithoView {
    val text = TextView(ctx)
    text.init()
    val view = ElmaLithoView.Widget(text)
    this.child(view)
    return view
}

fun text(ctx: ComponentContext, init: TextView.()->Unit) : ElmaLithoView {
    val text = TextView(ctx)
    text.init()
    return ElmaLithoView.Widget(text)
}