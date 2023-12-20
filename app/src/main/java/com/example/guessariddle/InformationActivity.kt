package com.example.guessariddle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.guessariddle.databinding.ActivityInformationBinding

class ActivityInfo : AppCompatActivity() {
    lateinit var binding: ActivityInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.TotalAnswers.text = "Всего ответов: " + intent.getStringExtra("answeredTotal")
        binding.CorrectAnswers.text = "Правильных ответов: " + intent.getStringExtra("answeredCorrect")
        binding.WrongAnswers.text = "Неправильных ответов: " + intent.getStringExtra("answeredWrong")
    }

    fun btnBack(view: View) {
        finish()
    }
}