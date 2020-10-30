package com.bits9.medconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [questionsFragment.OnListFragmentInteractionListener] interface.
 */
class questionsFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var questions: ArrayList<Questions> = arrayListOf()
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        populateView()
    }

    fun populateView() {
        firestore.collection("Questions").get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                for (doc in querySnapshot.documentChanges) {
                    print(doc.toString())
                    val eachQuestion = doc.document.toObject(Questions::class.java)
                    questions.add(Questions(doc.document.data["question"].toString(), doc.document.data["questioner"].toString(), doc.document.id  )));
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (this.view is RecyclerView) {
            populateView()
            (this.view as RecyclerView).adapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_questions_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyQuestionsRecyclerViewAdapter(context, questions)

            }
        }
        return view
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            questionsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
