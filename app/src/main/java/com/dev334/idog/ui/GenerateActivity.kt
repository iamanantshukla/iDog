package com.dev334.idog.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.dev334.idog.databinding.ActivityGenerateBinding
import com.dev334.idog.viewmodel.GenerateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenerateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenerateBinding;
    private val generateViewModel by viewModel<GenerateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGenerate.setOnClickListener{
            generateViewModel.getImage()
            generateViewModel.image.observe(this) {
                val url: String? = it
                if (!url.isNullOrEmpty()) {
                    Glide.with(this)
                        .load(url)
                        .into(binding.imageView)
                }else{
                    Log.i("GenerateActivityDebugger", "onCreate: Error generating Image")
                }
            }
        }

    }
}