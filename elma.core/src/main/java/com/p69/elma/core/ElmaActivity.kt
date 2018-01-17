package com.p69.elma.core

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.ConflatedBroadcastChannel

sealed class LifeCycleEvent {
    data class Create(val savedInstanceState: Bundle?) : LifeCycleEvent()
    data class CreatePersistent(val savedInstanceState: Bundle?, val persistentState: PersistableBundle?) : LifeCycleEvent()
    object Start : LifeCycleEvent()
    data class PostCreate(val savedInstanceState: Bundle?) : LifeCycleEvent()
    data class PostCreatePersistent(val savedInstanceState: Bundle?, val persistentState: PersistableBundle?) : LifeCycleEvent()
    object Resume : LifeCycleEvent()
    object Pause : LifeCycleEvent()
    object Stop : LifeCycleEvent()
    object Destroy : LifeCycleEvent()
    object Restart : LifeCycleEvent()
    data class SaveInstanceState(var outState: Bundle?) : LifeCycleEvent()
    data class SaveInstanceStatePersistent(var outState: Bundle, var outPersistentState: PersistableBundle) : LifeCycleEvent()
    data class RestoreInstanceState(val savedInstanceState: Bundle) : LifeCycleEvent()
    data class RestoreInstanceStatePersistent(val savedInstanceState: Bundle, val persistentState: PersistableBundle) : LifeCycleEvent()
    data class NewIntent(val intent: Intent) : LifeCycleEvent()
    object BackPressed : LifeCycleEvent()
    object AttachedToWindow : LifeCycleEvent()
    object DetachedFromWindow : LifeCycleEvent()
    data class ConfigurationChanged(val newConfig: Configuration) : LifeCycleEvent()
    data class ActivityResult(val requestCode: Int, val resultCode: Int, val data: Intent) : LifeCycleEvent()
    data class RequestPermissionsResult(val requestCode: Int, val permissions: Array<String>, val grantResults: IntArray) : LifeCycleEvent()
}


open class ElmaActivity (private val suppressDefaultBackPressed : Boolean = true) : AppCompatActivity() {
    val rootJob = Job() // simple solution to cancel all child coroutines when activity is destroyed
    private val lifeCycleBroadcast = ConflatedBroadcastChannel<LifeCycleEvent>()
    val lifeCycleChannel: BroadcastChannel<LifeCycleEvent> = lifeCycleBroadcast

    @CallSuper override fun onCreate(savedInstanceState: Bundle?) {
        lifeCycleBroadcast.offer(LifeCycleEvent.Create(savedInstanceState))
        super.onCreate(savedInstanceState)
    }

    @CallSuper override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        lifeCycleBroadcast.offer(LifeCycleEvent.CreatePersistent(savedInstanceState, persistentState))
        super.onCreate(savedInstanceState, persistentState)
    }

    @CallSuper override fun onStart() {
        lifeCycleBroadcast.offer(LifeCycleEvent.Start)
        super.onStart()
    }

    @CallSuper override fun onPostCreate(savedInstanceState: Bundle?) {
        lifeCycleBroadcast.offer(LifeCycleEvent.PostCreate(savedInstanceState))
        super.onPostCreate(savedInstanceState)
    }

    @CallSuper override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        lifeCycleBroadcast.offer(LifeCycleEvent.PostCreatePersistent(savedInstanceState, persistentState))
        super.onPostCreate(savedInstanceState, persistentState)
    }

    @CallSuper override fun onResume() {
        lifeCycleBroadcast.offer(LifeCycleEvent.Resume)
        super.onResume()
    }

    @CallSuper override fun onPause() {
        lifeCycleBroadcast.offer(LifeCycleEvent.Pause)
        super.onPause()
    }

    @CallSuper override fun onStop() {
        lifeCycleBroadcast.offer(LifeCycleEvent.Stop)
        super.onStop()
    }

    @CallSuper override fun onDestroy() {
        lifeCycleBroadcast.offer(LifeCycleEvent.Destroy)
        super.onDestroy()
    }

    @CallSuper override fun onRestart() {
        lifeCycleBroadcast.offer(LifeCycleEvent.Restart)
        super.onRestart()
    }

    @CallSuper override fun onSaveInstanceState(outState: Bundle?) {
        lifeCycleBroadcast.offer(LifeCycleEvent.SaveInstanceState(outState))
        super.onSaveInstanceState(outState)
    }

    @CallSuper override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        lifeCycleBroadcast.offer(LifeCycleEvent.SaveInstanceStatePersistent(outState, outPersistentState))
        super.onSaveInstanceState(outState, outPersistentState)
    }

    @CallSuper override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        lifeCycleBroadcast.offer(LifeCycleEvent.RestoreInstanceState(savedInstanceState))
        super.onRestoreInstanceState(savedInstanceState)
    }

    @CallSuper override fun onRestoreInstanceState(savedInstanceState: Bundle, persistentState: PersistableBundle) {
        lifeCycleBroadcast.offer(LifeCycleEvent.RestoreInstanceStatePersistent(savedInstanceState, persistentState))
        super.onRestoreInstanceState(savedInstanceState, persistentState)
    }

    @CallSuper override fun onNewIntent(intent: Intent) {
        lifeCycleBroadcast.offer(LifeCycleEvent.NewIntent(intent))
        super.onNewIntent(intent)
    }

    @CallSuper override fun onBackPressed() {
        lifeCycleBroadcast.offer(LifeCycleEvent.BackPressed)
        if (!suppressDefaultBackPressed) {
            super.onBackPressed()
        }
    }

    @CallSuper override fun onAttachedToWindow() {
        lifeCycleBroadcast.offer(LifeCycleEvent.AttachedToWindow)
        super.onAttachedToWindow()
    }

    @CallSuper override fun onDetachedFromWindow() {
        lifeCycleBroadcast.offer(LifeCycleEvent.DetachedFromWindow)
        super.onDetachedFromWindow()
        lifeCycleBroadcast.close()
        rootJob.cancel()
    }

    @CallSuper override fun onConfigurationChanged(newConfig: Configuration) {
        lifeCycleBroadcast.offer(LifeCycleEvent.ConfigurationChanged(newConfig))
        super.onConfigurationChanged(newConfig)
    }

    @CallSuper override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        lifeCycleBroadcast.offer(LifeCycleEvent.ActivityResult(requestCode, resultCode, data))
        super.onActivityResult(requestCode, resultCode, data)
    }

    @CallSuper override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                                       grantResults: IntArray) {
        lifeCycleBroadcast.offer(LifeCycleEvent.RequestPermissionsResult(requestCode, permissions, grantResults))
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}