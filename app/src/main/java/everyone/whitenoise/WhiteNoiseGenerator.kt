package everyone.whitenoise

import kotlin.random.Random

class WhiteNoiseGenerator {
    fun nextSample(): Short {
        return Random.nextInt(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
    }
}