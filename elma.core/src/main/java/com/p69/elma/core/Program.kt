package com.p69.elma.core

import android.util.Log
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch

/**
 * Data class which represents results of updates iteration
 * @param[TModel] the Type of the model
 * @param[TMsg] the Type of the messages
 * @property[model] the model instance
 * @property[effects] the list of commands which will be executed by passing messages through the program loop
 */
data class UpdateResult<out TModel, out TMsg>(val model: TModel, val effects: Cmd<TMsg> = CmdF.none)

/**
 * Data class which represents results of updates iteration with effects for both: current model and it's parent
 * @param[TModel] the Type of the model
 * @param[TMsg] the Type of the messages
 * @param[TParentMsg] the Type of the parent's messages
 * @property[model] the model instance
 * @property[effects] the list of commands which will be executed by passing messages through the program loop
 * @property[parentEffects] the list of commands for the parent which will be executed by passing messages through the program loop
 */
data class UpdateResultWithParentsEffects<out TModel, out TMsg, out TParentMsg>(
        val model: TModel,
        val effects: Cmd<TMsg> = CmdF.none,
        val parentEffects: Cmd<TParentMsg> = CmdF.none)


/**
 * Object for starting an application loop
 * @param[TArg] - type of the argument for [init] function
 * @param[TModel] - type of the model
 * @param[TMsg] - type of the message
 * @param[TView] - type of the result of the [view] function
 * @property[init] - function for creation of initial model state
 * @property[update] - function for updating the model
 * @property[view] - function for displaying the model
 * @property[subscribe] - function for subscribing on external events
 * @property[setState] - function for mutating the whole state of the application. Shouldn't be used in typical application
 * @property[onError] - callback for errors during processing app messages, override for additional logging.
 */
data class Program<in TArg, TModel, TMsg, out TView>(
        val init: (TArg) -> UpdateResult<TModel, TMsg>,
        val update: (TMsg, TModel) -> UpdateResult<TModel, TMsg>,
        val subscribe: (TModel) -> Cmd<TMsg>,
        val view: (TModel, Dispatch<TMsg>) -> TView,
        val setState: (TModel, Dispatch<TMsg>) -> Unit,
        val onError: (Pair<String, Exception>) -> Unit
)


fun <TArg, TModel, TMsg, TView> mkProgram(
        init: (TArg) -> UpdateResult<TModel, TMsg>,
        update: (TMsg, TModel) -> UpdateResult<TModel, TMsg>,
        view: (TModel, Dispatch<TMsg>) -> TView): Program<TArg, TModel, TMsg, TView> {
    return Program(
            init = init,
            update = update,
            view = view,
            subscribe = { _ -> emptyList() },
            setState = { model, dispatcher -> view(model, dispatcher) },
            onError = { err -> Log.e("Program loop", err.first, err.second) }
    )
}

fun <TArg, TModel, TMsg, TView> mkSimple(
        init: (TArg) -> TModel,
        update: (TMsg, TModel) -> TModel,
        view: (TModel, Dispatch<TMsg>) -> TView): Program<TArg, TModel, TMsg, TView> {
    return Program(
            init = { arg -> UpdateResult(init(arg)) },
            update = { msg, model -> UpdateResult(update(msg, model)) },
            view = view,
            subscribe = { _ -> emptyList() },
            setState = { model, dispatcher -> view(model, dispatcher) },
            onError = { err -> Log.e("Program loop", err.first, err.second) }
    )
}

fun <TArg, TModel, TMsg, TView> mkProgramFromComponent(component: ElmaComponent<TArg, TModel, TMsg, TView>): Program<TArg, TModel, TMsg, TView> {
    return Program(
            init = component::init,
            update = component::update,
            view = component::view,
            subscribe = { _ -> emptyList() },
            setState = { model, dispatcher -> component.view(model, dispatcher) },
            onError = { err -> Log.e("Program loop", err.first, err.second) }
    )
}

fun <TArg, TModel, TMsg, TView> (Program<TArg, TModel, TMsg, TView>).withSubscription(subscribe: (TModel) -> Cmd<TMsg>): Program<TArg, TModel, TMsg, TView> {
    fun sub(model: TModel): Cmd<TMsg> = CmdF.batch(this.subscribe(model), subscribe(model))
    return this.copy(
            subscribe = ::sub
    )
}

fun <TArg, TModel, TMsg, TView> (Program<TArg, TModel, TMsg, TView>).runWith(arg: TArg, mailBoxCapacity: Int = Channel.CONFLATED) {
    val program = this
    val (initialModel, initialEffects) = program.init(arg)
    var currentModel = initialModel
    val loop = actor<TMsg>(context = UI, capacity = mailBoxCapacity) {
        for (msg in channel) {
            try {
                val (updatedModel, effects) = program.update(msg, currentModel)
                currentModel = updatedModel
                program.setState(currentModel, { m -> channel.offer(m) })

                for (effect in effects) {
                    effect({ m -> channel.offer(m) })
                }
            } catch (e: Exception) {
                program.onError(Pair("Failed while processing message.", e))
            }
        }
    }
    program.setState(initialModel, { m -> loop.offer(m) })

    val effects = initialEffects + program.subscribe(initialModel)
    effects.forEach { it({ m -> loop.offer(m) }) }
}

fun <TModel, TMsg, TView> (Program<Unit, TModel, TMsg, TView>).run() = runWith(Unit)