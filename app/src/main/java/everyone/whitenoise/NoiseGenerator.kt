package everyone.whitenoise

abstract class NoiseGenerator {
    /**
     * Get the next sample from this [NoiseGenerator].
     */
    abstract fun getNextSample(): Short
}