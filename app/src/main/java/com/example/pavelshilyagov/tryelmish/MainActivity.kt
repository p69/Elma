package com.example.pavelshilyagov.tryelmish


import android.os.Bundle
import com.example.pavelshilyagov.tryelmish.main.MainLithoComponent
import com.example.pavelshilyagov.tryelmish.main.MainModel
import com.example.pavelshilyagov.tryelmish.main.Msg
import com.facebook.litho.ComponentContext
import com.p69.elma.core.*
import com.p69.elma.litho.withLitho
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch

class MainActivity : ElmaActivity<MainModel, Msg>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startWithLitho()
    }

    private fun startWithLitho() {
        val componentContext = ComponentContext(this)
        mkProgramFromComponent(MainLithoComponent(componentContext, this))
                .withLitho(this, componentContext)
                .withSubscription(this::subscription)
                .run(context = UI)
    }

    private fun subscription(model: MainModel): Cmd<Msg> {
        val sub: Sub<Msg> = { dispatcher ->
            launch {
                lifeCycleChannel.consumeEach { handleLifeCycleEvent(it, model, dispatcher) }
            }
        }
        return CmdF.ofSub(sub)
    }

    private suspend fun handleLifeCycleEvent(event: LifeCycleEvent, model: MainModel, dispatcher: Dispatch<Msg>) {
        when(event) {
            is LifeCycleEvent.BackPressed -> dispatcher(Msg.GoBack)
        }
    }
}




