package com.app.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.database.RecentChatModel
import com.app.view.listeners.OnListClickListener
import com.talkai.chatgpt.ai.app.databinding.RowHistoryBinding
import setOnMyClickListener

class RecentChatHistoryAdapter(
    var context: Context, var list: ArrayList<RecentChatModel>,
    var onListClickListener: OnListClickListener
) : RecyclerView.Adapter<RecentChatHistoryAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: RowHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                holder.binding.tvChatMessage.text = this.message

            }
            binding.ivDeleteChat.setOnMyClickListener {
                onListClickListener.onListClickSimple(position, list[position])
            }
            holder.itemView.setOnMyClickListener {
                onListClickListener.onListClick(position, list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}