package com.p69.elma.litho.DSL.layout

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.p69.elma.litho.DSL.DSL
import com.p69.elma.litho.DSL.ElmaLithoView


@DSL
abstract class ElmaLithoLayout(val ctx: ComponentContext) {
    abstract val builder: ComponentLayout.ContainerBuilder

    private fun addChild(child: Component.Builder<*, *>) {
        builder.child(child.build())
    }

    private fun addChild(child: ComponentLayout.Builder) {
        builder.child(child.build())
    }

    fun child(child: ElmaLithoView) {
        when (child) {
            is ElmaLithoView.Widget -> addChild(child.builder)
            is ElmaLithoView.Layout -> addChild(child.builder)
        }
    }
}

fun ElmaLithoLayout.columnLayout(init: ColumnLayout.() -> Unit) {
    val layout = ColumnLayout(this.ctx)
    layout.init()
    val view = ElmaLithoView.Layout(layout.builder)
    this.child(view)

}

fun ElmaLithoLayout.rowLayout(init: RowLayout.() -> Unit) {
    val layout = RowLayout(this.ctx)
    layout.init()
    val view = ElmaLithoView.Layout(layout.builder)
    this.child(view)
}