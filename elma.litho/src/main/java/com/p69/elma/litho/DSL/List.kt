package com.p69.elma.litho.DSL

import android.support.v7.widget.OrientationHelper
import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
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
                }
            }
            return builder.child(Recycler.create(c).binder(recyclerBinder).pullToRefresh(false)).build()
        }
    }

}

data class ListContainer(val c: ComponentContext, val recycler: Recycler.Builder, val binder: RecyclerBinder)

fun Container.list(init: ListComponent.Builder.() -> Unit) {
    val workaround = ListComponent.create(ctx) //ListWorkaround(ctx)
    workaround.init()
    this.children.add(ElmaLithoView.Widget(workaround))
}

fun ListComponent.Builder.items(init: Container.() -> Unit) {
    val container = Container(this.mContext)
    container.init()
    this.items(container.children)
}

fun Container.grid(
        spanCount: Int,
        orientation: Int = OrientationHelper.VERTICAL,
        reverseLayout: Boolean = false,
        rangeRatio: Float = 4f,
        canPrefetchDisplayLists: Boolean = false,
        canCacheDrawingDisplayLists: Boolean = false,
        isCircular: Boolean = false,
        init: ListContainer.()->Unit) {
    val recyclerBuilder = Recycler.create(ctx)
    val binder = RecyclerBinder.Builder()
            .layoutInfo(GridLayoutInfo(ctx, spanCount, orientation, reverseLayout))
            .rangeRatio(rangeRatio)
            .canPrefetchDisplayLists(canPrefetchDisplayLists)
            .canCacheDrawingDisplayLists(canCacheDrawingDisplayLists)
            .isCircular(isCircular)
            .build(ctx)
    val list = ListContainer(ctx, recyclerBuilder, binder)
    list.init()
    recyclerBuilder.binder(binder)
    children.add(ElmaLithoView.Widget(recyclerBuilder))
}

fun ListContainer.settings(init: Recycler.Builder.()->Unit) {
    this.recycler.init()
}

fun ListContainer.items(init: Container.()->Unit) {
    val container = Container(c)
    container.init()
    container.children.forEach { view ->
        when (view) {
            is ElmaLithoView.Widget -> binder.appendItem(view.builder.build())
            is ElmaLithoView.Layout -> binder.appendItem(ElmaLayoutViewComponent.create(c).layout(view).build())
        }
    }
}