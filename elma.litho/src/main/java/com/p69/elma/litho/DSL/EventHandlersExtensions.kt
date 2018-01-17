package com.p69.elma.litho.DSL

import com.facebook.litho.*
import com.facebook.litho.widget.EditText
import com.facebook.litho.widget.TextChangedEvent
import com.p69.elma.litho.throttle
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch

fun <C : Component<out Component<*>>, B : Component.Builder<C, B>> (Component.Builder<C, B>).onClick(handler: (ClickEvent) -> Unit): Component.Builder<*, *> {
    val dispatcher = createEventDispatcher(handler)
    this.clickHandler(EventHandler(dispatcher, "elma click handler", 1, null))
    return this
}

fun <C : Component<out Component<*>>, B : Component.Builder<C, B>> (Component.Builder<C, B>).onLongClick(handler: (LongClickEvent) -> Unit): Component.Builder<*, *> {
    val dispatcher = createEventDispatcher(handler)
    this.longClickHandler(EventHandler(dispatcher, "elma long click handler", 1, null))
    return this
}

fun <C : Component<out Component<*>>, B : Component.Builder<C, B>> (Component.Builder<C, B>).onFocusChange(handler: (FocusChangedEvent) -> Unit): Component.Builder<*, *> {
    val dispatcher = createEventDispatcher(handler)
    this.focusChangeHandler(EventHandler(dispatcher, "elma focus changed handler", 1, null))
    return this
}

fun <C : Component<out Component<*>>, B : Component.Builder<C, B>> (Component.Builder<C, B>).onTouch(handler: (TouchEvent) -> Unit): Component.Builder<*, *> {
    val dispatcher = createEventDispatcher(handler)
    this.touchHandler(EventHandler(dispatcher, "elma touch handler", 1, null))
    return this
}

fun <C : Component<out Component<*>>, B : Component.Builder<C, B>> (Component.Builder<C, B>).onInterceptTouch(handler: (InterceptTouchEvent) -> Unit): Component.Builder<*, *> {
    val dispatcher = createEventDispatcher(handler)
    this.interceptTouchHandler(EventHandler(dispatcher, "elma intercept touch handler", 1, null))
    return this
}

fun <C : Component<out Component<*>>, B : Component.Builder<C, B>> (Component.Builder<C, B>).onVisible(handler: (VisibleEvent) -> Unit): Component.Builder<*, *> {
    val dispatcher = createEventDispatcher(handler)
    this.visibleHandler(EventHandler(dispatcher, "elma visible handler", 1, null))
    return this
}

fun <C : Component<out Component<*>>, B : Component.Builder<C, B>> (Component.Builder<C, B>).onInvisible(handler: (InvisibleEvent) -> Unit): Component.Builder<*, *> {
    val dispatcher = createEventDispatcher(handler)
    this.invisibleHandler(EventHandler(dispatcher, "elma invisible handler", 1, null))
    return this
}

fun <C : Component<out Component<*>>, B : Component.Builder<C, B>> (Component.Builder<C, B>).onFocusVisible(handler: (FocusedVisibleEvent) -> Unit): Component.Builder<*, *> {
    val dispatcher = createEventDispatcher(handler)
    this.focusedHandler(EventHandler(dispatcher, "elma focus visible handler", 1, null))
    return this
}

fun <C : Component<out Component<*>>, B : Component.Builder<C, B>> (Component.Builder<C, B>).onUnfocusVisible(handler: (UnfocusedVisibleEvent) -> Unit): Component.Builder<*, *> {
    val dispatcher = createEventDispatcher(handler)
    this.unfocusedHandler(EventHandler(dispatcher, "elma unfocus visible handler", 1, null))
    return this
}

fun <C : Component<out Component<*>>, B : Component.Builder<C, B>> (Component.Builder<C, B>).onFullImpressionVisible(handler: (FullImpressionVisibleEvent) -> Unit): Component.Builder<*, *> {
    val dispatcher = createEventDispatcher(handler)
    this.fullImpressionHandler(EventHandler(dispatcher, "elma full impression visible handler", 1, null))
    return this
}

fun (EditText.Builder).onTextChanged(handler: (String) -> Unit): EditText.Builder {
    val dispatcher = createEventDispatcher<TextChangedEvent>{ evt->
        val txt = evt.text
        if (!txt.isNullOrEmpty()) {
            handler(txt)
        }
    }
    this.textChangedEventHandler(com.facebook.litho.EventHandler<TextChangedEvent>(dispatcher, "elma text changed handler", 1, null))
    return this
}

fun (EditText.Builder).onTextChangedWithThrottling(wait: Long = 300, handler: (String) -> Unit): EditText.Builder {
    val channel = Channel<String>()
    launch {
        channel.throttle(wait).consumeEach(handler)
    }
    val dispatcher = createEventDispatcher<TextChangedEvent> { evt ->
        val txt = evt.text
        if (!channel.isClosedForReceive && !txt.isNullOrEmpty()) {
            channel.offer(txt)
        }
    }
    this.textChangedEventHandler(com.facebook.litho.EventHandler<TextChangedEvent>(dispatcher, "elma text changed handler", 1, null))
    return this
}

inline fun <reified TEvent> createEventDispatcher(crossinline handler: (evt:TEvent) -> Unit): HasEventDispatcher {
    val clickDispatcher = EventDispatcher { _, event ->
        if (event is TEvent) {
            handler(event)
        }
        Any()
    }

    return HasEventDispatcher { clickDispatcher }
}