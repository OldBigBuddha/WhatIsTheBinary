package dev.oldbigbuddha.whatisthebinary

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

    private lateinit var type: QuestionType

    private val answers = mapOf(
        "PNG" to "89 50 4E 47 0D 0A 1A 0A",
        "PDF" to "25 50 44 46",
        "MP3" to "49 44 33",
        "ELF" to "7F 45 4C 46",
        "GZIP" to "1F 8B 08"
    )

    private val typeChoices = arrayListOf(
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

    private val signatureChoices = arrayListOf(
        "89 50 4E 47 0D 0A 1A 0A",
        "49 44 33",
        "25 50 44 46",
        "7F 45 4C 46",
        "1F 8B 08",
        "4D 54 68 64",
        "CA FE BA BE",
        "50 4B 03 04",
        "3C 3F 78 6D 6C 20",
        "D0 CF 11 E0 A1 B1 1A E1"
    )

    private lateinit var answer: String

    private val onClickChoiceButton = View.OnClickListener { button ->
        if (isCorrect(button as Button)) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Wrong...\n correct answer is `$answer`", Toast.LENGTH_SHORT)
                .show()
        }
        displayQuestion()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        intent?.let { it ->
            type = QuestionType.fromInt(
                it.getIntExtra(
                    "question_type",
                    QuestionType.BINARY_TO_TYPE.value
                )
            )

            bt_choice_1.setOnClickListener(onClickChoiceButton)
            bt_choice_2.setOnClickListener(onClickChoiceButton)
            bt_choice_3.setOnClickListener(onClickChoiceButton)

            displayQuestion()
        } ?: throw IllegalAccessException("No intent")

    }

    private fun displayQuestion() {
        val r = Random.nextInt(answers.size)
        if (type === QuestionType.BINARY_TO_TYPE) {
            answer = answers.keys.toList()[r]
            tv_question.text = answers[answer]
            val questions = arrayListOf(answer)

            for (i in 0..1) {
                questions.add(generateTypeChoice(questions))
            }

            questions.shuffle()

            bt_choice_1.text = questions[0]
            bt_choice_2.text = questions[1]
            bt_choice_3.text = questions[2]
        }
    }

    private fun generateTypeChoice(choices: List<String>): String {
        val choice = typeChoices[Random.nextInt(typeChoices.size)]
        if (choice in choices) return generateTypeChoice(choices)
        return choice
    }

    private fun isCorrect(choice: Button) = choice.text.toString() == answer

}
