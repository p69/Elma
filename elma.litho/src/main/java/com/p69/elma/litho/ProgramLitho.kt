package com.p69.elma.litho


import android.support.v4.app.SupportActivity
import com.facebook.litho.*
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.p69.elma.core.Dispatch
import com.p69.elma.core.Program
import com.p69.elma.litho.DSL.ElmaLithoView


sealed class RootModel {
    class None : RootModel()
    data class Some(val appModel: Any, val dispatcher: Dispatch<*>, val view: (Any, Dispatch<*>) -> ElmaLithoView) : RootModel()
}

@LayoutSpec
class LithoRootComponentSpec {

    companion object {
        @JvmStatic
        @OnCreateLayout
        fun onCreateLayot(context: ComponentContext, @Prop model: RootModel): ComponentLayout = when(model) {
            is RootModel.None -> Column.create(context).build()
            is RootModel.Some -> {
                val child = model.view(model.appModel, model.dispatcher)
                when (child) {
                    is ElmaLithoView.Widget -> Column.create(context).child(child.view.builder).build()
                    is ElmaLithoView.Layout -> Column.create(context).child(child.view.builder).build()
                }
            }
        }
    }

}

fun <TArg, TModel, TMsg> (Program<TArg, TModel, TMsg, ElmaLithoView>).withLitho(activity: SupportActivity, context: ComponentContext)
        : Program<TArg, TModel, TMsg, ElmaLithoView> {

    val rootComponent = LithoRootComponent.create(context).model(RootModel.None()).build()
    val rootView = LithoView.create(context, rootComponent)
    activity.setContentView(rootView)

    return this.copy(
            setState = { m, d ->
                val newState = RootModel.Some(m as Any, d as Dispatch<*>, { model, dispatcher -> this.view(model as TModel, dispatcher as Dispatch<TMsg>) })
                val root = LithoRootComponent.create(context).model(newState).build()
                rootView.setComponent(root)
            })
}