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
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var mSignUpPage: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        auth = FirebaseAuth.getInstance()
        mSignUpPage = findViewById(R.id.signup_activity)
        auth = FirebaseAuth.getInstance()
        autoLogin()
    }

    private fun autoLogin(){
        val user = auth.currentUser
        if(user != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun gotoLoginPage(view: View) {
        val intent = Intent(view.context, LoginActivity::class.java)
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

    fun createAccount(view: View) {
        get_started_button.startAnimation()
        val firstName = first_name_editText.text.toString()
        val lastName = last_name_editText.text.toString()


        val email = email_signup_editText.text.toString()
        val password = password_signup_editText.text.toString()
        // If sign in fails, display a message to the user.
        when {
            firstName.isBlank() -> {
                first_name_editText.error = "Input your First Name"
                get_started_button.revertAnimation {
                    get_started_button.text = getText(R.string.get_started)
                    get_started_button.background = getDrawable(R.drawable.big_button_shape)
                }
            }
            lastName.isBlank() -> {
                last_name_editText.error = "input your Last Name"
                get_started_button.revertAnimation {
                    get_started_button.text = getText(R.string.get_started)
                    get_started_button.background = getDrawable(R.drawable.big_button_shape)
                }
            }

            email.isBlank() -> {
                email_signup_editText.error = "Input your email"
                get_started_button.revertAnimation {
                    get_started_button.text = getText(R.string.get_started)
                    get_started_button.background = getDrawable(R.drawable.big_button_shape)
                }
            }
            emailValidator(email) -> {
                email_signup_editText.error = "Invalid Email"
                get_started_button.revertAnimation {
                    get_started_button.text = getText(R.string.get_started)
                    get_started_button.background = getDrawable(R.drawable.big_button_shape)
                }
            }
            password.isBlank() || password.length < 6 -> {
                password_signup_editText.error = "Password must be at least 6 digits long"
                get_started_button.revertAnimation {
                    get_started_button.text = getText(R.string.get_started)
                    get_started_button.background = getDrawable(R.drawable.big_button_shape)
                }
            }
            else -> {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.sendEmailVerification()
                            val imm: InputMethodManager =
                                this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view.windowToken, 0)
                            Snackbar.make(mSignUpPage, "Verification Email sent, please verify your email", Snackbar.LENGTH_LONG).show()
                            get_started_button.revertAnimation {
                                get_started_button.text = getText(R.string.verification)
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            get_started_button.revertAnimation {
                                get_started_button.text = getText(R.string.get_started)
                                get_started_button.background = getDrawable(R.drawable.big_button_shape)
                            }
                            val imm: InputMethodManager =
                                this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view.windowToken, 0)
                            Snackbar.make(mSignUpPage, task.exception?.localizedMessage.toString(), Snackbar.LENGTH_LONG).show()
                        }
                    }
            }

        }
}}
