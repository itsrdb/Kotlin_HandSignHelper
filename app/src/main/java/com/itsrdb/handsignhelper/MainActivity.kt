package com.itsrdb.handsignhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import net.gotev.speech.Speech
import net.gotev.speech.GoogleVoiceTypingDisabledException

import net.gotev.speech.SpeechRecognitionNotAvailable

import net.gotev.speech.SpeechDelegate
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Speech.init(this, getPackageName());

        val btn = findViewById<Button>(R.id.button)
        val micView = findViewById<net.gotev.speech.ui.SpeechProgressView>(R.id.progress)

        btn.setOnClickListener {
            try {
                Speech.getInstance().startListening(micView, object : SpeechDelegate {
                    override fun onStartOfSpeech() {
                        Log.i("speech", "speech recognition is now active")
                    }

                    override fun onSpeechRmsChanged(value: Float) {
                        Log.d("speech", "rms is now: $value")
                    }

                    override fun onSpeechPartialResults(results: List<String>) {
                        val str = StringBuilder()
                        for (res in results) {
                            str.append(res).append(" ")
                        }
                        Log.i("speech", "partial result: " + str.toString().trim { it <= ' ' })
                        val tvMain = findViewById<TextView>(R.id.tv_main)
                        tvMain.text = str.toString()
                    }

                    override fun onSpeechResult(result: String) {
                        Log.i("speech", "result: $result")
                        val tvMain = findViewById<TextView>(R.id.tv_main)
                        tvMain.text = result
                    }
                })
            } catch (exc: SpeechRecognitionNotAvailable) {
                Log.e("speech", "Speech recognition is not available on this device!")
                // You can prompt the user if he wants to install Google App to have
                // speech recognition, and then you can simply call:
                //
                // SpeechUtil.redirectUserToGoogleAppOnPlayStore(this);
                //
                // to redirect the user to the Google App page on Play Store
            } catch (exc: GoogleVoiceTypingDisabledException) {
                Log.e("speech", "Google voice typing must be enabled!")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Speech.getInstance().shutdown();
    }
}