package com.p69.elma.litho.DSL.layout

import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Row
import com.p69.elma.litho.DSL.ElmaLithoView

class RowLayout(ctx: ComponentContext) : ElmaLithoLayout(ctx) {
    private val rowBuilder = Row.create(ctx)

    override val builder: ComponentLayout.ContainerBuilder get() = rowBuilder
}

fun rowLayout(ctx: ComponentContext, init: RowLayout.() -> Unit): ElmaLithoView {
    val layout = RowLayout(ctx)
    layout.init()
    return ElmaLithoView.Layout(layout)
}