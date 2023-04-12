package com.backdoor.tictactoe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.backdoor.tictactoe.R
import com.backdoor.tictactoe.databinding.ActivitySettingsBinding
import com.backdoor.tictactoe.models.GameMoves
import com.backdoor.tictactoe.utils.AppUtils
import com.backdoor.tictactoe.utils.Constants
import com.backdoor.tictactoe.utils.SharedPreference

class Settings : AppCompatActivity() {
    companion object {
        private val TAG: String = Settings::class.java.simpleName
    }

    private lateinit var mBinding: ActivitySettingsBinding
    private lateinit var mSharedPreference: SharedPreference
    private var maxNumberOfPlayer: Int = 2
    private var minNumberOfPlayer: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        mSharedPreference = SharedPreference(this)
        mSharedPreference.putValue(
            Constants.KEY_SAVED_CURRENT_ACTIVITY,
            Constants.Activity.Settings
        )

        setBindings()
    }

    private fun setBindings() {
        numberOfPlayerBinding()
        whoPlayFirstBinding()
        difficultyLevelBinding()
    }

    private fun numberOfPlayerBinding(){
        updateNumberOfPlayers()

        mBinding.numberOfPlayerLayout.subtractPlayer.setOnClickListener {
            val numberOfPlayer = mSharedPreference.getValue(
                Int::class.java,
                Constants.KEY_NUMBER_OF_PLAYER,
                minNumberOfPlayer
            )

            numberOfPlayer?.let {
                if (numberOfPlayer - 1 >= minNumberOfPlayer) {
                    mSharedPreference.putValue(Constants.KEY_NUMBER_OF_PLAYER, numberOfPlayer - 1)
                    updateNumberOfPlayers()
                }
            }
        }

        mBinding.numberOfPlayerLayout.addPlayer.setOnClickListener {
            val numberOfPlayer = mSharedPreference.getValue(
                Int::class.java,
                Constants.KEY_NUMBER_OF_PLAYER,
                minNumberOfPlayer
            )

            numberOfPlayer?.let {
                if (numberOfPlayer + 1 <= maxNumberOfPlayer) {
                    mSharedPreference.putValue(Constants.KEY_NUMBER_OF_PLAYER, numberOfPlayer + 1)
                    updateNumberOfPlayers()
                }
            }

        }
    }

    private fun updateNumberOfPlayers(){
        val numberOfPlayer = mSharedPreference.getValue(
            Int::class.java,
            Constants.KEY_NUMBER_OF_PLAYER,
            minNumberOfPlayer
        )

        mBinding.numberOfPlayerLayout.numberOfPlayerTxt = numberOfPlayer

        if (numberOfPlayer!! == minNumberOfPlayer) {
            mBinding.numberOfPlayerLayout.isSubtractPlayerActive = false
            mBinding.numberOfPlayerLayout.isAddPlayerActive = true
        } else if (numberOfPlayer in (minNumberOfPlayer + 1) until maxNumberOfPlayer) {
            mBinding.numberOfPlayerLayout.isSubtractPlayerActive = true
            mBinding.numberOfPlayerLayout.isAddPlayerActive = true
        } else if (numberOfPlayer == maxNumberOfPlayer) {
            mBinding.numberOfPlayerLayout.isSubtractPlayerActive = true
            mBinding.numberOfPlayerLayout.isAddPlayerActive = false
        }
    }

    private fun whoPlayFirstBinding(){
        val isVibrationActive = mSharedPreference.getValue(
            Boolean::class.java,
            Constants.KEY_WHO_PLAY_FIRST,
            false
        ) == true

        mBinding.whoPlayFirstLayout.whoPlayFirst = isVibrationActive
        mBinding.whoPlayFirstLayout.whoPlayFirstSwitch.setOnCheckedChangeListener {
                _, isChecked ->
            run {
                mSharedPreference.putValue(Constants.KEY_WHO_PLAY_FIRST, isChecked)
            }
        }
    }

    private fun difficultyLevelBinding() {
        val difficultyLevelArray = resources.getStringArray(R.array.difficulty_levels)

        ArrayAdapter.createFromResource(
            this,
            R.array.difficulty_levels,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            // Populate the Spinner dropdown
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mBinding.difficultyLevelLayout.difficultyLevel.adapter = arrayAdapter

            // Set a starting value to show the Player
            mSharedPreference.getValue(
                keyClassType = String::class.java,
                key = Constants.KEY_DIFFICULTY_LEVEL,
                defaultValue = getString(R.string.random)
            ).let { level ->
                val levelIdx = difficultyLevelArray.indexOf(level)
                mBinding.difficultyLevelLayout.difficultyLevel.setSelection(levelIdx)
                Log.i(TAG, "Previous level = $level, Corresponding index = $levelIdx")
            }
        }

        mBinding.difficultyLevelLayout.difficultyLevel.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long)
                {
                    // An item was selected.
                    when (parent.getItemAtPosition(pos)) {
                        getString(R.string.easy) -> {
                            mSharedPreference.putValue(
                                Constants.KEY_DIFFICULTY_LEVEL,
                                getString(R.string.easy)
                            )
                        }
                        getString(R.string.medium) -> {
                            mSharedPreference.putValue(
                                Constants.KEY_DIFFICULTY_LEVEL,
                                getString(R.string.medium)
                            )
                        }
                        getString(R.string.hard) -> {
                            mSharedPreference.putValue(
                                Constants.KEY_DIFFICULTY_LEVEL,
                                getString(R.string.hard)
                            )
                        }
                        else -> {
                            mSharedPreference.putValue(
                                Constants.KEY_DIFFICULTY_LEVEL,
                                getString(R.string.random)
                            )
                        }
                    }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed();
    }
}