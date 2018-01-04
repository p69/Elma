package com.p69.elma.litho.DSL.layout

import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Row
import com.p69.elma.litho.DSL.ElmaLithoView

class RowLayout(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0) : ElmaLithoLayout(ctx) {
    private val rowBuilder = Row.create(ctx, defStyleAttr, defStyleRes)

    override val builder: ComponentLayout.ContainerBuilder get() = rowBuilder
}

fun rowLayout(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: RowLayout.() -> Unit): ElmaLithoView {
    val layout = RowLayout(ctx, defStyleAttr, defStyleRes)
    layout.init()
    return ElmaLithoView.Layout(layout.builder)
}