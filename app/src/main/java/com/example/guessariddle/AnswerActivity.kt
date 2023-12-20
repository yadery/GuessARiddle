package com.example.guessariddle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.guessariddle.databinding.ActivityAnswerBinding
import android.view.View

class ActivityAnswer : AppCompatActivity() {
    lateinit var binding: ActivityAnswerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)
        binding = ActivityAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var Answers: List<String?> = listOf(
            intent.getStringExtra("answer1"),
            intent.getStringExtra("answer2"),
            intent.getStringExtra("answer3"),
            intent.getStringExtra("answerCorrect")
        ).shuffled()

        binding.RiddelQuestionView.text = intent.getStringExtra("question")
        binding.FirstAnswerButton.text = Answers[0]
        binding.SecondAnswerButton.text = Answers[1]
        binding.ThirdAnswerButton.text = Answers[2]
        binding.FourthAnswerButton.text = Answers[3]
    }


    fun btnClickCheck(view: View) {
        var chosenValue = "";
        if (binding.FirstAnswerButton.isChecked) {
            chosenValue = binding.FirstAnswerButton.text.toString()
        }
        else if (binding.SecondAnswerButton.isChecked) {
            chosenValue = binding.SecondAnswerButton.text.toString()
        }
        else if (binding.ThirdAnswerButton.isChecked) {
            chosenValue = binding.ThirdAnswerButton.text.toString()
        }
        else if (binding.FourthAnswerButton.isChecked) {
            chosenValue = binding.FourthAnswerButton.text.toString()
        }
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("chosenAnswer", chosenValue)
        setResult(RESULT_OK, intent)
        finish()
    }
}