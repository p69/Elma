package com.example.hearthstoneexplorer

import android.support.v7.app.AppCompatActivity
import com.beust.klaxon.Converter
import com.beust.klaxon.JsonReader
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import com.example.hearthstoneexplorer.domain.Card
import com.example.hearthstoneexplorer.domain.CardType
import com.example.hearthstoneexplorer.domain.HearthstoneApi
import com.example.hearthstoneexplorer.home.*
import com.facebook.litho.ComponentContext
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.p69.elma.core.*
import com.p69.elma.litho.DSL.ElmaLithoView
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import java.io.StringReader


class RootComponent(private val context: ComponentContext, private val activity: AppCompatActivity) : ElmaComponent<Unit, HomeModel, HomeMsg, ElmaLithoView> {
    override fun init(args: Unit): UpdateResult<HomeModel, HomeMsg> {
        val model = HomeModel(cards = emptyList())
        val initLoadCmd = CmdF.ofAsyncFunc(
                {q-> searchByQuery(q)},
                "worldshaker",
                HomeMsg::OnCardsLoaded,
                HomeMsg::OnError
        )
        return UpdateResult(model, initLoadCmd)
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

private suspend fun searchByQuery(query: String): Deferred<List<Card>> = async {
    delay(300)
    val (_, _, res) = Fuel.request(HearthstoneApi.search(query)).responseString()
    return@async when (res) {
        is Result.Success -> {
            //TODO parse
            emptyList<Card>()
        }
        is Result.Failure -> throw res.error
    }
}

val cardTypeConverter = object: Converter<CardType> {
    override fun toJson(value: CardType): String? = """{"type" : "${value.description}""""

    override fun fromJson(jv: JsonValue) = when (jv.objString("type")) {
        CardType.Enchantment.description -> CardType.Enchantment
        CardType.Hero.description -> CardType.Hero
        CardType.HeroPower.description -> CardType.HeroPower
        CardType.Minion.description -> CardType.Minion
        CardType.Spell.description -> CardType.Spell
        CardType.Weapon.description -> CardType.Weapon
        else -> throw IllegalArgumentException("Unknown card type $jv")
    }
}

fun parseCards(str: String): List<Card> {
    val klaxon = Klaxon()
            .converter(cardTypeConverter)
    JsonReader(StringReader(str)).use { reader ->
        val result = arrayListOf<Card>()
        reader.beginArray {
            while (reader.hasNext()) {
                val card = klaxon.parse<Card>(reader)
                if (card != null) result.add(card)
            }
        }
        return result
    }
}



