package com.example.hearthstoneexplorer

import android.support.v7.app.AppCompatActivity
import com.example.hearthstoneexplorer.home.*
import com.facebook.litho.ComponentContext
import com.p69.elma.core.*
import com.p69.elma.litho.DSL.ElmaLithoView


class RootComponent(private val context: ComponentContext, private val activity: AppCompatActivity) : ElmaComponent<Unit, HomeModel, HomeMsg, ElmaLithoView> {
    override fun init(args: Unit): UpdateResult<HomeModel, HomeMsg> {
        val model = HomeModel("")
        return UpdateResult(model)
    }

    override fun update(msg: HomeMsg, model: HomeModel): UpdateResult<HomeModel, HomeMsg> {
        return when(msg) {
            is HomeMsg.Exit -> {
                activity.finish()
                UpdateResult(model)
            }
            is HomeMsg.HideVirtualKeyboard -> {
                activity.hideVirtualKeyboard()
                UpdateResult(model)
            }
            else -> Home.update(msg, model)
        }
    }

    override fun view(model: HomeModel, dispatch: Dispatch<HomeMsg>): ElmaLithoView =
            HomeUI.view(model, context, dispatch)
}