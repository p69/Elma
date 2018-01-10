package com.p69.elma.core

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch

typealias Dispatch<TMsg> = (TMsg) -> Unit

typealias Sub<TMsg> = (Dispatch<TMsg>) -> Unit

typealias Cmd<TMsg> = List<Sub<TMsg>>


object CmdF {
    inline val none: Cmd<*>
        get() = emptyList()

    fun <TMsg> ofMsg(msg: TMsg): Cmd<TMsg> = listOf({ dispatch -> dispatch(msg) })

    fun <TMsg> ofSub(sub: Sub<TMsg>): Cmd<TMsg> = listOf(sub)

    fun <TMsg> ofAction(action: (Dispatch<TMsg>)->Unit): Cmd<TMsg> = listOf(action)

    fun <T, TMsg> map(f: (T) -> TMsg, cmd: Cmd<T>): Cmd<TMsg> {
        return cmd.map { dispatcher: (Dispatch<T>) -> Unit ->
            val dispatcherMapper: (Dispatch<TMsg>) -> Dispatch<T> = { dispatch: Dispatch<TMsg> ->
                { x: T -> dispatch(f(x)) }
            }
            { d: Dispatch<TMsg> -> dispatcher(dispatcherMapper(d)) }
        }
    }

    infix fun <T, TMsg> Cmd<T>.map(f: (T) -> TMsg): Cmd<TMsg> = map(f, this)

    fun <TMsg> batch(vararg cmds: Cmd<TMsg>): Cmd<TMsg> = cmds.toList().flatten()

    fun <TArg, TResult, TMsg> ofAsyncFunc(
            task: suspend (TArg) -> TResult,
            arg: TArg,
            ofSuccess: (TResult) -> TMsg,
            ofError: (Exception) -> TMsg,
            parent: Job? = null): Cmd<TMsg> = ofAction { dispatch ->
        launch(parent = parent) {
            try {
                val res = task(arg)
                if (isActive) {
                    dispatch(ofSuccess(res))
                }
            } catch (ex: Exception) {
                if (isActive) {
                    dispatch(ofError(ex))
                }
            }
        }
    }


    fun <TArg, TResult, TMsg> ofFunc(
            task: (TArg) -> TResult,
            arg: TArg,
            ofSuccess: (TResult) -> TMsg,
            ofError: (Exception) -> TMsg): Cmd<TMsg> = ofAction { dispatch ->
        try {
            val res = task(arg)
            dispatch(ofSuccess(res))
        } catch (ex: Exception) {
            dispatch(ofError(ex))
        }
    }

    fun <TArg, TResult, TMsg> performFunc(
            task: (TArg) -> TResult,
            arg: TArg,
            ofSuccess: (TResult) -> TMsg
    ): Cmd<TMsg> = ofAction { dispatch ->
        try {
            val res = task(arg)
            dispatch(ofSuccess(res))
        } catch (_: Exception) {
        }
    }

    fun <TArg, TMsg> attemptFunc(
            task: (TArg) -> Unit,
            arg: TArg,
            ofError: (Exception) -> TMsg
    ): Cmd<TMsg> = ofAction { dispatch ->
        try {
            task(arg)
        } catch (ex: Exception) {
            dispatch(ofError(ex))
        }
    }
}
