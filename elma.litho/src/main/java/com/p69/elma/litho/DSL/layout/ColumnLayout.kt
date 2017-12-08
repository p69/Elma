package com.p69.elma.litho.DSL.layout

import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.p69.elma.litho.DSL.ElmaLithoView

class ColumnLayout(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0) : ElmaLithoLayout(ctx) {
    private val columnBuilder = Column.create(ctx, defStyleAttr, defStyleRes)

    override val builder: ComponentLayout.ContainerBuilder get() = columnBuilder
}

fun columnLayout(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: ColumnLayout.() -> Unit): ElmaLithoView {
    val layout = ColumnLayout(ctx, defStyleAttr, defStyleRes)
    layout.init()
    return ElmaLithoView.Layout(layout)
}