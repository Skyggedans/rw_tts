package com.rockwellits.rw_plugins.rw_tts

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle


class RwTtsPlugin(val activity: Activity, val channel: MethodChannel) : MethodCallHandler,
        Application.ActivityLifecycleCallbacks {
    private val ACTION_TTS = "com.realwear.ttsservice.intent.action.TTS"
    private val ACTION_TTS_FINISHED = "com.realwear.ttsservice.intent.action.TTS_FINISHED"
    private val EXTRA_TEXT = "text_to_speak"
    private val EXTRA_ID = "tts_id"
    private val EXTRA_PAUSE = "pause_speech_recognizer"
    private lateinit var methodResult: Result


    private val ttsBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action

            if (action == ACTION_TTS_FINISHED) {
                val ttsId = intent.getIntExtra(EXTRA_ID, 0)

                methodResult.success(ttsId)
            }
        }
    }

    init {
        activity.application.registerActivityLifecycleCallbacks(this)
        activity.registerReceiver(ttsBroadcastReceiver, IntentFilter(ACTION_TTS_FINISHED))
    }

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "com.rockwellits.rw_plugins/rw_tts")

            channel.setMethodCallHandler(RwTtsPlugin(registrar.activity(), channel))
        }
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when {
            call.method == "speak" -> {
                methodResult = result

                val text = call.argument<String>("text")
                val requestCode = call.argument<Int>("requestCode")

                if (text != null && requestCode != null) {
                    speak(text, requestCode)
                }
                else {
                    result.error(RwTtsPlugin::class.java.canonicalName, "Unable to start TTS", null)
                }
            }
            else -> result.notImplemented()
        }
    }

    override fun onActivityPaused(activity: Activity) {
        activity.unregisterReceiver(ttsBroadcastReceiver)
    }

    override fun onActivityResumed(activity: Activity) {
        activity.registerReceiver(ttsBroadcastReceiver, IntentFilter(ACTION_TTS_FINISHED))
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    private fun speak(text: String, requestCode: Int) {
        val intent = Intent(ACTION_TTS)

        intent.putExtra(EXTRA_TEXT, text)
        intent.putExtra(EXTRA_ID, requestCode)
        intent.putExtra(EXTRA_PAUSE, false)

        activity.sendBroadcast(intent)
    }
}
