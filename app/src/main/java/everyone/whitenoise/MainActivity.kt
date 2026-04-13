package everyone.whitenoise

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val button = Button(this).apply {
            text = "Start White Noise"
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
            button.text = "Start White Noise"
        } else {
            startForegroundService(intent)
            button.text = "Stop White Noise"
        }

        isPlaying = !isPlaying
    }
}