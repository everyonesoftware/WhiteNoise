package everyone.whitenoise

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources

class MainActivity : AppCompatActivity() {
    private var isPlaying = false
    private var noiseName = WhiteNoiseGenerator.NOISE_NAME
    private var selectedNoiseButton: Button? = null

    private fun createNoiseTypeButton(noiseName: String): Button
    {
        return Button(this).apply {
            text = noiseName
            setTextColor(Color.WHITE)
            background = AppCompatResources.getDrawable(context, R.drawable.button_unselected)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setPadding(24, 24, 24, 24)
                setMargins(16, 16, 16, 16)
            }

            setOnClickListener {
                updateNoiseType(noiseName, this)
            }
        }
    }

    private fun highlightButton(button: Button?) {
        // reset previous
        selectedNoiseButton?.background = AppCompatResources.getDrawable(this, R.drawable.button_unselected)

        // set new
        button?.background = AppCompatResources.getDrawable(this, R.drawable.button_selected)

        selectedNoiseButton = button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val whiteButton = createNoiseTypeButton(WhiteNoiseGenerator.NOISE_NAME)
        val pinkButton = createNoiseTypeButton(PinkNoiseGenerator.NOISE_NAME)
        val brownButton = createNoiseTypeButton(BrownNoiseGenerator.NOISE_NAME)
        val noiseLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.CENTER

            addView(whiteButton)
            addView(pinkButton)
            addView(brownButton)
        }
        val totalLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = android.view.Gravity.CENTER

            layoutParams = android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT
            )

            addView(noiseLayout)
        }

        // Root layout (full black screen)
        val root = android.widget.FrameLayout(this).apply {
            setBackgroundColor(Color.BLACK)
            addView(totalLayout)
        }

        setContentView(root)
    }

    private fun updateNoiseType(noiseName: String, noiseButton: Button)
    {
        val intent = Intent(this, NoiseService::class.java).apply {
            putExtra(NoiseService.NOISE_NAME, noiseName)
        }

        if (this.noiseName != noiseName || !isPlaying)
        {
            startForegroundService(intent)
            this.noiseName = noiseName
            highlightButton(noiseButton)
            isPlaying = true
        }
        else
        {
            stopService(intent)
            highlightButton(null)
            isPlaying = false
        }
    }
}