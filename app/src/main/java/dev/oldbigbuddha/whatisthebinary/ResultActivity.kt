package dev.oldbigbuddha.whatisthebinary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val countQuestions = intent.getIntExtra("NumberOfQuestions", 10)
        val score = intent.getIntExtra("score", 0)
        val resultsStr = intent.getStringArrayListExtra("results")
        val results = arrayListOf<QuizResult>()
        for (result in resultsStr) {
            results.add(QuizResult.fromString(result))
        }

        tv_number_of_questions.text = countQuestions.toString()
        tv_score.text = score.toString()

        list_results.adapter = ResultAdapter(this, results)

        bt_return_home.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
