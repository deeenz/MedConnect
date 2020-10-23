package com.bits9.medconnect

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var mForgotPassword: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        auth = FirebaseAuth.getInstance()
        val email = intent.getStringExtra("UserEmail")
        email_forgot_password.setText(email)
        mForgotPassword = findViewById(R.id.forgot_password_activity)
    }

    fun retrievePassword(view: View){
        retrieve_password_button.startAnimation()
        auth.sendPasswordResetEmail(email_forgot_password.text.toString()).addOnCompleteListener(this){task ->
            if (task.isSuccessful){
                retrieve_password_button.revertAnimation{
                    retrieve_password_button.text = getText(R.string.retrieve_password)
                    retrieve_password_button.background = getDrawable(R.drawable.big_button_shape)
                }
                val imm : InputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                Snackbar.make(mForgotPassword, "Password Retrieval Email Sent Successfully", Snackbar.LENGTH_LONG).show()
            }else{
                retrieve_password_button.revertAnimation{
                    retrieve_password_button.text = getText(R.string.retrieve_password)
                    retrieve_password_button.background = getDrawable(R.drawable.big_button_shape)
                }
                val imm : InputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                Snackbar.make(mForgotPassword, task.exception?.localizedMessage.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
    }
}

