package com.example.android.proximo.ui

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
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

        //TODO: This is not ok to do. Not a good pratice. Needs change! (no time now)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//Set Portrait

        // Configure the navigation
        val navHost = myNavHostFragment as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, navHost.navController)

        val actionBar: ActionBar? = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#9CB0F5"))
        actionBar!!.setBackgroundDrawable(colorDrawable)
        actionBar.elevation = 0.0F

        val navController = this.findNavController(R.id.myNavHostFragment)

        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }
}