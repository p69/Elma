package com.p69.elma.litho.DSL.layout

import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.p69.elma.litho.DSL.ElmaLithoView

class ColumnLayout(ctx: ComponentContext) : ElmaLithoLayout(ctx) {
    private val columnBuilder = Column.create(ctx)

    override val builder: ComponentLayout.ContainerBuilder get() = columnBuilder
}

fun columnLayout(ctx: ComponentContext, init: ColumnLayout.() -> Unit): ElmaLithoView {
    val layout = ColumnLayout(ctx)
    layout.init()
    return ElmaLithoView.Layout(layout)
}