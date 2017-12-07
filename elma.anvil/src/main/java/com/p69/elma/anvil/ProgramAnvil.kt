package com.p69.elma.anvil

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.p69.elma.core.Dispatch
import com.p69.elma.core.Program
import trikita.anvil.Anvil
import trikita.anvil.RenderableView

@SuppressLint("ViewConstructor")
class AnvilRenderer<in TModel, out TMsg> @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, private val view: (TModel, Dispatch<TMsg>) -> Unit
) : RenderableView(context, attrs, defStyleAttr) {
    override fun view() {
        if (model != null) {
            view(model!!, dispatcher)
        }
    }

    private var model: TModel? = null
    private var dispatcher: Dispatch<TMsg> = { _ -> }

    fun setState(newState: TModel, dispatch: Dispatch<TMsg>) {
        model = newState
        dispatcher = dispatch
    }
}


fun <TArg, TModel, TMsg> (Program<TArg, TModel, TMsg, Unit>).withAnvil(container: View, ctx: Context)
        : Program<TArg, TModel, TMsg, Unit> {
    val renderer = AnvilRenderer(ctx, view = this.view)
    Anvil.mount(container, renderer)
    return this.copy(setState = { m, d ->
        renderer.setState(m, d)
        Anvil.render()
    })
}