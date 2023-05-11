package com.app.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.talkai.chatgpt.ai.app.R


class ViewPagerIntroAdapter(var ctx:Context) : RecyclerView.Adapter<ViewPagerIntroAdapter.ViewHolder>() {
    // Array of images
    // Adding images from drawable folder
    private val images = intArrayOf(
        R.drawable.bg_intro_1,
        R.drawable.bg_intro_2,
        R.drawable.bg_intro_3,

    )



    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_intro_pager, parent, false)
        return ViewHolder(view)
    }

    // This method binds the screen with the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview
        holder.images.setImageResource(images[position])
    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return images.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var images: ImageView

        init {
            images = itemView.findViewById(R.id.image_view)
        }
    }


}
