package com.example.android.proximo.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.android.proximo.R
import com.example.android.proximo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        // Configure the navigation
        val navHost = myNavHostFragment as NavHostFragment
        val graph = navHost.navController.navInflater.inflate(R.navigation.nav_graph)

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        val tutorialDone = sharedPref.getBoolean(getString(R.string.introTutorial), false)

        if (tutorialDone) {
            graph.startDestination = R.id.locationFragment
        } else {
            graph.startDestination = R.id.viewPagerFragment
        }

        navHost.navController.graph = graph
        NavigationUI.setupActionBarWithNavController(this, navHost.navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }
}