package com.backdoor.tictactoe.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.backdoor.tictactoe.R
import com.backdoor.tictactoe.databinding.ActivityHistoryBinding
import com.backdoor.tictactoe.models.DatabaseHelper
import com.backdoor.tictactoe.models.HistoryAdapter
import com.backdoor.tictactoe.utils.HistoryModal

class HistoryActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityHistoryBinding
    private var historyAdapter: HistoryAdapter? = null
    private var historyList = ArrayList<HistoryModal>()
    private lateinit var databaseHelper: DatabaseHelper

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_history)

        databaseHelper = DatabaseHelper(this)
        historyList = databaseHelper.history

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        mBinding.idHistoryItems.layoutManager = layoutManager
        mBinding.idHistoryItems.isNestedScrollingEnabled = false
        historyAdapter = HistoryAdapter(historyList)
        mBinding.idHistoryItems.adapter = historyAdapter

        mBinding.resetHistoryBtn.setOnClickListener{
            if (databaseHelper.clearAllData){
                historyAdapter!!.notifyDataSetChanged()
                super.onBackPressed()
            }

        }
    }
}