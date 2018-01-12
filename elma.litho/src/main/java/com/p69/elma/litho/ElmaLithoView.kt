package com.p69.elma.litho

import com.facebook.litho.Component
import com.facebook.litho.ComponentLayout
import com.facebook.litho.sections.Section as LithoSection

sealed class ElmaLithoView {
    data class Layout(val builder: ComponentLayout.ContainerBuilder) : ElmaLithoView()
    data class Widget(val builder: Component.Builder<*, *>) : ElmaLithoView()
    data class Section(val builder: LithoSection.Builder<*, *>) : ElmaLithoView()
}