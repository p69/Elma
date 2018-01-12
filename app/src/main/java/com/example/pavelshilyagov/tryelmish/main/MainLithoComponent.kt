package com.example.pavelshilyagov.tryelmish.main

import android.support.v4.app.SupportActivity
import com.example.pavelshilyagov.tryelmish.hideVirtualKeyboard
import com.example.pavelshilyagov.tryelmish.search.Search
import com.facebook.litho.ComponentContext
import com.p69.elma.core.*
import com.p69.elma.litho.ElmaLithoView
import java.util.*


class MainLithoComponent(private val context:ComponentContext, private val activity: SupportActivity) : ElmaComponent<Unit, MainModel, Msg, ElmaLithoView> {

    override fun init(args: Unit): UpdateResult<MainModel, Msg> {
        val initialScreen = Screen.Search(Search.init())
        val backStack = Stack<Screen>()
        val model = MainModel(initialScreen, backStack)
        return UpdateResult(model)
    }

    override fun update(msg: Msg, model: MainModel): UpdateResult<MainModel, Msg> {
        return when(msg) {
            is Msg.Exit -> {
                activity.finish()
                UpdateResult(model)
            }
            is Msg.HideVirtualKeyboard -> {
                activity.hideVirtualKeyboard()
                UpdateResult(model)
            }
            else -> Main.update(msg, model)
        }
    }

    override fun view(model: MainModel, dispatch: Dispatch<Msg>): ElmaLithoView = MainUI.view(model, context, dispatch)
}