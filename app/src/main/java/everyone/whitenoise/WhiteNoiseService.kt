package everyone.whitenoise

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.*
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlin.concurrent.thread
import kotlin.random.Random

class WhiteNoiseService : Service() {

    private var isRunning = false
    private lateinit var audioTrack: AudioTrack

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotification())
        startNoise()
        return START_STICKY
    }

    override fun onDestroy() {
        stopNoise()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startNoise() {
        if (isRunning) return
        isRunning = true

        val sampleRate = 44100

        val bufferSize = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        val audioFormat = AudioFormat.Builder()
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .setSampleRate(sampleRate)
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
            .build()

        audioTrack = AudioTrack.Builder()
            .setAudioAttributes(audioAttributes)
            .setAudioFormat(audioFormat)
            .setBufferSizeInBytes(bufferSize)
            .setTransferMode(AudioTrack.MODE_STREAM)
            .build()

        audioTrack.play()

        thread(start = true) {
            val buffer = ShortArray(bufferSize)
            val noiseGenerator = BrownNoiseGenerator()

            while (isRunning) {
                for (i in buffer.indices) {
                    buffer[i] = noiseGenerator.nextSample()
                }
                audioTrack.write(buffer, 0, buffer.size)
            }
        }
    }

    private fun stopNoise() {
        isRunning = false

        if (::audioTrack.isInitialized) {
            try {
                audioTrack.stop()
            } catch (_: IllegalStateException) {}
            audioTrack.release()
        }
    }

    private fun createNotification(): Notification {
        val channelId = "white_noise_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "White Noise",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("White Noise Running")
            .setContentText("Tap to return to app")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
}