package com.mobiledevbrain.timefighter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var tapMeButton: Button
    private lateinit var gameScoreTextView: TextView
    private lateinit var timeLeftTextView: TextView
    private var score = 0
    private var gameStarted = false
    private lateinit var countDownTimer: CountDownTimer
    internal var initialCountDown: Long = 5000
    internal var countDownInterval: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupViewsInitialState()
        setupTimer()
    }

    private fun setupViews() {
        tapMeButton = findViewById(R.id.tap_me_button)
        tapMeButton.setOnClickListener {
            incrementScore()
        }

        gameScoreTextView = findViewById(R.id.game_score)
        timeLeftTextView = findViewById(R.id.time_left)
    }

    private fun setupViewsInitialState() {
        val startGameScore = getString(R.string.game_score, 0.toString())
        gameScoreTextView.text = startGameScore

        val initialTimeLeft = initialCountDown / 1000
        timeLeftTextView.text = getString(R.string.time_left, initialTimeLeft.toString())
    }

    private fun setupTimer() {
        countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.time_left, timeLeft.toString())
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, getString(R.string.game_over_message, score.toString()), Toast.LENGTH_SHORT).show()
                resetGame()
            }
        }
    }

    private fun startGame() {
        if (!gameStarted) {
            countDownTimer.start()
            gameStarted = true
        }
    }

    private fun resetGame() {
        setupViewsInitialState()
        setupTimer()
        score = 0
        gameStarted = false
    }
    
    private fun incrementScore() {
        startGame()
        score += 1
        val newScore = getString(R.string.game_score, score.toString())
        gameScoreTextView.text = newScore
    }
}
