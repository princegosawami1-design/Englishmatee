package com.example.voice

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class VoiceManager(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _isSlowPlayback = MutableStateFlow(false)
    val isSlowPlayback: StateFlow<Boolean> = _isSlowPlayback.asStateFlow()

    private var lastSpokenText: String = ""

    init {
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (e: Exception) {
            Log.e("VoiceManager", "Error creating TextToSpeech: ${e.message}")
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts?.setLanguage(Locale.ENGLISH)
            }
            tts?.setPitch(1.0f)
            tts?.setSpeechRate(1.0f)
            isInitialized = true

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isSpeaking.value = true
                }

                override fun onDone(utteranceId: String?) {
                    _isSpeaking.value = false
                }

                override fun onError(utteranceId: String?) {
                    _isSpeaking.value = false
                }
            })
        } else {
            isInitialized = false
        }
    }

    fun speak(text: String, isSlow: Boolean = _isSlowPlayback.value) {
        if (!isInitialized || tts == null) return
        lastSpokenText = text
        _isSlowPlayback.value = isSlow

        val rate = if (isSlow) 0.72f else 1.0f
        tts?.setSpeechRate(rate)
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utterance_${System.currentTimeMillis()}")
    }

    fun replay() {
        if (lastSpokenText.isNotEmpty()) {
            speak(lastSpokenText, _isSlowPlayback.value)
        }
    }

    fun togglePlaybackSpeed(): Boolean {
        val newSpeed = !_isSlowPlayback.value
        _isSlowPlayback.value = newSpeed
        return newSpeed
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
