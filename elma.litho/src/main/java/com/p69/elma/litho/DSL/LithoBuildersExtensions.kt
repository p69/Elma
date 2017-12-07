package com.p69.elma.litho.DSL

import com.facebook.litho.Component
import com.facebook.litho.EventDispatcher
import com.facebook.litho.EventHandler
import com.facebook.litho.HasEventDispatcher
import com.facebook.litho.widget.EditText
import com.facebook.litho.widget.TextChangedEvent

fun (Component.Builder<*, *>).clickHandler(handler: () -> Unit): Component.Builder<*, *> {
    val dispatcher = createEventDispatcher(handler)
    this.clickHandler(EventHandler(dispatcher, "elmishClickHandler", 1, null))
    return this
}

fun (EditText.Builder).textChangedEventHandler(handler: (String) -> Unit): Component.Builder<*, *> {
    val dispatcher = createEventDispatcher(handler)
    this.textChangedEventHandler(com.facebook.litho.EventHandler<TextChangedEvent>(dispatcher, "elmishTextChangedHandler", 1, null))
    return this
}

fun createEventDispatcher(handler: () -> Unit): HasEventDispatcher {
    val clickDispatcher = EventDispatcher { _, _ ->
        handler()
        Any()
    }

    return HasEventDispatcher { clickDispatcher }
}

fun createEventDispatcher(handler: (String) -> Unit): HasEventDispatcher {
    val clickDispatcher = EventDispatcher { _, eventState ->
        if (eventState is TextChangedEvent) {
            handler(eventState.text)
        }

        Any()
    }

    return HasEventDispatcher { clickDispatcher }
}