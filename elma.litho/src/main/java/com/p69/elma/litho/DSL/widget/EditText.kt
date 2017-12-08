package com.p69.elma.litho.DSL.widget

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.widget.EditText
import com.p69.elma.litho.DSL.ElmaLithoView
import com.p69.elma.litho.DSL.layout.ElmaLithoLayout
import com.p69.elma.litho.DSL.textChangedEventHandler

class EditTextView(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0) : ElmaLithoWidgetView(ctx) {
    private val editTextBuilder: EditText.Builder = EditText.create(ctx, defStyleAttr, defStyleRes)
    override val builder: Component.Builder<*, *> = editTextBuilder

    var editable: Boolean = true
        set(value) {
            editTextBuilder.editable(value)
        }

    var text: String = ""
        set(value) {
            editTextBuilder.text(value)
        }

    var textSizeDip: Float = 0f
        set(value) {
            editTextBuilder.textSizeDip(value)
        }

    var onTextChanged: (String) -> Unit = { _ -> }
        set(value) {
            editTextBuilder.textChangedEventHandler(value)
        }
}

fun ElmaLithoLayout.editText(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: EditTextView.() -> Unit): ElmaLithoView {
    val editText = EditTextView(ctx, defStyleAttr, defStyleRes)
    editText.init()
    val view = ElmaLithoView.Widget(editText)
    this.child(view)
    return view
}

fun editText(ctx: ComponentContext, defStyleAttr: Int = 0, defStyleRes: Int = 0, init: EditTextView.() -> Unit): ElmaLithoView {
    val editText = EditTextView(ctx, defStyleAttr, defStyleRes)
    editText.init()
    return ElmaLithoView.Widget(editText)
}