package com.oyegbite.tictactoe.models

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oyegbite.tictactoe.utils.historyModal

internal class DatabaseHelper(context: Context) {
    private val historyDatabase: SharedPreferences
    private val gson: Gson

    init {
        historyDatabase = context.getSharedPreferences("historyDatabases", Context.MODE_PRIVATE)
        gson = Gson()
    }

    fun saveHistory(history: ArrayList<historyModal>) {
        val editor = historyDatabase.edit()
        editor.putString("history", gson.toJson(history))
        editor.apply()
    }

    val history: ArrayList<historyModal>
        get() {
            val historyString = historyDatabase.getString("history", null)
            val historyListType = object : TypeToken<ArrayList<historyModal?>?>() {}.type
            val history = gson.fromJson<ArrayList<historyModal>>(historyString, historyListType)
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