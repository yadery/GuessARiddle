package com.example.guessariddle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.guessariddle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val RiddleList: List<Riddle> = listOf(
        Riddle("Идёт сегодня дождик, Бери с собою …!", setOf<String>("Зонтик", "Игрушку", "Мячик", "Друга")),
        Riddle("За спиной у нас висит, Много сможет он вместить!", setOf<String>("Рюкзак", "Капюшон", "Футболка", "Куртка")),
        Riddle("На пол снова убежало, С моей кровати …", setOf<String>("Одеяло", "Подушка", "Игрушка", "Краб")),
        Riddle("Только на небо приплыли, Тут же солнце собой скрыли.", setOf<String>("Облака", "Дома", "Страницы", "Снег")),
        Riddle("Будем чистыми с тобой, Смоем пеной всё густой!", setOf<String>("Мыло", "Вода", "Мяч", "Чулан")),
        Riddle("В них прошли мы по дороге, Не промокли наши ноги!", setOf<String>("Сапоги", "Сандали", "Кроссовки", "Туфли")),
        Riddle("Она сидит на голове, Чтоб тепло было тебе!", setOf<String>("Шапка", "Зонтик", "Куртка", "Стержень")),
        Riddle("Она красивая игрушка, Танюше верная подружка.", setOf<String>("Кукла", "Мишка", "Рак", "Мячик")),
        Riddle("Как по ткани он пройдёт, Складочки все соберёт", setOf<String>("Утюг", "Кастрюля", "Кубик", "Пароход")),
        Riddle("На небо туча приплыла, Его с собой к нам привела.", setOf<String>("Дождь", "Еда", "Мячик", "Иголка")),
        Riddle("Её руками обниму И сразу сладко так усну.", setOf<String>("Подушка", "Дверь", "Пол", "Ковёр")),
        Riddle("Её сначала разверни, А потом уж в рот клади.", setOf<String>("Конфета", "Колбаса", "Хлеб", "Труба")),
        Riddle("Суп в тарелку мы нальём, Рукой затем её возьмём!", setOf<String>("Ложка", "Вилка", "Нож", "Ножницы")),
        Riddle("По утрам у тёти Маши Ем вкуснейшую я …", setOf<String>("Кашу", "Лук", "Чай", "Миг")),
        Riddle("Он разрежет всё подряд! Булку, фрукты и салат!", setOf<String>("Нож", "Ложка", "Вилка", "Ножницы"))
    )

    var GameList = (RiddleList.shuffled()).take(10);
    var gameInProgress = false;
    var answeredTotal = 0;
    var answeredCorrect = 0;
    var correctAnswer = 0;
    var questionAnswered = 0;
    var userAnswer = "";

    lateinit var binding: ActivityMainBinding;
    private var launcher: ActivityResultLauncher<Intent>? = null;
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);
        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val answer = result.data?.getStringExtra("chosenAnswer")
                    var corr = "Неправильный ответ"
                    binding.UserAnswerView.setVisibility(View.VISIBLE);
                    binding.AnswerCorrectnessView.setVisibility(View.VISIBLE);
                    binding.AnswerCorrectnessView.setTextColor(Color.RED)
                    if (answer == GameList.get(questionAnswered).answers.elementAtOrNull(0).toString()) {
                        corr = "Правильный ответ";
                        binding.AnswerCorrectnessView.setTextColor(Color.GREEN)
                        answeredCorrect++;
                    }
                    val qText = GameList.get(questionAnswered).riddleText;
                    questionAnswered++;
                    if (questionAnswered == 10)
                    {
                        binding.InfomationButton.isEnabled = true;
                        gameInProgress = false;
                        answeredTotal += questionAnswered;
                        questionAnswered = 0;
                        binding.QuestionButton.text = "Заново";
                    }
                    binding.QuestionButton.isEnabled = true;
                    binding.AnswerButton.isEnabled = false;
                    binding.RiddelQuest.text = qText;
                    binding.UserAnswerView.text = "Ваш ответ: " + answer;
                    binding.AnswerCorrectnessView.text = corr;
                }
            }
    }

    fun btnClickNext(view: View) {
        binding.UserAnswerView.setVisibility(View.GONE);
        binding.AnswerCorrectnessView.setVisibility(View.GONE);
        if (gameInProgress) {
            binding.RiddelQuest.text = GameList.get(questionAnswered).riddleText
            binding.Riddle.text = "Вопрос " + (questionAnswered+1)
            binding.AnswerButton.isEnabled = true
            binding.QuestionButton.isEnabled = false
            binding.QuestionButton.text = "Загадка"
        }
        else {
            GameList = (RiddleList.shuffled()).take(10)
            binding.RiddelQuest.text = GameList.get(questionAnswered).riddleText
            binding.AnswerButton.isEnabled = true
            binding.InfomationButton.isEnabled = false
            gameInProgress = true
            binding.Riddle.text = "Вопрос 1"
            questionAnswered = 0
            answeredCorrect = 0
        }
    }

    fun btnClickAnswer(view: View) {
        val intent = Intent(this, ActivityAnswer::class.java);
        intent.putExtra("question", GameList[questionAnswered].riddleText);
        intent.putExtra("answerCorrect", GameList[questionAnswered].answers.elementAtOrNull(0));
        intent.putExtra("answer1", GameList[questionAnswered].answers.elementAtOrNull(1));
        intent.putExtra("answer2", GameList[questionAnswered].answers.elementAtOrNull(2));
        intent.putExtra("answer3", GameList[questionAnswered].answers.elementAtOrNull(3));
        launcher?.launch(intent);
    }

    fun btnClickInfo(view: View) {
        val intent2 = Intent(this, ActivityInfo::class.java);
        intent2.putExtra("answeredTotal", answeredTotal.toString());
        intent2.putExtra("answeredCorrect", answeredCorrect.toString());
        intent2.putExtra("answeredWrong", (answeredTotal - answeredCorrect).toString());
        startActivity(intent2);
    }
}

class Riddle(
    val riddleText: String,
    var answers:Set<String>) {
}