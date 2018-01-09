package com.example.hearthstoneexplorer

import android.support.v7.app.AppCompatActivity
import com.example.hearthstoneexplorer.domain.Card
import com.example.hearthstoneexplorer.domain.HearthstoneApi
import com.example.hearthstoneexplorer.domain.parseCards
import com.example.hearthstoneexplorer.home.Home
import com.example.hearthstoneexplorer.home.HomeModel
import com.example.hearthstoneexplorer.home.HomeMsg
import com.example.hearthstoneexplorer.home.HomeUI
import com.facebook.litho.ComponentContext
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.p69.elma.core.CmdF
import com.p69.elma.core.Dispatch
import com.p69.elma.core.ElmaComponent
import com.p69.elma.core.UpdateResult
import com.p69.elma.litho.DSL.ElmaLithoView
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay


class RootComponent(private val context: ComponentContext, private val activity: AppCompatActivity) : ElmaComponent<Unit, HomeModel, HomeMsg, ElmaLithoView> {
    override fun init(args: Unit): UpdateResult<HomeModel, HomeMsg> {
        val model = HomeModel(cards = emptyList())
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