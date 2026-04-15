package everyone.whitenoise

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var isPlaying = false

    private val startNoiseText = "Start Noise"
    private val stopNoiseText = "Stop Noise"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val button = Button(this).apply {
            text = startNoiseText
            setOnClickListener {
                toggleNoise(this)
            }
        }

        setContentView(button)
    }

    private fun toggleNoise(button: Button) {
        val intent = Intent(this, WhiteNoiseService::class.java)

        if (isPlaying) {
            stopService(intent)
            button.text = startNoiseText
        } else {
            startForegroundService(intent)
            button.text = stopNoiseText
        }

        isPlaying = !isPlaying
    }
}