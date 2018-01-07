package com.p69.elma.core

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import kotlinx.coroutines.experimental.channels.*
import kotlinx.coroutines.experimental.launch

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


open class ElmaActivity<TModel, TMsg> (private val suppressDefaultBackPressed : Boolean = true) : AppCompatActivity() {
    private val lifeCycleBroadcast = ConflatedBroadcastChannel<LifeCycleEvent>()
    val lifeCycleChannel: BroadcastChannel<LifeCycleEvent> = lifeCycleBroadcast

    @CallSuper override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.Create(savedInstanceState))
        }
    }

    @CallSuper override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.CreatePersistent(savedInstanceState, persistentState))
        }
    }

    @CallSuper override fun onStart() {
        super.onStart()
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.Start)
        }
    }

    @CallSuper override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.PostCreate(savedInstanceState))
        }
    }

    @CallSuper override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.PostCreatePersistent(savedInstanceState, persistentState))
        }
    }

    @CallSuper override fun onResume() {
        super.onResume()
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.Resume)
        }
    }

    @CallSuper override fun onPause() {
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.Pause)
        }
        super.onPause()
    }

    @CallSuper override fun onStop() {
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.Stop)
        }
        super.onStop()
    }

    @CallSuper override fun onDestroy() {
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.Destroy)
            lifeCycleBroadcast.close()
        }
        super.onDestroy()
    }

    @CallSuper override fun onRestart() {
        super.onRestart()
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.Restart)
        }
    }

    @CallSuper override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.SaveInstanceState(outState))
        }
    }

    @CallSuper override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.SaveInstanceStatePersistent(outState, outPersistentState))
        }
    }

    @CallSuper override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.RestoreInstanceState(savedInstanceState))
        }
    }

    @CallSuper override fun onRestoreInstanceState(savedInstanceState: Bundle, persistentState: PersistableBundle) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.RestoreInstanceStatePersistent(savedInstanceState, persistentState))
        }
    }

    @CallSuper override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.NewIntent(intent))
        }
    }

    @CallSuper override fun onBackPressed() {
        if (!suppressDefaultBackPressed) {
            super.onBackPressed()
        }
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.BackPressed)
        }
    }

    @CallSuper override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.AttachedToWindow)
        }
    }

    @CallSuper override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.DetachedFromWindow)
        }
    }

    @CallSuper override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.ConfigurationChanged(newConfig))
        }
    }

    @CallSuper override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.ActivityResult(requestCode, resultCode, data))
        }
    }

    @CallSuper override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                                       grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        launch {
            lifeCycleBroadcast.send(LifeCycleEvent.RequestPermissionsResult(requestCode, permissions, grantResults))
        }
    }
}