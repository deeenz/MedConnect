package com.bits9.medconnect

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class AnswersRecyclerViewAdapter(
    private val context : Context,
    private val mValues: ArrayList<Answer>
) : RecyclerView.Adapter<AnswersRecyclerViewAdapter.ViewHolder>() {

    val firestore: FirebaseFirestore  = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.answer_item, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val upVoteView: TextView = mView.findViewById(R.id.up_vote)
        val downVoteView: TextView = mView.findViewById(R.id.down_vote)
        val upVoteButton: ImageButton = mView.findViewById(R.id.upVote_button)
        val downVoteButton: ImageButton = mView.findViewById(R.id.downVotebutton)
        var questionPosition = 0
        var currentAnswer: Answer = mValues[questionPosition]

        init {
            upVoteButton.setOnClickListener{
                val currentUpvote = currentAnswer.upVotes + 1
                firestore.collection("Questions").document(currentAnswer.question).update("UpVote", currentUpvote)
            }

            downVoteButton.setOnClickListener{
                val currentDownvote = currentAnswer.downVotes + 1
                firestore.collection("Questions").document(currentAnswer.question).update("DownVote", currentDownvote)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.upVoteView.text = item.upVotes.toString()
        holder.downVoteView.text = item.downVotes.toString()
        holder.upVoteView.text = item.upVotes.toString()
        holder.downVoteView.text = item.downVotes.toString()
        holder.questionPosition = position
    }

}
