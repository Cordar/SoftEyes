package com.hsd.upc.hack.softeyes2

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Pair
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.huawei.agconnect.crash.AGConnectCrash
import com.huawei.agconnect.remoteconfig.AGConnectConfig
import com.huawei.hms.mlsdk.common.MLApplication
import com.huawei.hms.mlsdk.tts.*

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val apiKey = "DAEDAJQsFH0CwMKt+Y5TaIeNWvnATEpTiZGWIpbwWmbvLfYEjkeRUTUhgw+z6Ms3E+rYiz5h0JO+WLs0/Dn7Mcp4gqUFx7xPwtSNdQ=="
        MLApplication.getInstance().setApiKey(apiKey);

        val extras = intent.extras
        val byteArray = extras!!.getByteArray("image")

        val image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
        val imageView: ImageView = findViewById<ImageView>(R.id.image)
        imageView.setImageBitmap(image)

        val description = intent.getStringExtra("description")
        val button: Button = findViewById<Button>(R.id.button)
        button.setText(description)



        val backButton: Button = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            try {
                val intent = Intent(this@ResultsActivity, MainActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        button.setOnClickListener {
            try {
                confText2Speech(description)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun confText2Speech(sourceText: String?) {
        // Use customized parameter settings to create a TTS engine.
        val mlTtsConfig = MLTtsConfig() // Set the text converted from speech to Chinese.
            .setLanguage(MLTtsConstants.TTS_EN_US) // Set the Chinese timbre.
            .setPerson(MLTtsConstants.TTS_SPEAKER_FEMALE_EN) // Set the speech speed. The range is (0,5.0]. 1.0 indicates a normal speed.
            .setSpeed(1.0f) // Set the volume. The range is (0,2). 1.0 indicates a normal volume.
            .setVolume(1.0f)
        val mlTtsEngine = MLTtsEngine(mlTtsConfig)
        // Set the volume of the built-in player, in dBs. The value is in the range of [0, 100].
        mlTtsEngine.setPlayerVolume(20)
        // Update the configuration when the engine is running.
        mlTtsEngine.updateConfig(mlTtsConfig)

        val callback: MLTtsCallback = object : MLTtsCallback {
            override fun onError(taskId: String, err: MLTtsError) {

                val test = ""
                // Processing logic for TTS failure.
            }

            override fun onWarn(taskId: String, warn: MLTtsWarn) {
                // Alarm handling without affecting service logic.
                val test = ""
            }

            // Return the mapping between the currently played segment and text. start: start position of the audio segment in the input text; end (excluded): end position of the audio segment in the input text.
            override fun onRangeStart(taskId: String, start: Int, end: Int) {
                // Process the mapping between the currently played segment and text.
            }

            // taskId: ID of an audio synthesis task corresponding to the audio.
            // audioFragment: audio data.
            // offset: offset of the audio segment to be transmitted in the queue. One audio synthesis task corresponds to an audio synthesis queue.
            // range: text area where the audio segment to be transmitted is located; range.first (included): start position; range.second (excluded): end position.
            override fun onAudioAvailable(
                taskId: String?,
                audioFragment: MLTtsAudioFragment?,
                offset: Int,
                p3: Pair<Int, Int>?,
                bundle: Bundle?
            ) {
                val test = ""
                // Audio stream callback API, which is used to return the synthesized audio data to the app.
            }

            override fun onEvent(taskId: String, eventId: Int, bundle: Bundle) {
                // Callback method of a TTS event. eventId indicates the event name.
                when (eventId) {
                    MLTtsConstants.EVENT_PLAY_START -> {
                    }
                    MLTtsConstants.EVENT_PLAY_STOP -> {                        // Called when playback stops.
                        var isInterrupted: Boolean =
                            bundle.getBoolean(MLTtsConstants.EVENT_PLAY_STOP_INTERRUPTED)
                    }
                    MLTtsConstants.EVENT_PLAY_RESUME -> {
                    }
                    MLTtsConstants.EVENT_PLAY_PAUSE -> {
                    }
                    MLTtsConstants.EVENT_SYNTHESIS_START -> {
                    }
                    MLTtsConstants.EVENT_SYNTHESIS_END -> {
                    }
                    MLTtsConstants.EVENT_SYNTHESIS_COMPLETE -> {                      // Audio synthesis is complete. All synthesized audio streams are passed to the app.
                        var isInterrupted
                                : Boolean =
                            bundle.getBoolean(MLTtsConstants.EVENT_SYNTHESIS_INTERRUPTED)
                    }
                    else -> {
                    }
                }
            }
        }

        mlTtsEngine.setTtsCallback(callback)
        /**
         * First parameter sourceText: text information to be synthesized. The value can contain a maximum of 500 characters.
         * Second parameter indicating the synthesis mode: The format is configA | configB | configC.
         * configA:
         * MLTtsEngine.QUEUE_APPEND: After an audio synthesis task is generated, the audio synthesis task is processed as follows: If playback is going on, the task is added to the queue for execution in sequence; if playback pauses, the playback is resumed and the task is added to the queue for execution in sequence; if there is no playback, the audio synthesis task is executed immediately.
         * MLTtsEngine.QUEUE_FLUSH: The ongoing TTS task and playback are stopped immediately, all TTS tasks in the queue are cleared, and the current TTS task is executed immediately and played.
         * configB:
         * MLTtsEngine.OPEN_STREAM: The synthesized audio data is output through onAudioAvailable.
         * configC:
         * MLTtsEngine.EXTERNAL_PLAYBACK: external playback mode. The player provided by the SDK is shielded. You need to process the audio output by the onAudioAvailable callback API. In this case, the playback-related APIs in the callback APIs become invalid, and only the callback APIs related to audio synthesis can be listened.
         */
// Use the built-in player of the SDK to play speech in queuing mode.
        val id = mlTtsEngine.speak(sourceText, MLTtsEngine.OPEN_STREAM)
        mlTtsEngine.resume()
// In queuing mode, the synthesized audio stream is output through onAudioAvailable, and the built-in player of the SDK is used to play the speech.
// String id = mlTtsEngine.speak(sourceText, MLTtsEngine.QUEUE_APPEND | MLTtsEngine.OPEN_STREAM);
// In queuing mode, the synthesized audio stream is output through onAudioAvailable, and the audio stream is not played, but controlled by you.
// String id = mlTtsEngine.speak(sourceText, MLTtsEngine.QUEUE_APPEND | MLTtsEngine.OPEN_STREAM | MLTtsEngine.EXTERNAL_PLAYBACK);


    }
}