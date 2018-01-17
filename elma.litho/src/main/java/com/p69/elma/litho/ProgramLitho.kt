package com.p69.elma.litho


import android.support.v4.app.SupportActivity
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.p69.elma.core.Program
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext


fun <TArg, TModel, TMsg> (Program<TArg, TModel, TMsg, ElmaLithoView>).withLitho(activity: SupportActivity, context: ComponentContext, coroutineContext: CoroutineContext)
        : Program<TArg, TModel, TMsg, ElmaLithoView> {

    val uiComponent = ElmaViewComponent.create(context).viewCreator(ElmaViewCreator.None)
    val rootView = LithoView.create(context, uiComponent.build())
    activity.setContentView(rootView)

    return this.copy(
            setState = { m, d ->
                launch(coroutineContext) {
                    val newState = ElmaViewCreator.Some { view(m, d) }
                    val newUiComponent = ElmaViewComponent.create(context).viewCreator(newState)
                    rootView.setComponent(newUiComponent.build())
                }
            })
}