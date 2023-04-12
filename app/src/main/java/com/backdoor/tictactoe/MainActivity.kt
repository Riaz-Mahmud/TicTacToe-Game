package com.backdoor.tictactoe

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.backdoor.tictactoe.activities.*
import com.backdoor.tictactoe.databinding.ActivitySplashBinding
import com.backdoor.tictactoe.utils.Constants
import com.backdoor.tictactoe.utils.SharedPreference

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG: String = MainActivity::class.java.simpleName
    }

    private lateinit var mSharedPreference: SharedPreference
    private lateinit var mBinding: ActivitySplashBinding
    private var mCountDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "MainActivity started.")
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        mSharedPreference = SharedPreference(this)

        val weHaveGameSaved = mSharedPreference.getValue(
            Boolean::class.java,
            Constants.KEY_IS_GAME_SAVED,
            false
        ) == true

        if (weHaveGameSaved) {
            Log.i(TAG, "Game saved detected!")
            navigateToScene()
            finish()
        } else {
            goToWelcomeActivity();
            finish()
        }

    }

    private fun goToWelcomeActivity() {
        startActivity(Intent(this, WelcomeActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun navigateToScene() {
        Log.i(TAG, "Opening Scene activity...")
        startActivity(Intent(this, Scene::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun cancelTimer() {
        mCountDownTimer?.let {
            it.cancel()
            mCountDownTimer = null
        }
    }

    override fun onStop() {
        cancelTimer()
        super.onStop()
    }

    override fun onDestroy() {
        cancelTimer()
        super.onDestroy()
    }
}