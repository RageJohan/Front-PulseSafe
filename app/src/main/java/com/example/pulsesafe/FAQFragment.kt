package com.example.pulsesafe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class FAQFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFAQItem(
            view.findViewById(R.id.faqItem1),
            view.findViewById(R.id.faqAnswer1),
            view.findViewById(R.id.faqArrow1)
        )
        // Setup other FAQ items similarly
    }

    private fun setupFAQItem(item: LinearLayout, answer: TextView, arrow: ImageView) {
        item.setOnClickListener {
            if (answer.visibility == View.VISIBLE) {
                answer.visibility = View.GONE
                arrow.rotation = 0f
            } else {
                answer.visibility = View.VISIBLE
                arrow.rotation = 180f
            }
        }
    }
}