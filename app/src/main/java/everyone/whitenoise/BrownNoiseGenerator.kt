package everyone.whitenoise

class BrownNoiseGenerator : NoiseGenerator() {
    companion object {
        const val NOISE_NAME = "Brown Noise"
    }
    private var last = 0.0

    override fun getNextSample(): Short {
        val white = (Math.random() * 2 - 1) // -1 to 1
        last = (last + white * 0.02) // smoothing factor

        // clamp
        if (last > 1) last = 1.0
        if (last < -1) last = -1.0

        return (last * Short.MAX_VALUE).toInt().toShort()
    }
}