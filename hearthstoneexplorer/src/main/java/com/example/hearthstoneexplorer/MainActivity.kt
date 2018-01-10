package com.example.hearthstoneexplorer

import android.os.Bundle
import com.example.hearthstoneexplorer.home.HomeModel
import com.example.hearthstoneexplorer.home.HomeMsg
import com.facebook.litho.ComponentContext
import com.p69.elma.core.*
import com.p69.elma.litho.withLitho
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch

class MainActivity : ElmaActivity<HomeModel, HomeMsg>() {
    companion object {
        val rootJob = Job() // simple solution to cancel all child coroutines when activity is destroyed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()
    }

    override fun onDestroy() {
        super.onDestroy()
        rootJob.cancel()
    }

    private fun start() {
        val componentContext = ComponentContext(this)
        mkProgramFromComponent(RootComponent(componentContext, this))
                .withLitho(this, componentContext)
                .withSubscription(this::subscription)
                .runWith(Unit, rootJob = rootJob)
    }

    private fun subscription(model: HomeModel): Cmd<HomeMsg> {
        val sub: Sub<HomeMsg> = { dispatcher ->
            launch {
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
