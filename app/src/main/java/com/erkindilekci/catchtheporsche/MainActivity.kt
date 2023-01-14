package com.erkindilekci.catchtheporsche

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.erkindilekci.catchtheporsche.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var score=0
    private lateinit var binding: ActivityMainBinding
    var imageArray: ArrayList<ImageView> = ArrayList()
    var runnable: Runnable = Runnable {  }
    var handler: Handler = Handler(Looper.getMainLooper())
    var random = java.util.Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.timeText.text = "Time: 10"
        binding.scoreText.text = "Score: 0"

        imageArray.add(binding.imageView)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)

        imageArray.forEach {
            it.isVisible = false
        }
    }

    fun start(view: View){
        binding.button.isVisible = false
        makeVisible()

        object: CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                binding.timeText.text = "Time: ${p0/1000}"

            }

            override fun onFinish() {
                handler.removeCallbacks(runnable)
                val alert = AlertDialog.Builder(this@MainActivity)
                imageArray.forEach {
                    it.isVisible = false
                }
                alert.setTitle("Restart")
                alert.setMessage("Do want to restart the game?")
                alert.setPositiveButton("Yes") {dialogInterface, i ->
                    finish()
                    overridePendingTransition(0, 0)
                    startActivity(getIntent())
                    overridePendingTransition(0, 0)
                }

                alert.setNegativeButton("No") {dialogInterface, i ->
                    Toast.makeText(applicationContext, "Game Finished!", Toast.LENGTH_LONG).show()
                    binding.scoreText.text = "Score: 0"
                    finish()
                }
                alert.show()
            }
        }.start()
    }

    fun increaseTheScore(view: View){
        score+=1
        binding.scoreText.text = "Score: $score"
    }

    fun makeVisible(){
        runnable = object:Runnable{
            override fun run() {
                imageArray.forEach {
                    it.visibility = View.INVISIBLE
                }
                val random = java.util.Random()
                var randomNumber = random.nextInt(9)
                imageArray[randomNumber].visibility = View.VISIBLE
                handler.postDelayed(runnable,500)
            }

        }
        handler.post(runnable)
    }

}