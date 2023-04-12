package com.backdoor.tictactoe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.backdoor.tictactoe.R
import com.backdoor.tictactoe.databinding.ActivityChooseYourSideBinding
import com.backdoor.tictactoe.models.GameMoves
import com.backdoor.tictactoe.utils.AppUtils
import com.backdoor.tictactoe.utils.Constants
import com.backdoor.tictactoe.utils.SharedPreference

class ChooseYourSide : AppCompatActivity() {
    companion object {
        private val TAG: String = ChooseYourSide::class.java.simpleName
    }

    private lateinit var mBinding: ActivityChooseYourSideBinding
    private lateinit var mSharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_your_side)

        mSharedPreference = SharedPreference(this)
        mSharedPreference.putValue(
            Constants.KEY_SAVED_CURRENT_ACTIVITY,
            Constants.Activity.ChooseYourSide
        )

        setBindingsAndListeners()
    }

    private fun setBindingsAndListeners() {
        mBinding.playWithAI = !AppUtils.isTwoPlayerMode(TAG, mSharedPreference)
        mBinding.playerOneName =
            mSharedPreference.getValue(String::class.java, Constants.KEY_PLAYER_1_NAME)

        // Save 1st Player as "O" if "O" is selected
        mBinding.oChoose.setOnClickListener {
            val isOChecked = mBinding.oChoose.isChecked
            Log.i(TAG, "O checked is $isOChecked")

            if (isOChecked) {
                mBinding.isSideChosen = true
                mBinding.isSideChosenX = false

                mSharedPreference.putValue(
                    Constants.KEY_PLAYER_1_SIDE,
                    Constants.VALUE_PLAYER_1_SIDE_O
                )

                val isXChecked = mBinding.xChoose.isChecked
                Log.i(TAG, "X checked is $isXChecked")
                if (isXChecked) { // Uncheck "X"
                    mBinding.xChoose.isChecked = false
                    Log.i(TAG, "X has been unchecked")
                }
            }
        }

        // Save 1st Player as "X" if "X" is selected
        mBinding.xChoose.setOnClickListener {
            val isXChecked = mBinding.xChoose.isChecked
            Log.i(TAG, "X checked is $isXChecked")

            if (isXChecked) {
                mBinding.isSideChosen = true
                mBinding.isSideChosenX = true

                mSharedPreference.putValue(
                    Constants.KEY_PLAYER_1_SIDE,
                    Constants.VALUE_PLAYER_1_SIDE_X
                )

                val isOChecked = mBinding.oChoose.isChecked
                Log.i(TAG, "O checked is $isOChecked")
                if (isOChecked) { // Uncheck "O"
                    mBinding.oChoose.isChecked = false
                    Log.i(TAG, "O has been unchecked")
                }
            }
        }

        mBinding.startGame.setOnClickListener {
            resetFields()
            navigateToSceneActivity()
        }
    }

    private fun resetFields() {
        mSharedPreference.putValue(Constants.KEY_IS_GAME_SAVED, true)
        mSharedPreference.putValue(Constants.KEY_IS_GAME_OVER, false)
        mSharedPreference.putValue(Constants.KEY_PLAYER_1_SCORE, 0)
        mSharedPreference.putValue(Constants.KEY_PLAYER_2_SCORE, 0)
        mSharedPreference.putValue(Constants.KEY_FIRST_PLAYER, getString(R.string.first_player))
        mSharedPreference.putValue(Constants.KEY_IS_PLAYER_ONE_TURN, true)
        mSharedPreference.putValue(Constants.KEY_GAME_MOVES, GameMoves(arrayListOf()))
    }

    private fun navigateToSceneActivity() {
        startActivity(Intent(this, Scene::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}