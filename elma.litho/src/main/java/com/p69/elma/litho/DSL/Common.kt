package com.p69.elma.litho.DSL

import com.p69.elma.litho.DSL.layout.ElmaLithoLayout
import com.p69.elma.litho.DSL.widget.ElmaLithoWidgetView

@DslMarker
annotation class DSL

sealed class ElmaLithoView {
    data class Layout(val view: ElmaLithoLayout) : ElmaLithoView()
    data class Widget(val view: ElmaLithoWidgetView) : ElmaLithoView()
}