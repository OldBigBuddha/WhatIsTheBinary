package dev.oldbigbuddha.whatisthebinary

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_question.*
import kotlin.random.Random

// Reference: https://www.filesignatures.net/index.php?page=all
// PNG: 89 50 4E 47 0D 0A 1A 0A
// PDF: 25 50 44 46
// MP3: 49 44 33
// ELF: 7F 45 4C 46
// GZIP: 1F 8B 08

class QuestionActivity : AppCompatActivity() {

    companion object {
        const val NUMBER_OF_CHOICES_SAMPlE = 3
    }

    private var numberOfChoices = 0
    private var countQuestions = 0
    private var score = 0
    private lateinit var answer: String
    private val results = arrayListOf<String>()

    private val questions = arrayListOf(
        "PNG",
        "PDF",
        "MP3",
        "ELF",
        "GZIP"
    )

    private val correctAnswersMap = mapOf(
        "PNG" to "89 50 4E 47 0D 0A 1A 0A",
        "PDF" to "25 50 44 46",
        "MP3" to "49 44 33",
        "ELF" to "7F 45 4C 46",
        "GZIP" to "1F 8B 08"
    )

    private val dummyChoices = arrayListOf(
        "PNG",
        "PDF",
        "MP3",
        "MP4",
        "ELF",
        "GZIP",
        "BZIP2",
        "TAR",
        "AVI",
        "ZIP"
    )

    private val onClickChoiceButton = View.OnClickListener { button ->
        results.add(
            QuizResult(
                correctAnswersMap.getValue(answer),
                (button as Button).text.toString(),
                answer
            ).toString()
        )
        if (isCorrect(button)) {
            score++
            Toast.makeText(
                this,
                "Correct",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                "Wrong...\n correct answer is `$answer`",
                Toast.LENGTH_SHORT
            )
                .show()
        }

        tv_score.text = "$score / $countQuestions"

        if (countQuestions == questions.size) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("NumberOfQuestions", questions.size)
            intent.putExtra("score", score)
            intent.putExtra("results", results)
            startActivity(intent)
        } else {
            displayQuestion()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        bt_choice_1.setOnClickListener(onClickChoiceButton)
        bt_choice_2.setOnClickListener(onClickChoiceButton)
        bt_choice_3.setOnClickListener(onClickChoiceButton)

        numberOfChoices = NUMBER_OF_CHOICES_SAMPlE

        questions.shuffle()
        displayQuestion()
    }

    private fun displayQuestion() {
        countQuestions++

        answer = questions[countQuestions - 1]
        tv_question.text = correctAnswersMap[answer]

        val choices = arrayListOf<String>()
        choices.add(answer)

        dummyChoices.shuffle()
        for (i in 0 until (numberOfChoices - 1)) {
            choices.add(generateChoice(choices))
        }

        choices.shuffle()
        bt_choice_1.text = choices[0]
        bt_choice_2.text = choices[1]
        bt_choice_3.text = choices[2]
    }

    private fun generateChoice(choices: List<String>): String {
        val choice = dummyChoices[Random.nextInt(dummyChoices.size)]
        if (choice in choices) return generateChoice(choices)
        return choice
    }

    private fun isCorrect(choice: Button) = choice.text.toString() == answer

}
