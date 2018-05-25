package com.p69.elma.litho.DSL

import android.support.v7.util.DiffUtil
import android.support.v7.widget.OrientationHelper
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.widget.*
import com.p69.elma.litho.ElmaLayoutViewComponent
import com.p69.elma.litho.ElmaLithoView

data class ListContainer(val c: ComponentContext, val recycler: Recycler.Builder, val binder: RecyclerBinder)

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

fun Container.list(
        orientation: Int = OrientationHelper.VERTICAL,
        reverseLayout: Boolean = false,
        rangeRatio: Float = 4f,
        canPrefetchDisplayLists: Boolean = false,
        canCacheDrawingDisplayLists: Boolean = false,
        isCircular: Boolean = false,
        init: ListContainer.()->Unit) {
    val recyclerBuilder = Recycler.create(ctx)
    val binder = RecyclerBinder.Builder()
            .layoutInfo(LinearLayoutInfo(ctx, orientation, reverseLayout))
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

internal fun <T> RecyclerBinder.applyDiff(
        ctx: ComponentContext,
        old: List<T>, newItems: List<T>,
        func: (T) -> ElmaLithoView,
        compareIds: (T, T) -> Boolean) {
    val renderer = RecyclerBinderUpdateCallback.ComponentRenderer<T> { x, _ ->
        when (x) {
            is ElmaLithoView.Widget -> ComponentRenderInfo.create().component(x.builder.build()).build()
            is ElmaLithoView.Layout -> ComponentRenderInfo.create().component(ElmaLayoutViewComponent.create(ctx).layout(x).build()).build()
            else -> ComponentRenderInfo.create().component(Text.create(ctx).text(x.toString()).build()).build()
        }

    }
    val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun getOldListSize(): Int = old.size
        override fun getNewListSize(): Int = newItems.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                compareIds(old[oldItemPosition], newItems[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition] == newItems[newItemPosition]
    })
    val callback = RecyclerBinderUpdateCallback.acquire(
            old.size, newItems, renderer, this)
    diffResult.dispatchUpdatesTo(callback)
    callback.applyChangeset()
    RecyclerBinderUpdateCallback.release(callback)
}

//class ContextualRecyclerBinder<T>(
//        compFactory: (T) -> Contextual<Component.ContainerBuilder<*>>,
//        private val compareId: (T, T) -> Boolean,
//        private val factory: RecyclerBinder.Builder.() -> Unit = {}) {
//
//    private val func = createItemComponent(compFactory)
//    private var wrapper: BinderWrapper<T>? = null
//
//    fun getBinder(context: ComponentContext): RecyclerBinder {
//        val w = wrapper
//        return if (w != null) w.binder else {
//            val b = RecyclerBinder.Builder().apply(factory).build(context)
//            wrapper = BinderWrapper(b, emptyList(), context)
//            b
//        }
//    }
//
//    fun copy(newItems: List<T>): ContextualRecyclerBinder<T> {
//        val w = wrapper
//        if (w != null) {
//            val binder = w.binder
//            val oldItems = w.items
//            val context = w.context
//
//            binder.applyDiff(
//                    oldItems,
//                    newItems,
//                    { func(it)(context) },
//                    compareId)
//
//            wrapper = w.copy(items = newItems)
//        }
//        return this
//    }
//
//    data class BinderWrapper<T>(
//            val binder: RecyclerBinder,
//            val items: List<T>,
//            val context: ComponentContext)
//}