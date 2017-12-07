package com.example.pavelshilyagov.tryelmish


import com.example.pavelshilyagov.tryelmish.main.*
import com.facebook.litho.ComponentContext
import com.p69.elma.core.*
import com.p69.elma.litho.withLitho
import com.trello.navi2.Event
import com.trello.navi2.rx.RxNavi
import kotlinx.coroutines.experimental.launch

class MainActivity : NaviRxActivity() {
    init {
        RxNavi.observe(this, Event.CREATE).subscribe({ _ ->
            setContentView(R.layout.activity_main)
            startWithLitho()
        })
    }

    private fun startWithLitho() {
        val componentContext = ComponentContext(this)
        mkProgramFromComponent(MainLithoComponent(componentContext, this))
                .withLitho(this, componentContext)
                .withSubscription(this::subscription)
                .run()
    }

    private fun subscription(model: MainModel): Cmd<Msg> {
        val sub: Sub<Msg> = { dispatcher ->
            RxNavi.observe(this, Event.BACK_PRESSED)
                    .subscribe({ _ -> launch { dispatcher(Msg.GoBack) } })
        }

        return CmdF.ofSub(sub)
    }
}


