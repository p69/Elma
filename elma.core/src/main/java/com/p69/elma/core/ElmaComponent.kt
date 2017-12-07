package com.p69.elma.core

/**
 * Simple wrapper for basic functions
 * @param[TArg] - type of the argument for [init] function
 * @param[TModel] - type of the model
 * @param[TMsg] - type of the message
 * @param[TView] - type of the result of the [view] function
 * @property[init] - function for creation of initial model state
 * @property[update] - function for updating the model
 * @property[view] - function for displaying the model
 */
interface ElmaComponent<in TArg, TModel, TMsg, out TView> {
    fun init(args: TArg): UpdateResult<TModel, TMsg>
    fun update(msg: TMsg, model: TModel): UpdateResult<TModel, TMsg>
    fun view(model: TModel, dispatch: Dispatch<TMsg>): TView
}