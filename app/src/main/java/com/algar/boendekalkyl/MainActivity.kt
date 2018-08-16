package com.algar.boendekalkyl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.navigation_fragment)
        setupActionBarWithNavController(this, navController)
        setupWithNavController(bottom_navigation, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.navigation_fragment).navigateUp()
    }
}
