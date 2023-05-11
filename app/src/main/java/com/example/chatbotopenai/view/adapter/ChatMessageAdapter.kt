package com.app.view.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.app.model.MessageModel
import com.app.utils.Utils
import com.app.view.listeners.OnListClickListener
import com.talkai.chatgpt.ai.app.databinding.RowChatBinding
import com.talkai.chatgpt.ai.app.databinding.RowTypingBinding


class ChatMessageAdapter (var context: Context, var list: ArrayList<MessageModel?>,
                          var onListClickListener: OnListClickListener
): RecyclerView.Adapter<ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    inner class MyViewHolder(val binding: RowChatBinding) : ViewHolder(binding.root)

    inner class LoadingViewHolder(val bindingLoading: RowTypingBinding) :
        ViewHolder(bindingLoading.root)

    override fun getItemViewType(position: Int): Int {
        return if (list.get(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {

            val binding = RowChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(binding)
        } else {
            val binding =
                RowTypingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LoadingViewHolder(binding)
        }
    }


    override fun getItemCount(): Int {
        return list.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            populateItemRows(holder, position)
        } else if (holder is LoadingViewHolder) {
            showLoadingView((holder as LoadingViewHolder?)!!, position)
        }
    }

    private fun showLoadingView(viewHolder: LoadingViewHolder, position: Int) {
        //ProgressBar would be displayed

    }

    private fun populateItemRows(holder: MyViewHolder, position: Int) {
        if (list[position]!!.role.equals("User")) {
            holder.binding.llUser.visibility = View.VISIBLE
            holder.binding.llAssistant.visibility = View.GONE
            holder.binding.halloweenLottie.visibility = View.GONE

            holder.binding.tvUserMessage.text = list[position]!!.message
        } else {
            holder.binding.llUser.visibility = View.GONE
            holder.binding.halloweenLottie.visibility = View.GONE
            holder.binding.llAssistant.visibility = View.VISIBLE
            holder.binding.tvAssistantMessage.text = list[position]!!.message
        }

        holder.itemView.setOnClickListener {
            Utils.copyTextToClipboard(list[position]!!.message,context)
        }
        holder.itemView.setOnLongClickListener {
            Utils.copyTextToClipboard(list[position]!!.message,context)

            return@setOnLongClickListener true

        }
    }



}