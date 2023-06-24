package com.dev334.idog.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev334.idog.adapter.DogAdapter
import com.dev334.idog.databinding.ActivityDisplayBinding
import com.dev334.idog.util.LruCacheManager

class DisplayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayBinding
    private lateinit var cacheManager: LruCacheManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get images from cache
        cacheManager = LruCacheManager.getInstance(this)
        val bitmaps = cacheManager.getAllImages()

        //setting up recycler view
        binding.recyclerViewDog.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)
        val adapter = DogAdapter(bitmaps)
        binding.recyclerViewDog.adapter = adapter

        binding.buttonClearDogs.setOnClickListener{
            cacheManager.clearCache()
            bitmaps.clear()
            adapter.notifyDataSetChanged()
        }
    }
}