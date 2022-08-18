package com.yourcompany.bullseye

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import com.yourcompany.bullseye.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
  private var sliderValue = 0
  private var targetValue = Random.nextInt(1, 100)
  private var totalScore = 0
  private var currentRound = 1


  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    supportActionBar?.hide()

    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    binding.targetTextView.text = targetValue.toString()
    binding .gameRoundTextView?.text = currentRound.toString()
    binding.hitMeButton.setOnClickListener {
     // Log.i("Button Click Event", "You clicked the Hit Me Button")
      showResult()
      totalScore += pointsForCurrentRound()
      binding.gameScoreTextView?.text = totalScore.toString()
    }

    binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        sliderValue = progress
      }

      override fun onStartTrackingTouch(seekBar: SeekBar?) {}

      override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    })

  }

  private fun differanceAmount() = abs(targetValue - sliderValue)

  private fun pointsForCurrentRound(): Int {
    val maxScore = 100
    val difference =differanceAmount()
    return maxScore - difference
  }

  private fun showResult() {
    val dialogTitle = alertTitle()
    val dialogMessage =
      getString(R.string.result_dialog_message, sliderValue, pointsForCurrentRound())
//    val dialogMessage = "The slider's value is $sliderValue"

    val builder = AlertDialog.Builder(this)

    builder.setTitle(dialogTitle)
    builder.setMessage(dialogMessage)
    builder.setPositiveButton(R.string.result_dialog_button_text) { dialog, _ ->
      dialog.dismiss()
      targetValue = Random.nextInt(1,100)
      binding.targetTextView.text = targetValue.toString()

      currentRound += 1
      binding.gameRoundTextView?.text = currentRound.toString()
    }

    builder.create().show()
  }

  private fun alertTitle():String{
    val differance = differanceAmount()

    val title: String = when {
        differance == 0 -> {
          getString(R.string.alert_title_1)
        }
        differance < 5 -> {
          getString(R.string.alert_title_2)
        }
        differance <= 10 -> {
          getString(R.string.alert_title_3)
        }
        else -> {
          getString (R.string.alert_title_4)
        }
    }

    return title

  }

}








