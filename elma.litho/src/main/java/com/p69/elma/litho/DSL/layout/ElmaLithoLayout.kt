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

    fun ElmaLithoLayout.columnLayout(init: ColumnLayout.() -> Unit): ElmaLithoView {
        val layout = ColumnLayout(ctx)
        layout.init()
        addChild(layout.builder)
        return ElmaLithoView.Layout(layout)
    }

    fun ElmaLithoLayout.rowLayout(init: RowLayout.() -> Unit): ElmaLithoView {
        val layout = RowLayout(ctx)
        layout.init()
        addChild(layout.builder)
        return ElmaLithoView.Layout(layout)
    }

    fun ElmaLithoLayout.child(child: ElmaLithoView) {
        when (child) {
            is ElmaLithoView.Widget -> addChild(child.view.builder)
            is ElmaLithoView.Layout -> addChild(child.view.builder)
        }
    }
}