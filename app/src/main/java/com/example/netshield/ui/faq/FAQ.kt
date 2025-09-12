package com.example.netshield.ui.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.netshield.R
import com.example.netshield.databinding.FragmentFaqBinding
import androidx.core.view.isGone

class FAQ: Fragment(){

    private var _binding: FragmentFaqBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFaqBinding.inflate(inflater, container, false)
        val root: View = binding.root

        displayQuestions(R.id.faq_card1, R.id.a1)
        displayQuestions(R.id.faq_card2, R.id.a2)

        return root
    }

    private fun displayQuestions(@IdRes idCard : Int,
                                 @IdRes idAns: Int) {
        val card = binding.root.findViewById<CardView>(idCard)
        val answer = binding.root.findViewById<TextView>(idAns)

        card.setOnClickListener {
            if (answer.isGone) {
                answer.visibility = View.VISIBLE
            } else {
                answer.visibility = View.GONE
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}