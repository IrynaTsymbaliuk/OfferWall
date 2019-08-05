package com.example.offer_wall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.game_fr.view.*
import java.util.*

class GameFragment : Fragment() {

    private lateinit var resultMessage: TextView
    private lateinit var slot1: ImageView
    private lateinit var slot2: ImageView
    private lateinit var slot3: ImageView
    private var wheel1: Wheel? = null
    private var wheel2: Wheel? = null
    private var wheel3: Wheel? = null
    private lateinit var runButton: Button
    private var isStarted: Boolean = false

    companion object {

        private val RANDOM = Random()

        fun randomLong(lower: Long, upper: Long): Long {
            return lower + (RANDOM.nextDouble() * (upper - lower)).toLong()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.game_fr, container, false)

        slot1 = rootView.slot_one
        slot2 = rootView.slot_two
        slot3 = rootView.slot_three
        runButton = rootView.run_button
        resultMessage = rootView.result_message

        runButton.setOnClickListener {
            if (isStarted) {
                wheel1!!.stopWheel()
                wheel2!!.stopWheel()
                wheel3!!.stopWheel()

                if (wheel1!!.currentIndex == wheel2!!.currentIndex && wheel2!!.currentIndex == wheel3!!.currentIndex) {
                    resultMessage.text = resources.getString(R.string.big_prize)
                } else if (wheel1!!.currentIndex == wheel2!!.currentIndex || wheel2!!.currentIndex == wheel3!!.currentIndex
                    || wheel1!!.currentIndex == wheel3!!.currentIndex
                ) {
                    resultMessage.text = resources.getString(R.string.little_prize)
                } else {
                    resultMessage.text = resources.getString(R.string.you_lose)
                }

                runButton.text = resources.getString(R.string.start)
                isStarted = false

            } else {

                wheel1 = Wheel(object : Wheel.WheelListener {
                    override fun newImage(img: Int) {
                        activity!!.runOnUiThread { slot1.setImageResource(img) }
                    }
                }, 200, randomLong(0, 200))

                wheel1!!.start()

                wheel2 = Wheel(object : Wheel.WheelListener {
                    override fun newImage(img: Int) {
                        activity!!.runOnUiThread { slot2.setImageResource(img) }
                    }
                }, 200, randomLong(150, 400))

                wheel2!!.start()

                wheel3 = Wheel(object : Wheel.WheelListener {
                    override fun newImage(img: Int) {
                        activity!!.runOnUiThread { slot3.setImageResource(img) }
                    }
                }, 200, randomLong(150, 400))

                wheel3!!.start()

                runButton.text = resources.getString(R.string.stop)
                resultMessage.text = ""
                isStarted = true
            }
        }

        return rootView

    }

}
