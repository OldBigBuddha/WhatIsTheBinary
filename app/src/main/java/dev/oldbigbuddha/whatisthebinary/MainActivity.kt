package dev.oldbigbuddha.whatisthebinary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_type_to_binary.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("question_type", QuestionType.TYPE_TO_BINARY)
            startActivity(intent)
        }

        bt_binary_to_type.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("question_type", QuestionType.BINARY_TO_TYPE)
            startActivity(intent)
        }
    }
}

enum class QuestionType constructor(val value: Int) {
    BINARY_TO_TYPE(0),
    TYPE_TO_BINARY(1);

    companion object {
        fun fromInt(_value: Int): QuestionType =
            values().firstOrNull { it.value == _value } ?: BINARY_TO_TYPE
    }
}
