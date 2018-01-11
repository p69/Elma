package com.p69.elma.litho.DSL

import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.*
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.common.DataDiffSection
import com.facebook.litho.sections.common.RenderEvent
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.RenderInfo

//
//class ListItems(val ctx: ComponentContext) {
//    val items = mutableListOf<ElmaLithoView.Widget>()
//}



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
        fun onRenderItem(c: SectionContext, @FromEvent model: ElmaLithoView): RenderInfo =
                ComponentRenderInfo.create().component(ListItemComponent.create(c).view(model).build()).build()
    }
}

@LayoutSpec
class ListItemComponentSpec {
    companion object {
        @JvmStatic
        @OnCreateLayout
        fun onCreateLayot(context: ComponentContext, @Prop view: ElmaLithoView): ComponentLayout = when(view) {
            is ElmaLithoView.Widget -> Column.create(context).child(view.builder).build()
            is ElmaLithoView.Layout -> Column.create(context).child(view.builder).build()
        }
    }

}

fun listSection(ctx: ComponentContext, init: ListSectionWorkaround.() -> Unit): ElmaLithoView {
    val builder = ListSectionWorkaround(ctx)
    builder.init()
    return ElmaLithoView.Widget(builder.buildRecyclerCollection())
}

fun Container.listSection(init: ListSectionWorkaround.() -> Unit) {
    val builder = ListSectionWorkaround(ctx)
    builder.init()
    this.children.add(ElmaLithoView.Widget(builder.buildRecyclerCollection()))
}

fun ListSectionWorkaround.items(init: Container.() -> Unit) {
    val items = Container(this.componentContext)
    items.init()
    this.data(items)
}

