package com.p69.elma.litho.DSL

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.widget.*
import com.p69.elma.litho.ElmaLithoView


fun <C : Component<out Component<*>>, B : Component.Builder<C, B>> widget(
        create: () -> B,
        init: B.() -> Unit): ElmaLithoView {
    val builder = create()
    builder.init()
    return ElmaLithoView.Widget(builder)
}

// Text
fun Container.text(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: Text.Builder.() -> Unit)
        = this.children.add(widget({ Text.create(this.ctx, defStyleAttr, defStyleRes) }, init))

fun text(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: Text.Builder.() -> Unit): ElmaLithoView
        = widget({ Text.create(ctx, defStyleAttr, defStyleRes) }, init)


// EditText
fun Container.editText(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: EditText.Builder.() -> Unit)
        = this.children.add(widget({ EditText.create(this.ctx, defStyleAttr, defStyleRes) }, init))

fun editText(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: EditText.Builder.() -> Unit)
        = widget({ EditText.create(ctx, defStyleAttr, defStyleRes) }, init)


// Card
fun Container.card(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: Card.Builder.() -> Unit)
        = this.children.add(widget({ Card.create(this.ctx, defStyleAttr, defStyleRes) }, init))

fun card(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: Card.Builder.() -> Unit)
        = widget({ Card.create(ctx, defStyleAttr, defStyleRes) }, init)


// HorizontalScroll
fun Container.horizontalScroll(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: HorizontalScroll.Builder.() -> Unit)
        = this.children.add(widget({ HorizontalScroll.create(this.ctx, defStyleAttr, defStyleRes) }, init))

fun horizontalScroll(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: HorizontalScroll.Builder.() -> Unit)
        = widget({ HorizontalScroll.create(ctx, defStyleAttr, defStyleRes) }, init)


// VerticalScroll
fun Container.verticalScroll(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: VerticalScroll.Builder.() -> Unit)
        = this.children.add(widget({ VerticalScroll.create(this.ctx, defStyleAttr, defStyleRes) }, init))

fun verticalScroll(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: VerticalScroll.Builder.() -> Unit)
        = widget({ VerticalScroll.create(ctx, defStyleAttr, defStyleRes) }, init)


// Image
fun Container.image(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: Image.Builder.() -> Unit)
        = this.children.add(widget({ Image.create(this.ctx, defStyleAttr, defStyleRes) }, init))

fun image(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: Image.Builder.() -> Unit)
        = widget({ Image.create(ctx, defStyleAttr, defStyleRes) }, init)


// Recycler
fun Container.recycler(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: Recycler.Builder.() -> Unit)
        = this.children.add(widget({ Recycler.create(this.ctx, defStyleAttr, defStyleRes) }, init))

fun recycler(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: Recycler.Builder.() -> Unit)
        = widget({ Recycler.create(ctx, defStyleAttr, defStyleRes) }, init)


// SolidColor
fun Container.solidColor(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: SolidColor.Builder.() -> Unit)
        = this.children.add(widget({ SolidColor.create(this.ctx, defStyleAttr, defStyleRes) }, init))

fun solidColor(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: SolidColor.Builder.() -> Unit)
        = widget({ SolidColor.create(ctx, defStyleAttr, defStyleRes) }, init)


// Progress
fun Container.progress(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: Progress.Builder.() -> Unit)
        = this.children.add(widget({ Progress.create(this.ctx, defStyleAttr, defStyleRes) }, init))

fun progress(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: Progress.Builder.() -> Unit)
        = widget({ Progress.create(ctx, defStyleAttr, defStyleRes) }, init)