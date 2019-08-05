package com.example.offer_wall

class Wheel(
    private val wheelListener: WheelListener?,
    private val frameDuration: Long,
    private val startIn: Long)
    : Thread() {

    var currentIndex: Int = 0
    private var isStarted: Boolean = false

    companion object {

        private val drawables = intArrayOf(
            R.drawable.apple,
            R.drawable.cherry,
            R.drawable.grape,
            R.drawable.heart,
            R.drawable.orange,
            R.drawable.seven,
            R.drawable.strawbery
        )

    }

    interface WheelListener {
        fun newImage(img: Int)
    }

    init {
        currentIndex = 0
        isStarted = true
    }

    private fun nextImg() {
        currentIndex++

        if (currentIndex == drawables.size) {
            currentIndex = 0
        }
    }

    override fun run() {
        try {
            sleep(startIn)
        } catch (e: InterruptedException) {
        }

        while (isStarted) {
            try {
                sleep(frameDuration)
            } catch (e: InterruptedException) {
            }

            nextImg()

            wheelListener?.newImage(drawables[currentIndex])
        }
    }

    fun stopWheel() {
        isStarted = false
    }

}