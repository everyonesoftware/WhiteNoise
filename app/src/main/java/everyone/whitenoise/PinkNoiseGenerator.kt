package everyone.whitenoise

import kotlin.random.Random

class PinkNoiseGenerator {

    private var b0 = 0.0
    private var b1 = 0.0
    private var b2 = 0.0
    private var b3 = 0.0
    private var b4 = 0.0
    private var b5 = 0.0
    private var b6 = 0.0

    fun nextSample(): Short {
        val white = Math.random() * 2 - 1.0

        b0 = 0.99886 * b0 + white * 0.0555179
        b1 = 0.99332 * b1 + white * 0.0750759
        b2 = 0.96900 * b2 + white * 0.1538520
        b3 = 0.86650 * b3 + white * 0.3104856
        b4 = 0.55000 * b4 + white * 0.5329522
        b5 = -0.7616 * b5 - white * 0.0168980

        val pink = (b0 + b1 + b2 + b3 + b4 + b5 + b6 + white * 0.5362)
        b6 = white * 0.115926

        // scale down to avoid clipping
        val sample = (pink * 0.11 * Short.MAX_VALUE)

        return sample.toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
    }
}