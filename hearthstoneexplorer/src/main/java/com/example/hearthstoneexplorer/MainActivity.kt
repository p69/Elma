package com.example.hearthstoneexplorer

import android.os.Bundle
import com.example.hearthstoneexplorer.home.HomeModel
import com.example.hearthstoneexplorer.home.HomeMsg
import com.facebook.litho.ComponentContext
import com.facebook.litho.StateHandler
import com.p69.elma.core.*
import com.p69.elma.litho.withLitho
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch

class MainActivity : ElmaActivity<HomeModel, HomeMsg>() {
    companion object {
        val rootJob = Job() // simple solution to cancel all child coroutines when activity is destroyed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        start()
    }

    override fun onDestroy() {
        super.onDestroy()
        rootJob.cancel()
    }

    private fun start() {
        val componentContext = ComponentContext(this, StateHandler()) // Strange, but it crashes with NPE if StateHandler is not provided here
        mkProgramFromComponent(RootComponent(componentContext, this))
                .withLitho(this, componentContext)
                .withSubscription(this::subscription)
                .run(context = UI + rootJob)
    }

    private fun subscription(model: HomeModel): Cmd<HomeMsg> {
        val sub: Sub<HomeMsg> = { dispatcher ->
            launch(parent = rootJob) {
                lifeCycleChannel.consumeEach { handleLifeCycleEvent(it, model, dispatcher) }
            }
        }
        return CmdF.ofSub(sub)
    }

    private suspend fun handleLifeCycleEvent(event: LifeCycleEvent, model: HomeModel, dispatcher: Dispatch<HomeMsg>) {
        when(event) {
            is LifeCycleEvent.BackPressed -> dispatcher(HomeMsg.Back)
        }
    }
}
