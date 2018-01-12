package com.p69.elma.litho.DSL

import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.FromEvent
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.common.DataDiffSection
import com.facebook.litho.sections.common.RenderEvent
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.RenderInfo
import com.p69.elma.litho.ElmaLithoView
import com.p69.elma.litho.ElmaViewComponent
import com.p69.elma.litho.ElmaViewCreator

@GroupSectionSpec
class ListSectionSpec {
    companion object {
        @JvmStatic
        @OnCreateChildren
        fun onCreateChildren(c: SectionContext, @Prop data: Container): Children {
            val builder = Children.create()
            val dataDiffBuilder = DataDiffSection.create<ElmaLithoView>(c)
            dataDiffBuilder
                    .data(data.children)
                    .renderEventHandler(ListSection.onRenderItem(c))
            builder.child(dataDiffBuilder)
            return builder.build()
        }

        @OnEvent(RenderEvent::class)
        @JvmStatic
        fun onRenderItem(c: SectionContext, @FromEvent model: ElmaLithoView): RenderInfo {
            val viewCreator = ElmaViewCreator.Some { model }
            return ComponentRenderInfo.create().component(ElmaViewComponent.create(c).viewCreator(viewCreator).build()).build()
        }
    }
}

fun listSection(ctx: ComponentContext, init: ListSectionWorkaround.() -> Unit): ElmaLithoView {
    val builder = ListSectionWorkaround(ctx)
    builder.init()
    return ElmaLithoView.Section(builder.builder)
}

fun Container.listSection(init: ListSectionWorkaround.() -> Unit) {
    val builder = ListSectionWorkaround(ctx)
    builder.init()
    this.children.add(ElmaLithoView.Section(builder.builder))
}

fun ListSectionWorkaround.items(init: Container.() -> Unit) {
    val items = Container(this.componentContext)
    items.init()
    this.data(items)
}

