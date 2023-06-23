package com.dev334.idog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev334.idog.databinding.ActivityMainBinding
import com.dev334.idog.ui.GenerateActivity

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
    }
}