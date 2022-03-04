package com.yinp.proappkotlin.base

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.savedstate.SavedStateRegistryOwner
import java.util.*
import java.util.concurrent.LinkedBlockingDeque
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 *
 * Project Name : ManageStartActivity
 * Create Time  : 2021-09-23 14:36
 * Create By    : @author xIao
 * Version      : 1.0.0
 * https://github.com/TxcA/ManageStartActivity.git
 * 在这个基础上做了修改，之前是使用接口委托的方式实现的，单是不想用xx:class.java的形式，
 * 之前用的KClass反射的形式，现在修改了部分，等以后看看其他的能修改不。
 **/

abstract class SkipFragment : Fragment() {
    /**
     * start activity for result deque
     */
    lateinit var startActivityResultDeque: LinkedBlockingDeque<StartActivityResult>

    /**
     * https://developer.android.google.cn/reference/kotlin/androidx/activity/result/ActivityResultLauncher.html
     * [androidx.activity.result.ActivityResultLauncher]
     */
    lateinit var activityForResult: ActivityResultLauncher<Intent>

    /**
     * current context
     */
    private var msaContext: Context? = null

    /**
     * current activity result callback
     */
    private val activityResultCallback = object : ActivityResultCallback<ActivityResult> {
        override fun onActivityResult(result: ActivityResult?) {
            result ?: return
            checkInit()
            startActivityResultDeque.pollFirst()?.invoke(result.resultCode, result.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initManageStart()
    }

    /**
     * {@inheritDoc}
     */
    private fun initManageStart() {
        startActivityResultDeque = bindHostSaveState() ?: LinkedBlockingDeque()
        msaContext = requireContext()
        activityForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            activityResultCallback
        )
    }

    /**
     * save host callback state
     */
    private fun SavedStateRegistryOwner.bindHostSaveState(): LinkedBlockingDeque<StartActivityResult>? {
        val saveStateKey = savedStateRegistry.consumeRestoredStateForKey(SAVE_STATE_KEY)
            ?.getString(SAVE_STATE_BUNDLE_KEY) ?: UUID.randomUUID().toString()

        savedStateRegistry.registerSavedStateProvider(SAVE_STATE_KEY) {
            Bundle().apply {
                if (::startActivityResultDeque.isInitialized) {
                    msaResultSaveState[saveStateKey] = startActivityResultDeque
                    putString(SAVE_STATE_BUNDLE_KEY, saveStateKey)
                }
            }
        }

        return msaResultSaveState.remove(saveStateKey)
    }

    /**
     * check is init
     */
    fun checkInit() {
        if (!::startActivityResultDeque.isInitialized) {
            throw IllegalArgumentException("call `initManageStartActivity` required before `onResume`.")
        }
    }

    /**
     * [msaContext] run on not null
     */
    fun runSafeContext(block: Context.() -> Unit) = msaContext?.block()

    /**
     * {@inheritDoc}
     */
    inline fun <reified T> gotoActivityForResult(
        noinline block: Intent.() -> Unit,
        noinline options: () -> ActivityOptionsCompat?,
        noinline result: StartActivityResult
    ) {
        runSafeContext {
            checkInit()
            startActivityResultDeque.offerFirst(result)
            activityForResult.launch(Intent(this, T::class.java).apply(block), options.invoke())
        }
    }

    /**
     * {@inheritDoc}
     */
    inline fun <reified T> T.gotoForResult(
        noinline block: Intent.() -> Unit,
        noinline options: () -> ActivityOptionsCompat?,
        noinline result: StartActivityResult
    ) {
        gotoActivityForResult<T>(block, options, result)
    }

    /**
     * {@inheritDoc}
     */
    suspend inline fun <reified T> gotoActivityForResultSync(
        noinline options: () -> ActivityOptionsCompat?,
        noinline block: Intent.() -> Unit
    ): Result = suspendCoroutine {
        gotoActivityForResult<T>(block, options) { code, intent ->
            it.resume(Result(code, intent))
        }
    }

    /**
     * {@inheritDoc}
     */
    suspend inline fun <reified T> T.gotoForResultSync(
        noinline options: () -> ActivityOptionsCompat?,
        noinline block: Intent.() -> Unit
    ): Result = gotoActivityForResultSync<T>(options, block)

    inline fun <reified T> gotoActivity(
        noinline options: () -> ActivityOptionsCompat? = { null },
        noinline block: Intent.() -> Unit
    ) {
        runSafeContext {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(
                    Intent(this, T::class.java).apply(block),
                    options()?.toBundle()
                )
            } else {
                startActivity(Intent(this, T::class.java).apply(block))
            }
        }
    }

    inline fun <reified T> gotoActivity(
        noinline options: () -> ActivityOptionsCompat? = { null },
    ) {
        gotoActivity<T>(options) {}
    }

    /**
     * {@inheritDoc}
     */
    inline fun <reified T> T.goto(
        noinline options: () -> ActivityOptionsCompat?,
        noinline block: Intent.() -> Unit
    ) {
        gotoActivity<T>(options, block)
    }

    /**
     * {@inheritDoc}
     */
    fun Intent.goto(options: () -> ActivityOptionsCompat?) {
        runSafeContext {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(this@goto, options()?.toBundle())
            } else {
                startActivity(this@goto)
            }
        }
    }

    companion object {
        /**
         * key is [androidx.savedstate.SavedStateRegistry.SavedStateProvider]
         */
        private const val SAVE_STATE_KEY = "mas_save_state"

        /**
         * save state key
         */
        private const val SAVE_STATE_BUNDLE_KEY = "mas_bundle_tag"

        /**
         * result save
         */
        private val msaResultSaveState =
            LinkedHashMap<String, LinkedBlockingDeque<StartActivityResult>>()
    }
}