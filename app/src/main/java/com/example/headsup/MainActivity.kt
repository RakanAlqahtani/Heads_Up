package com.example.headsup

import android.app.ProgressDialog
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Surface
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.headsupprep.API.APIClient
import com.example.headsupprep.API.APIInterface
import com.example.headsupprep.Model.Celebrity
import com.example.headsupprep.Model.CelebrityItem
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var progressDialog: ProgressDialog
    var celebrity = Celebrity()
    var count = 0
    private   var  start_timer_in_millis = 600000

    private lateinit var mCountDowenTimer : CountDownTimer

    private  var mTimerRunning : Boolean = false

    private var mTimeLeftInMillies = start_timer_in_millis
    private var gameActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getCelebrity()

btnStart.setOnClickListener{

    newTimer()
}
    }






    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            count++
            setUpUI(count)
//            newTimer(mTimerRunning)

           updateStatus(true)
        }

        else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            updateStatus(false)

        }
    }



    private fun newTimer(){
        if(!gameActive){
            gameActive = true
            textView.text = "Please Rotate Device"
            btnStart.isVisible = false
            val rotation = windowManager.defaultDisplay.rotation
            if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180){
                updateStatus(false)
            }else{
                updateStatus(true)
            }

            object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tvtimer.text = "Time: ${millisUntilFinished / 1000}"
                }

                override fun onFinish() {
                    gameActive = false
                    tvtimer.text = "Time: --"
                    textView.text = "Heads Up!"
                    btnStart.isVisible = true
                    updateStatus(false)
                }
            }.start()
        }
    }

    private fun setUpUI(id : Int) {
        if(id < celebrity.size){
            tvName.text = celebrity[id].name
            tvTaboo1.text = celebrity[id].taboo1
            tvTaboo2.text = celebrity[id].taboo2
            tvTaboo3.text = celebrity[id].taboo3
        }    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
//
//            tvTaboo1.text = "celebrity.name"
//
//            print("!!!!!!!!!!!!!!!!!")
////
////            progressDialog = ProgressDialog(this)
////            progressDialog.setMessage("Please Wait")
////            getCelebrity()
//
//        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
//            tvTaboo1.text = "celebrity.name"
//
//        }
//    }

    private fun getCelebrity() {
        var apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        apiInterface?.getCelebrities()?.enqueue(object : Callback<Celebrity>{
            override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>) {
                celebrity = response.body()!!


            }

            override fun onFailure(call: Call<Celebrity>, t: Throwable) {
                Toast.makeText(this@MainActivity,"some thing wrong",Toast.LENGTH_LONG).show()




            }


        })
    }


    private fun updateStatus(showCelebrity: Boolean){
        if(showCelebrity){
            llcelebrity.isVisible = true
            llMain.isVisible = false
        }else{
            llcelebrity.isVisible = false
            llMain.isVisible = true
        }
    }

}