package com.bits9.medconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.core.View

class CustomDialog : AppCompatActivity() {

   lateinit var  questionTextField: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_dialog)
        questionTextField = findViewById(R.id.question_editText)

    }

    fun submitQuestion(view: android.view.View) {
        var firestore:FirebaseFirestore = FirebaseFirestore.getInstance()
        var myQuestion = hashMapOf<String, String>("question" to questionTextField.text.toString(), "questioner" to "Nur")
        firestore.collection("Questions").document().set(myQuestion)
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
