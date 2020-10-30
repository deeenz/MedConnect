package com.bits9.medconnect

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(){

    lateinit var bottomNavigation: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        if (savedInstanceState == null) {
            val fragment = questionsFragment()
            supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }
    }



    var navigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.navigation_home -> {
                        openFragment(questionsFragment.newInstance(1));
                        return true
                    }
                    R.id.group -> {
                        openFragment(group_fragment.newInstance(1))
                        return true
                    }
                    R.id.ask_question -> {
                        var intent = Intent(this@MainActivity, CustomDialog::class.java)
                        startActivity(intent)
                    }
                }
                return false
            }
        }

    fun openFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
