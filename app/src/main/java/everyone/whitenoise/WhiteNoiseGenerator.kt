package everyone.whitenoise

import kotlin.random.Random

class WhiteNoiseGenerator : NoiseGenerator() {
    companion object {
        const val NOISE_NAME = "White Noise"
    }

    override fun getNextSample(): Short {
        return Random.nextInt(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
    }
}