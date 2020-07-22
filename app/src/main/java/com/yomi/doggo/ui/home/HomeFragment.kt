package com.yomi.doggo.ui.home

import android.graphics.Color
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
import com.yomi.doggo.ui.model.BreedQuestion
import com.yomi.doggo.util.*
import kotlinx.android.synthetic.main.fragment_home.*
import nl.dionsegijn.konfetti.models.Shape
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
            handleCurrentQuestion(question)
        })

        viewModel.readyForNextQuestion.observe(viewLifecycleOwner, Observer { prepareForNextQuestion(it) })

        viewModel.showCelebration.observe(viewLifecycleOwner, Observer { bool -> if (bool) showConfetti() })

        viewModel.resetView.observe(viewLifecycleOwner, Observer { shouldReset ->
            if (shouldReset) {
                resetUI()
                confetti.stopGracefully()
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            progress_bar.show(isLoading)
            txt_guess.show(!isLoading)
        })

        viewModel.chancesLeft.observe(viewLifecycleOwner, Observer {
            handleChancesMessage(it)
        })

        viewModel.loadingError.observe(viewLifecycleOwner, Observer {
            view?.findViewById<View>(R.id.error_layout)?.show(it)
        })
    }

    private fun handleCurrentQuestion(question: BreedQuestion) {
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
                    setOnClickListener { viewModel.selectOption(option) }
                }
                btnsContainer.addView(btn)
                optionButtons.add(btn)
            }
        } else {
            optionButtons.forEach { it.updateState(question.options) }
        }
    }

    private fun prepareForNextQuestion(isReady: Boolean) {
        if (isReady) {
            optionButtons.forEach { it.isEnabled = false }
            fab_next.show()
        } else {
            fab_next.hide()
        }
    }

    private fun handleChancesMessage(pair: Pair<Boolean, Int?>) {
        txt_chances.show(pair.first)
        pair.second?.run {
            val chances = resources.getQuantityString(R.plurals.chances, this, this)
            txt_chances.text = getString(R.string.chances_msg, this.toString(), chances)
        }
    }

    private fun resetUI() {
        optionButtons.clear()
        btnsContainer.removeAllViews()
        image_dog.setImageDrawable(null)
    }

    private fun showConfetti() {
        confetti.build()
            .addColors(Color.YELLOW, resources.getColor(R.color.colorPrimaryLight), resources.getColor(R.color.colorPrimary))
            .setDirection(359.0, 0.0)
            .setSpeed(9f, 18f)
            .setTimeToLive(2000L)
            .addShapes(Shape.Square, Shape.Circle)
            .setFadeOutEnabled(true)
            .streamFor(100, 5000L)
    }
}