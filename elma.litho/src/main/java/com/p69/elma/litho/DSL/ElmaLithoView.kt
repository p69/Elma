package com.p69.elma.litho.DSL

import com.facebook.litho.Component
import com.facebook.litho.ComponentLayout

sealed class ElmaLithoView {
    data class Layout(val builder: ComponentLayout.ContainerBuilder) : ElmaLithoView()
    data class Widget(val builder: Component.Builder<*, *>) : ElmaLithoView()
}