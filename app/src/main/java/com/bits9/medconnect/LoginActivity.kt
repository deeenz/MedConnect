package com.bits9.medconnect

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var mLoginPage: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        auth = FirebaseAuth.getInstance()
        mLoginPage = findViewById(R.id.login_activity)
    }

    fun gotoSignUpPage(view: View) {
        val intent = Intent(view.context, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun emailValidator(email: CharSequence): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val emailPattern =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(emailPattern)
        matcher = pattern.matcher(email)
        return !matcher.matches()
    }

    fun gotoForgotPassword(view: View){
        val email = email_login_editText.text.toString()
        val intent = Intent(view.context, ForgotPasswordActivity::class.java)
        intent.putExtra("UserEmail", email)
        startActivity(intent)

    }

    fun login(view: View) {
        signin_button.startAnimation()
        val email = email_login_editText.text.toString()
        val password = password_login_editText.text.toString()
        when {
            email.isBlank() -> {
                email_login_editText.error = "Input your email"
                signin_button.revertAnimation {
                    signin_button.text = getText(R.string.sign_in)
                    signin_button.background = getDrawable(R.drawable.big_button_shape)
                }
            }
            emailValidator(email) -> {
                email_login_editText.error = "Invalid Email"
                signin_button.revertAnimation {
                    signin_button.text = getText(R.string.sign_in)
                    signin_button.background = getDrawable(R.drawable.big_button_shape)
                }
            }
            password.isBlank() || password.length < 6 -> {
                password_login_editText.error = "Password must be at least 6 digits long"
                signin_button.revertAnimation {
                    signin_button.text = getText(R.string.sign_in)
                    signin_button.background = getDrawable(R.drawable.big_button_shape)
                }
            }else ->{
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    if (user?.isEmailVerified!!){
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent)
                    }else{
                        signin_button.revertAnimation {
                            signin_button.text = getText(R.string.sign_in)
                            signin_button.background = getDrawable(R.drawable.big_button_shape)
                        }
                        val imm: InputMethodManager =
                            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view.windowToken, 0)
                        Snackbar.make(mLoginPage, "Your Email is not verified yet, check your mail to verify or tap to resend", Snackbar.LENGTH_LONG)
                            .setAction("Resend", View.OnClickListener {
                                val firebaseUser = auth.currentUser
                                firebaseUser?.sendEmailVerification()
                            }).show()
                    }
                }else{
                    signin_button.revertAnimation {
                        signin_button.text = getText(R.string.sign_in)
                        signin_button.background = getDrawable(R.drawable.big_button_shape)
                    }
                    val imm: InputMethodManager =
                        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    Snackbar.make(mLoginPage, task.exception?.localizedMessage.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        }
        }
    }
}

