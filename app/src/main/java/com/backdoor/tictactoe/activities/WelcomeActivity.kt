package com.backdoor.tictactoe.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.backdoor.tictactoe.R
import com.backdoor.tictactoe.utils.AppUtils
import com.backdoor.tictactoe.utils.Constants
import com.backdoor.tictactoe.utils.SharedPreference
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    private var shouldCancelCurrDialog = false
    private lateinit var mSharedPreference: SharedPreference
    private var minNumberOfPlayer: Int = 1
    private var maxNumberOfPlayer: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        mSharedPreference = SharedPreference(this)

        playGame()
        historyBtnPress();
        exitBtnPress()
        settingBtnPress()
    }

    private fun historyBtnPress() {
        historyBtn.setOnClickListener{
            startActivity(Intent(this,HistoryActivity::class.java))
        }
    }

    private fun playGame() {
        playGameBtn.setOnClickListener{
            val numberOfPlayer = mSharedPreference.getValue(
                Int::class.java,
                Constants.KEY_NUMBER_OF_PLAYER,
                minNumberOfPlayer
            )

            if (numberOfPlayer == minNumberOfPlayer){
                savePlayMode(Constants.VALUE_PLAY_MODE_AI)
            }else if (numberOfPlayer == maxNumberOfPlayer){
                savePlayMode(Constants.VALUE_PLAY_MODE_FRIEND)
            }
        }
    }

    private fun savePlayMode(playMode: String) {
        mSharedPreference.putValue(Constants.KEY_PLAY_MODE, playMode)
        navigateToEnterPlayerNameActivity()
    }

    private fun navigateToEnterPlayerNameActivity() {
        startActivity(Intent(this, EnterPlayerName::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun settingBtnPress() {
        settingBtn.setOnClickListener{
            startActivity(Intent(this, Settings::class.java))
        }
    }

    private fun exitBtnPress() {
        exitBtn.setOnClickListener {
            showExitGameDialog()
        }
    }

    private fun showExitGameDialog() {
        val dialog = MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.confirm_exit))
            .setNeutralButton(
                getString(R.string.yes)
            ) { dialog, _ ->
                dialog.dismiss()
                finish()
                super.onBackPressed()
            }
            .setPositiveButton(
                getString(R.string.no)
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(shouldCancelCurrDialog)
            .show()

        // Display dialog at bottom of screen
        AppUtils.displayDialogAt(dialog, Gravity.BOTTOM)
    }

    override fun onBackPressed() {
        showExitGameDialog()
    }
}