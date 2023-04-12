package com.backdoor.tictactoe.models

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.backdoor.tictactoe.utils.HistoryModal

internal class DatabaseHelper(context: Context) {
    private val historyDatabase: SharedPreferences
    private val gson: Gson

    init {
        historyDatabase = context.getSharedPreferences("historyDatabases", Context.MODE_PRIVATE)
        gson = Gson()
    }

    fun saveHistory(history: ArrayList<HistoryModal>) {
        val editor = historyDatabase.edit()
        editor.putString("history", gson.toJson(history))
        editor.apply()
    }

    val history: ArrayList<HistoryModal>
        get() {
            val historyString = historyDatabase.getString("history", null)
            val historyListType = object : TypeToken<ArrayList<HistoryModal?>?>() {}.type
            val history = gson.fromJson<ArrayList<HistoryModal>>(historyString, historyListType)
            return history ?: ArrayList()
        }

    val clearAllData: Boolean
        get() {
            return try {
                historyDatabase.edit().clear().apply();
                true
            }catch (e: Exception){
                false
            }
        }
}