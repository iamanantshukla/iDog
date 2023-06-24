package com.dev334.idog.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev334.idog.databinding.ActivityMainBinding
import com.dev334.idog.util.LruCacheManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGenerateDogs.setOnClickListener{
            var intent = Intent(this, GenerateActivity::class.java);
            startActivity(intent)
        }

        binding.button2.setOnClickListener{
            var intent = Intent(this, DisplayActivity::class.java);
            startActivity(intent)
        }

        val lruCacheManager = LruCacheManager.getInstance(this)
        lruCacheManager.getAllImages()
    }
}