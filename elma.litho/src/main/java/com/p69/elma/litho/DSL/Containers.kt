package com.p69.elma.litho.DSL

import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Row

class Container(val ctx: ComponentContext) {
    val children = mutableListOf<ElmaLithoView>()
}

fun column(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: ComponentLayout.ContainerBuilder.() -> Unit): ElmaLithoView {
    val builder = Column.create(ctx, defStyleAttr, defStyleRes)
    builder.init()
    return ElmaLithoView.Layout(builder)
}

fun row(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: ComponentLayout.ContainerBuilder.() -> Unit): ElmaLithoView {
    val builder = Row.create(ctx, defStyleAttr, defStyleRes)
    builder.init()
    return ElmaLithoView.Layout(builder)
}

fun ComponentLayout.ContainerBuilder.children(ctx: ComponentContext, init: Container.() -> Unit) {
    val container = Container(ctx)
    container.init()
    container.children.forEach { child ->
        when (child) {
            is ElmaLithoView.Widget -> this.child(child.builder)
            is ElmaLithoView.Layout -> this.child(child.builder)
        }
    }
}

fun Container.include(view: ElmaLithoView) {
    this.children.add(view)
}

fun Container.column(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: ComponentLayout.ContainerBuilder.() -> Unit) {
    val builder = Column.create(this.ctx, defStyleAttr, defStyleRes)
    builder.init()
    val view = ElmaLithoView.Layout(builder)
    this.children.add(view)
}

fun Container.row(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: ComponentLayout.ContainerBuilder.() -> Unit) {
    val builder = Row.create(this.ctx, defStyleAttr, defStyleRes)
    builder.init()
    val view = ElmaLithoView.Layout(builder)
    this.children.add(view)
}