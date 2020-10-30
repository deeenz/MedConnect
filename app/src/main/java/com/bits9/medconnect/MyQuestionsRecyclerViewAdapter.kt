package com.bits9.medconnect

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class MyQuestionsRecyclerViewAdapter(
    private val context : Context,
    private val mValues: ArrayList<Questions>
) : RecyclerView.Adapter<MyQuestionsRecyclerViewAdapter.ViewHolder>() {

    val firestore: FirebaseFirestore  = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_questions, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.question.text = item.question
        holder.questioner.text = "By " + item.questioner
        holder.questionPosition = position

    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val question: TextView = mView.findViewById(R.id.question_text_view)
        val questioner: TextView = mView.findViewById(R.id.questioner_textView)
        var questionPosition = 0
        var currentQuestion: Questions = mValues[questionPosition]

        init {
            question?.setOnClickListener{
                val intent = Intent(context, AnswerActivity::class.java)
                intent.putExtra("QUESTION_POS", questionPosition)
                intent.putExtra("QUESTION_DOCUMENT_ID", currentQuestion.answers)
                context.startActivity(intent)
            }
        }
    }
}
