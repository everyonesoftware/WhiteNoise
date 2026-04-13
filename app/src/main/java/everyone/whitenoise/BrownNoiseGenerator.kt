package everyone.whitenoise

import kotlin.random.Random

class BrownNoiseGenerator {
    private var last = 0.0

    fun nextSample(): Short {
        val white = (Math.random() * 2 - 1) // -1 to 1
        last = (last + white * 0.02) // smoothing factor

        // clamp
        if (last > 1) last = 1.0
        if (last < -1) last = -1.0

        return (last * Short.MAX_VALUE).toInt().toShort()
    }
}