package com.g3.spot_guide.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.g3.spot_guide.base.interfaces.BaseParameters

abstract class BaseActivity<PARAMETERS : BaseParameters> : AppCompatActivity(), LifecycleObserver {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.lifecycle.addObserver(this)

        this.initializeParameters()

        this.onActivityLoadingFinished()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun create() {
        this.onActivityCreated()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun start() {
        this.onActivityStarted()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun resume() {
        this.onActivityResumed()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun pause() {
        this.onActivityPaused()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun stop() {
        this.onActivityStopped()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun destroy() {
        this.onActivityDestroyed()
    }

    protected abstract fun onActivityLoadingFinished()

    private fun initializeParameters() = this.intent.extras?.let { extras -> this.createParameters()?.loadParameters(extras) }
    protected open fun createParameters(): PARAMETERS? = null

    protected open fun onActivityCreated() {}
    protected open fun onActivityStarted() {}
    protected open fun onActivityResumed() {}
    protected open fun onActivityPaused() {}
    protected open fun onActivityStopped() {}
    protected open fun onActivityDestroyed() {}
}