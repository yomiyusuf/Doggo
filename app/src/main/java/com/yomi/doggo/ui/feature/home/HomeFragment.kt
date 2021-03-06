package com.yomi.doggo.ui.feature.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.airbnb.paris.extensions.style
import com.yomi.doggo.R
import com.yomi.doggo.ui.common.ProgressFragment
import com.yomi.doggo.ui.model.BreedQuestion
import com.yomi.doggo.util.*
import kotlinx.android.synthetic.main.fragment_home.*
import nl.dionsegijn.konfetti.models.Shape
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : ProgressFragment() {

    private val viewModel by viewModel<HomeViewModel>()

    lateinit var btnsContainer: LinearLayout
    private val optionButtons = mutableListOf<Button>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        if (savedInstanceState == null) viewModel.getRandomDog()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnsContainer = requireView().findViewById<LinearLayout>(R.id.btn_container)
        fab_next.setOnClickListener { viewModel.getRandomDog() }
    }

    override fun onStart() {
        super.onStart()
        registerObservers()
    }

    private fun registerObservers() {
        viewModel.currentQuestion.observe(viewLifecycleOwner, Observer { question -> handleCurrentQuestion(question) })

        viewModel.readyForNextQuestion.observe(viewLifecycleOwner, Observer { prepareForNextQuestion(it) })

        viewModel.showCelebration.observe(viewLifecycleOwner, Observer {  if (it) showCelebration() })

        viewModel.resetView.observe(viewLifecycleOwner, Observer { resetUI(it) })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { handleLoadingState(it) })

        viewModel.chancesLeft.observe(viewLifecycleOwner, Observer { handleChancesMessage(it) })

        viewModel.loadingError.observe(viewLifecycleOwner, Observer { handleErrorState(it)})
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

    override fun onLoadingStateChange(isLoading: Boolean) {
        txt_guess.show(!isLoading)
    }

    private fun resetUI(reset: Boolean) {
        if (reset) {
            optionButtons.clear()
            btnsContainer.removeAllViews()
            image_dog.setImageDrawable(null)
            confetti.stopGracefully()
        }
    }

    override fun onRetry() {
        viewModel.getRandomDog()
    }

    private fun showCelebration() {
        vibratePhone()
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