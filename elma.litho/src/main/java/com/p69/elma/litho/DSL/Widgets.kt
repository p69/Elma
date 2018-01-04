package com.p69.elma.litho.DSL

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.widget.EditText
import com.facebook.litho.widget.Text
import com.p69.elma.litho.DSL.layout.ElmaLithoLayout


fun <C : Component<out Component<*>>, B : Component.Builder<C, B>> widget(
        create: () -> B,
        init: B.() -> Unit): ElmaLithoView {
    val builder = create()
    builder.init()
    return ElmaLithoView.Widget(builder)
}


fun ElmaLithoLayout.text(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: Text.Builder.() -> Unit)
        = this.child(widget({ Text.create(this.ctx, defStyleAttr, defStyleRes) }, init))


fun text(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: Text.Builder.() -> Unit): ElmaLithoView
        = widget({ Text.create(ctx, defStyleAttr, defStyleRes) }, init)


fun ElmaLithoLayout.editText(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: EditText.Builder.() -> Unit)
        = this.child(widget({ EditText.create(this.ctx, defStyleAttr, defStyleRes) }, init))


fun editText(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: EditText.Builder.() -> Unit)
        = widget({ EditText.create(ctx, defStyleAttr, defStyleRes) }, init)