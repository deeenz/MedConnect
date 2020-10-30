package com.bits9.medconnect

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class AnswerActivity : AppCompatActivity() {
    private lateinit var adapter: AnswersRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

//        var answer = intent.getStringArrayListExtra("QUESTION_POS")//TODO get real answers
        var answer = arrayListOf<Answer>(Answer("asasa","sdsd00", 2,2,"sdsd","dsd"))
        var recyclerView: RecyclerView = findViewById(R.id.answers_recycler_view)
        adapter = AnswersRecyclerViewAdapter(this, answer)
        recyclerView.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
