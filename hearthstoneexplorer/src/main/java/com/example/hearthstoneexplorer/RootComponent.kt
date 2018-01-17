package com.example.hearthstoneexplorer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.hearthstoneexplorer.home.Home
import com.example.hearthstoneexplorer.home.HomeModel
import com.example.hearthstoneexplorer.home.HomeMsg
import com.example.hearthstoneexplorer.home.HomeUI
import com.facebook.litho.ComponentContext
import com.p69.elma.core.Dispatch
import com.p69.elma.core.ElmaComponent
import com.p69.elma.core.UpdateResult
import com.p69.elma.litho.ElmaLithoView


class RootComponent(private val context: ComponentContext, private val activity: AppCompatActivity) : ElmaComponent<Bundle?, HomeModel, HomeMsg, ElmaLithoView> {

    private val bundleKey = "model"

    override fun init(args: Bundle?): UpdateResult<HomeModel, HomeMsg> {
        val fromParcel = args?.getParcelable<HomeModel>(bundleKey)
        val model = fromParcel ?: HomeModel()
        return UpdateResult(model)
    }

    override fun update(msg: HomeMsg, model: HomeModel): UpdateResult<HomeModel, HomeMsg> {
        return when(msg) {
            HomeMsg.Exit -> {
                activity.finish()
                UpdateResult(model)
            }
            HomeMsg.HideVirtualKeyboard -> {
                activity.hideVirtualKeyboard()
                UpdateResult(model)
            }
            is HomeMsg.SaveInstanceState -> {
                msg.outState?.putParcelable(bundleKey, model)
                UpdateResult(model)
            }
            else -> Home.update(msg, model)
        }
    }

    override fun view(model: HomeModel, dispatch: Dispatch<HomeMsg>): ElmaLithoView =
            HomeUI.view(model, context, dispatch)
}