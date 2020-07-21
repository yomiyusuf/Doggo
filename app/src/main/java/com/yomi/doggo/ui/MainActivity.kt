package com.yomi.doggo.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.yomi.doggo.R
import com.yomi.doggo.util.InternetUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        InternetUtil.observe(this, Observer { isConnected ->
            if (isConnected) {
                dismissError()
            } else {
                showError(getString(R.string.no_internet_msg))
            }
        })
    }

    private fun showError(errorMsg: String) {
        snackBar = Snackbar.make(coordinatorLayout, errorMsg , Snackbar.LENGTH_INDEFINITE)
        snackBar?.show()
    }

    private fun dismissError() {
        snackBar?.dismiss()
    }
}
