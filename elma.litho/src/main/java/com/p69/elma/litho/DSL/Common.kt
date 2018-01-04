package com.p69.elma.litho.DSL

import com.facebook.litho.Component
import com.facebook.litho.ComponentLayout

@DslMarker
annotation class DSL

sealed class ElmaLithoView {
    data class Layout(val builder: ComponentLayout.ContainerBuilder) : ElmaLithoView()
    data class Widget(val builder: Component.Builder<*, *>) : ElmaLithoView()
}