package com.dev334.idog.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
            generateViewModel.getImage(this)
            generateViewModel.bitmap.observe(this) {
                if (it!=null) {
                    binding.imageView.setImageBitmap(it)
                }else{
                    Log.i("GenerateActivityDebugger", "onCreate: Error generating Image")
                }
            }
        }

        generateViewModel.isLoading.observe(this) {
            if(it){
                binding.buttonGenerate.isEnabled = false
                binding.buttonGenerate.setTextColor(ColorStateList.valueOf(Color.GRAY))
            }else{
                binding.buttonGenerate.isEnabled = true
                binding.buttonGenerate.setTextColor(ColorStateList.valueOf(Color.WHITE))
            }
        }
    }
}