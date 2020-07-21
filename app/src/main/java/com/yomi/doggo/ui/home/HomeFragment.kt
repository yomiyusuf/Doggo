package com.yomi.doggo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.airbnb.paris.extensions.style
import com.yomi.doggo.R
import com.yomi.doggo.util.capitalizeWords
import com.yomi.doggo.util.getProgressDrawable
import com.yomi.doggo.util.loadImage
import com.yomi.doggo.util.updateState
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel by viewModel<HomeViewModel>()

    lateinit var btnsContainer: LinearLayout
    private val optionButtons = mutableListOf<Button>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        registerObservers()
        if (savedInstanceState == null) viewModel.getRandomDog()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnsContainer = requireView().findViewById<LinearLayout>(R.id.btn_container)
        fab_next.setOnClickListener { viewModel.getRandomDog() }
    }

    private fun registerObservers() {
        viewModel.currentQuestion.observe(viewLifecycleOwner, Observer { question ->
            val image = requireView().findViewById<ImageView>(R.id.image_dog)
            btnsContainer = requireView().findViewById<LinearLayout>(R.id.btn_container)
            if (optionButtons.isEmpty()) {
                image.loadImage(question.imageUrl, getProgressDrawable(requireContext()))
                question.options.forEach { option ->
                    //create option button
                    val btn = Button(context).apply {
                        style(R.style.primary_button)
                        text = option.name.capitalizeWords()
                        updateState(option)
                        setOnClickListener { viewModel.onOptionSelected(option) }
                    }
                    btnsContainer.addView(btn)
                    optionButtons.add(btn)
                }
                //cacheAllOptionsButton()
            } else {
                optionButtons.forEach { it.updateState(question.options) }
            }
        })

        viewModel.readyForNextQuestion.observe(viewLifecycleOwner, Observer {
            prepareForNextQuestion(it)
        })

        viewModel.showCelebration.observe(viewLifecycleOwner, Observer { bool ->
            if (bool) {
                //show celebration
            }
        })

        viewModel.resetView.observe(viewLifecycleOwner, Observer { shouldReset ->
            if (shouldReset) {
                resetUI()
            }
        })
    }

    private fun prepareForNextQuestion(isReady: Boolean) {
        if (isReady) {
            optionButtons.forEach { it.isEnabled = false }
            fab_next.show()
        } else {
            fab_next.hide()
        }
    }

    private fun resetUI() {
        optionButtons.clear()
        btnsContainer.removeAllViews()
        image_dog.setImageDrawable(null)
    }
}