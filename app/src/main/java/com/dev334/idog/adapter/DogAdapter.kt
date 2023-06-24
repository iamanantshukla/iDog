package com.dev334.idog.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev334.idog.databinding.CardImageBinding

class DogAdapter(private val dogList: List<Bitmap>) : RecyclerView.Adapter<DogAdapter.ViewHolder>(){

    class ViewHolder(val binding : CardImageBinding) : RecyclerView.ViewHolder(binding.root) {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardImageBinding.inflate((LayoutInflater.from(parent.context))))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imageDog.setImageBitmap(dogList[position])
    }
    override fun getItemCount(): Int {
        return dogList.size
    }
}