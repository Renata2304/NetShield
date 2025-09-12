package com.example.netshield.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.netshield.R
import com.example.netshield.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedPreferences = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)

        val savedLang = sharedPreferences.getString("language", "en") ?: "en"
        applyLocale(savedLang)

        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        binding.theme.isChecked = isDarkMode

        binding.theme.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPreferences.edit()
            editor.putBoolean("dark_mode", isChecked)
            editor.apply()

            val themeMode = if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(themeMode)

            requireActivity().recreate()
        }

        binding.language.setOnClickListener { view ->
            val popupMenu = PopupMenu(requireContext(), view)
            val languages = listOf("Română", "English")
            languages.forEach { popupMenu.menu.add(it) }

            popupMenu.menu.setGroupCheckable(0, true, true)
            for (i in 0 until popupMenu.menu.size()) {
                if (popupMenu.menu.getItem(i).title.toString() == savedLangDisplay(savedLang)) {
                    popupMenu.menu.getItem(i).isChecked = true
                }
            }

            popupMenu.setOnMenuItemClickListener { item ->
                val selectedLangCode = if (item.title == "Română") "ro" else "en"
                sharedPreferences.edit().putString("language", selectedLangCode).apply()
                applyLocale(selectedLangCode)
                requireActivity().recreate()
                true
            }

            popupMenu.show()
        }

        binding.faq.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_settings_to_navigation_faq)
        }

        return root
    }

    private fun savedLangDisplay(code: String): String {
        return if (code == "ro") "Română" else "English"
    }

    private fun applyLocale(code: String) {
        val locale = Locale(code)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}