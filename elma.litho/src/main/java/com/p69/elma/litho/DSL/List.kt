package com.p69.elma.litho.DSL

import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.facebook.litho.widget.GridLayoutInfo
import com.facebook.litho.widget.Recycler
import com.facebook.litho.widget.RecyclerBinder
import com.p69.elma.litho.ElmaLayoutViewComponent
import com.p69.elma.litho.ElmaLithoView

@LayoutSpec
class ListComponentSpec {

    companion object {
        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(c: ComponentContext, @Prop items: List<ElmaLithoView>): ComponentLayout {
            val builder = Column.create(c)
            val recyclerBinder = RecyclerBinder.Builder().layoutInfo(GridLayoutInfo(c, 2)).build(c)
            items.forEach { view ->
                when (view) {
                    is ElmaLithoView.Widget -> recyclerBinder.appendItem(view.builder.build())
                    is ElmaLithoView.Layout -> recyclerBinder.appendItem(ElmaLayoutViewComponent.create(c).layout(view).build())
                    is ElmaLithoView.Section -> recyclerBinder.appendItem(RecyclerCollectionComponent.create(c).disablePTR(true).section(view.builder).build())
                }
            }
            return builder.child(Recycler.create(c).binder(recyclerBinder).pullToRefresh(false)).build()
        }
    }

}

fun Container.list(init: ListWorkaround.() -> Unit) {
    val workaround = ListWorkaround(ctx)
    workaround.init()
    this.children.add(ElmaLithoView.Widget(workaround.builder))
}

fun ListWorkaround.items(init: Container.() -> Unit) {
    val container = Container(this.componentContext)
    container.init()
    this.items(container.children)
}