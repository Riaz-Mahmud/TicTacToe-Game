package com.backdoor.tictactoe.models

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.backdoor.tictactoe.R
import com.backdoor.tictactoe.utils.HistoryModal

class HistoryAdapter(
    private var historyList: ArrayList<HistoryModal>,
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.history_item,
            parent, false
        )
        return HistoryViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.sl.text = (position + 1).toString()
        holder.name.text = historyList[position].playerName
        holder.position.text = historyList[position].playerPosition
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sl: TextView = itemView.findViewById(R.id.historyItemSl)
        val name: TextView = itemView.findViewById(R.id.historyItemName)
        val position: TextView = itemView.findViewById(R.id.historyItemPosition)
    }
}