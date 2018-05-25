package com.p69.elma.litho

import com.facebook.litho.Component

sealed class ElmaLithoView {
    data class Layout(val builder: Component.Builder<*>) : ElmaLithoView()
    data class Widget(val builder: Component.Builder<*>) : ElmaLithoView()
}