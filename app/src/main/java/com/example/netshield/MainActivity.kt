package com.example.netshield

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.netshield.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)

        val lang = sharedPreferences.getString("language", "ro") ?: "ro"
        setLocale(lang)

        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.navView
        val navController = try {
            findNavController(R.id.nav_host_fragment_activity_main)
        } catch (e: Exception) {
            Log.e("Navigation", "Failed to find NavController", e)
            null
        }

        navController?.let {
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_home,
//                    R.id.navigation_dashboard,
                    R.id.navigation_settings
                )
            )

            navView.setupWithNavController(it)

            // Hide main navigation bar in CameraFragment
            it.addOnDestinationChangedListener { _, destination, _ ->
                navView.visibility = if (destination.id == R.id.navigation_faq) View.GONE else View.VISIBLE
            }
        }
    }
    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}