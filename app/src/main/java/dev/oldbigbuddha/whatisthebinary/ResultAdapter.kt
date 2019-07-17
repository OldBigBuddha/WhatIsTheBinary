package dev.oldbigbuddha.whatisthebinary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_result.view.*

class ResultAdapter(
    _context: Context,
    private val list: List<QuizResult>
) : BaseAdapter() {


    private val layoutInflater =
        _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = layoutInflater.inflate(R.layout.item_result, parent, false)
        val result = getItem(position)
        view.tv_index_of_question.text = (position + 1).toString()
        view.tv_question_result.text = result.question
        view.tv_user_answer.text = result.userAnswer
        view.tv_correct_answer.text = result.correctAnswer
        return view
    }

    override fun getItem(position: Int) = list[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = list.size
}

data class QuizResult(
    val question: String,
    val userAnswer: String,
    val correctAnswer: String
) {

    val isCorrect = userAnswer == correctAnswer

    companion object {

        fun fromString(raw: String): QuizResult {
            if (!Regex(".*,.*,.*").containsMatchIn(raw)) {
                throw IllegalArgumentException("Correct Format: '\$question,\$userAnswer,\$correctAnswer'")
            } else {
                val divisions = raw.split(",")
                return QuizResult(divisions[0], divisions[1], divisions[2])
            }
        }
    }

    override fun toString() = "$question,$userAnswer,$correctAnswer"
}