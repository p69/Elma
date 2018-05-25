package com.p69.elma.litho.DSL

import com.facebook.litho.*
import com.facebook.litho.widget.EditText
import com.facebook.litho.widget.TextChangedEvent
import com.p69.elma.litho.throttle
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch

fun <B : Component.Builder<B>> (Component.Builder<B>).onClick(handler: (ClickEvent) -> Unit): Component.Builder<*> {
    val dispatcher = createEventDispatcher(handler)
    this.clickHandler(EventHandler(dispatcher, 1, null))
    return this
}

fun <B : Component.Builder<B>> (Component.Builder<B>).onLongClick(handler: (LongClickEvent) -> Unit): Component.Builder<*> {
    val dispatcher = createEventDispatcher(handler)
    this.longClickHandler(EventHandler(dispatcher, 1, null))
    return this
}

fun <B : Component.Builder<B>> (Component.Builder<B>).onFocusChange(handler: (FocusChangedEvent) -> Unit): Component.Builder<*> {
    val dispatcher = createEventDispatcher(handler)
    this.focusChangeHandler(EventHandler(dispatcher, 1, null))
    return this
}

fun <B : Component.Builder<B>> (Component.Builder<B>).onTouch(handler: (TouchEvent) -> Unit): Component.Builder<*> {
    val dispatcher = createEventDispatcher(handler)
    this.touchHandler(EventHandler(dispatcher, 1, null))
    return this
}

fun <B : Component.Builder<B>> (Component.Builder<B>).onInterceptTouch(handler: (InterceptTouchEvent) -> Unit): Component.Builder<*> {
    val dispatcher = createEventDispatcher(handler)
    this.interceptTouchHandler(EventHandler(dispatcher, 1, null))
    return this
}

fun <B : Component.Builder<B>> (Component.Builder<B>).onVisible(handler: (VisibleEvent) -> Unit): Component.Builder<*> {
    val dispatcher = createEventDispatcher(handler)
    this.visibleHandler(EventHandler(dispatcher, 1, null))
    return this
}

fun <B : Component.Builder<B>> (Component.Builder<B>).onInvisible(handler: (InvisibleEvent) -> Unit): Component.Builder<*> {
    val dispatcher = createEventDispatcher(handler)
    this.invisibleHandler(EventHandler(dispatcher, 1, null))
    return this
}

fun <B : Component.Builder<B>> (Component.Builder<B>).onFocusVisible(handler: (FocusedVisibleEvent) -> Unit): Component.Builder<*> {
    val dispatcher = createEventDispatcher(handler)
    this.focusedHandler(EventHandler(dispatcher, 1, null))
    return this
}

fun <B : Component.Builder<B>> (Component.Builder<B>).onUnfocusVisible(handler: (UnfocusedVisibleEvent) -> Unit): Component.Builder<*> {
    val dispatcher = createEventDispatcher(handler)
    this.unfocusedHandler(EventHandler(dispatcher, 1, null))
    return this
}

fun <B : Component.Builder<B>> (Component.Builder<B>).onFullImpressionVisible(handler: (FullImpressionVisibleEvent) -> Unit): Component.Builder<*> {
    val dispatcher = createEventDispatcher(handler)
    this.fullImpressionHandler(EventHandler(dispatcher, 1, null))
    return this
}

fun (EditText.Builder).onTextChanged(handler: (String) -> Unit): EditText.Builder {
    val dispatcher = createEventDispatcher<TextChangedEvent> { evt ->
        val txt = evt.text
        if (!txt.isNullOrEmpty()) {
            handler(txt)
        }
    }
    this.textChangedEventHandler(com.facebook.litho.EventHandler<TextChangedEvent>(dispatcher, 1, null))
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
    this.textChangedEventHandler(com.facebook.litho.EventHandler<TextChangedEvent>(dispatcher, 1, null))
    return this
}

inline fun <reified TEvent> createEventDispatcher(crossinline handler: (evt: TEvent) -> Unit): HasEventDispatcher {
    val clickDispatcher = EventDispatcher { _, event ->
        if (event is TEvent) {
            handler(event)
        }
        Any()
    }

    return HasEventDispatcher { clickDispatcher }
}