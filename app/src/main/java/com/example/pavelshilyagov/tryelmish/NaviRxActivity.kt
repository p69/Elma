package com.example.pavelshilyagov.tryelmish

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import com.trello.navi2.Event
import com.trello.navi2.Listener
import com.trello.navi2.NaviComponent
import com.trello.navi2.internal.NaviEmitter


abstract class NaviRxActivity : AppCompatActivity(), NaviComponent {
    private val base = NaviEmitter.createActivityEmitter()

    override fun handlesEvents(vararg events: Event<*>): Boolean {
        return base.handlesEvents(*events)
    }

    override fun <T> addListener(event: Event<T>, listener: Listener<T>) {
        base.addListener(event, listener)
    }

    override fun <T> removeListener(listener: Listener<T>) {
        base.removeListener(listener)
    }

    @CallSuper override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        base.onCreate(savedInstanceState)
    }

    @CallSuper override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        base.onCreate(savedInstanceState, persistentState)
    }

    @CallSuper override fun onStart() {
        super.onStart()
        base.onStart()
    }

    @CallSuper override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        base.onPostCreate(savedInstanceState)
    }

    @CallSuper override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        base.onPostCreate(savedInstanceState, persistentState)
    }

    @CallSuper override fun onResume() {
        super.onResume()
        base.onResume()
    }

    @CallSuper override fun onPause() {
        base.onPause()
        super.onPause()
    }

    @CallSuper override fun onStop() {
        base.onStop()
        super.onStop()
    }

    @CallSuper override fun onDestroy() {
        base.onDestroy()
        super.onDestroy()
    }

    @CallSuper override fun onRestart() {
        super.onRestart()
        base.onRestart()
    }

    @CallSuper override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        base.onSaveInstanceState(outState!!)
    }

    @CallSuper override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        base.onSaveInstanceState(outState, outPersistentState)
    }

    @CallSuper override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        base.onRestoreInstanceState(savedInstanceState)
    }

    @CallSuper override fun onRestoreInstanceState(savedInstanceState: Bundle, persistentState: PersistableBundle) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        base.onRestoreInstanceState(savedInstanceState, persistentState)
    }

    @CallSuper override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        base.onNewIntent(intent)
    }

    @CallSuper override fun onBackPressed() {
        base.onBackPressed()
    }

    @CallSuper override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        base.onAttachedToWindow()
    }

    @CallSuper override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        base.onDetachedFromWindow()
    }

    @CallSuper override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        base.onConfigurationChanged(newConfig)
    }

    @CallSuper override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        base.onActivityResult(requestCode, resultCode, data)
    }

    @CallSuper override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                                       grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        base.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}