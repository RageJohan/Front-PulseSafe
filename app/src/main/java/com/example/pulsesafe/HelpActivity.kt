package com.example.pulsesafe

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        // Configurar la barra de estado
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        // Configurar botón de retroceso
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        // Configurar ViewPager y TabLayout
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        viewPager.adapter = HelpPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Preguntas Frecuentes"
                1 -> "Contacta Con Nosotros"
                else -> ""
            }
        }.attach()
    }

    class HelpPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> FAQFragment()
                1 -> ContactFragment()
                else -> throw IllegalArgumentException("Invalid position $position")
            }
        }
    }}