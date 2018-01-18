package com.example.hearthstoneexplorer

import android.os.Bundle
import com.example.hearthstoneexplorer.home.HomeModel
import com.example.hearthstoneexplorer.home.HomeMsg
import com.facebook.litho.ComponentContext
import com.facebook.litho.StateHandler
import com.p69.elma.core.*
import com.p69.elma.litho.withLitho
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newSingleThreadContext

class MainActivity : ElmaActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        start(savedInstanceState)
    }

    private fun start(savedInstanceState: Bundle?) {
        val componentContext = ComponentContext(this, StateHandler()) // Strange, but it crashes with NPE if StateHandler is not provided here
        val programContext = newSingleThreadContext("elma context") // Create new thread for main loop and dispatch all messages through it
        mkProgramFromComponent(RootComponent(componentContext, this))
                .withLitho(this, componentContext, UI + rootJob)
                .withSubscription(this::subscription)
                .runWith(savedInstanceState, context = programContext + rootJob)
    }

    private fun subscription(model: HomeModel): Cmd<HomeMsg> {
        val sub: Sub<HomeMsg> = { dispatcher ->
            launch(parent = rootJob) {
                lifeCycleChannel.consumeEach {
                  handleLifeCycleEvent(it, dispatcher)
                }
            }
        }
        return CmdF.ofSub(sub)
    }

    private fun handleLifeCycleEvent(event: LifeCycleEvent, dispatcher: Dispatch<HomeMsg>) {
        when (event) {
            LifeCycleEvent.BackPressed -> dispatcher(HomeMsg.Back)
            is LifeCycleEvent.SaveInstanceState -> dispatcher(HomeMsg.SaveInstanceState(event.outState))
        }
    }
}
