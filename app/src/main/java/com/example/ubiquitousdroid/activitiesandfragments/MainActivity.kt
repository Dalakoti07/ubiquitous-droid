package com.example.ubiquitousdroid.activitiesandfragments

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.ubiquitousdroid.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationBar :BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationBar=findViewById(R.id.bottom_navigation)
        bottomNavigationBar.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        bottomNavigationBar.selectedItemId=R.id.option_images
    }

    fun openFragment(fragment: Fragment?) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragment?.let { transaction.replace(R.id.container, it) }
        transaction.addToBackStack(null)
        transaction.commit()
    }

    val navigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.option_images -> {
                        openFragment(imgurfragment())
                        return true
                    }
                    R.id.option_uploaded -> {
                        openFragment(uploadFragment())
                        return true
                    }
                    R.id.option_Contacts -> {
                        openFragment(contactFragment())
                        return true
                    }
                }
                return false
            }
        }
}
