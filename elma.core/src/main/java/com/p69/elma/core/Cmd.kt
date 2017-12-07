package com.p69.elma.core

import kotlinx.coroutines.experimental.*

typealias Dispatch<TMsg> = suspend (TMsg) -> Unit

typealias Sub<TMsg> = (Dispatch<TMsg>) -> Unit

typealias Cmd<TMsg> = List<Sub<TMsg>>


object CmdF {
    inline val none: Cmd<*>
        get() = emptyList()

    fun <TMsg> ofMsg(msg: TMsg): Cmd<TMsg> = listOf({ dispatch -> launch { dispatch(msg) } })

    fun <TMsg> ofSub(sub: Sub<TMsg>): Cmd<TMsg> = listOf(sub)

    fun <T, TMsg> map(f: (T) -> TMsg, cmd: Cmd<T>): Cmd<TMsg> {
        return cmd.map { dispatcher: (Dispatch<T>) -> Unit ->
            val dispatcherMapper: (Dispatch<TMsg>) -> Dispatch<T> = { dispatch: Dispatch<TMsg> -> { x: T -> launch { dispatch(f(x)) } } }
            { d: Dispatch<TMsg> -> dispatcher(dispatcherMapper(d)) }
            //dispatcherMapper then dispatcher
        }
    }

    infix fun <T, TMsg> Cmd<T>.map(f: (T) -> TMsg): Cmd<TMsg> = map(f, this)

    fun <TMsg> batch(vararg cmds: Cmd<TMsg>): Cmd<TMsg> = cmds.toList().flatten()

    fun <TArg, TResult, TMsg> ofAsyncFunc(
            task: suspend (TArg) -> Deferred<TResult>,
            arg: TArg,
            ofSuccess: (TResult) -> TMsg,
            ofError: (Exception) -> TMsg): Cmd<TMsg> {
        fun bind(dispatch: Dispatch<TMsg>) = runBlocking(Unconfined) {
            try {
                val res = task(arg).await()
                dispatch(ofSuccess(res))
            } catch (ex: Exception) {
                dispatch(ofError(ex))
            }
        }
        return listOf(::bind)
    }

    fun <TArg, TResult, TMsg> ofFunc(
            task: (TArg) -> TResult,
            arg: TArg,
            ofSuccess: (TResult) -> TMsg,
            ofError: (Exception) -> TMsg): Cmd<TMsg> {
        fun bind(dispatch: Dispatch<TMsg>) {
            try {
                val res = task(arg)
                launch { dispatch(ofSuccess(res)) }
            } catch (ex: Exception) {
                launch { dispatch(ofError(ex)) }
            }
        }
        return listOf(::bind)
    }

    fun <TArg, TResult, TMsg> performFunc(
            task: (TArg) -> TResult,
            arg: TArg,
            ofSuccess: (TResult) -> TMsg
    ): Cmd<TMsg> {
        fun bind(dispatch: Dispatch<TMsg>) {
            try {
                val res = task(arg)
                launch { dispatch(ofSuccess(res)) }
            } catch (_: Exception) {
            }
        }
        return listOf(::bind)
    }

    fun <TArg, TMsg> attemptFunc(
            task: (TArg) -> Unit,
            arg: TArg,
            ofError: (Exception) -> TMsg
    ): Cmd<TMsg> {
        fun bind(dispatch: Dispatch<TMsg>) {
            try {
                task(arg)
            } catch (ex: Exception) {
                launch { dispatch(ofError(ex)) }
            }
        }
        return listOf(::bind)
    }
}
