package com.oyegbite.tictactoe.models

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oyegbite.tictactoe.R
import com.oyegbite.tictactoe.utils.historyModal

class HistoryAdapter(
    private var historyList: ArrayList<historyModal>,
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.HistoryViewHolder {
        // this method is use to inflate the layout file
        // which we have created for our recycler view.
        // on below line we are inflating our layout file.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.history_item,
            parent, false
        )
        // at last we are returning our view holder
        // class with our item View File.
        return HistoryViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        // on below line we are setting data to our text view and our image view.
        holder.sl.text = (position+1).toString()
        holder.name.text = historyList.get(position).playerName
        holder.position.text = historyList.get(position).playerPosition
    }

    override fun getItemCount(): Int {
        // on below line we are returning
        // our size of our list
        return historyList.size
    }

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are initializing our course name text view and our image view.
        val sl: TextView = itemView.findViewById(R.id.historyItemSl)
        val name: TextView = itemView.findViewById(R.id.historyItemName)
        val position: TextView = itemView.findViewById(R.id.historyItemPosition)
    }
}