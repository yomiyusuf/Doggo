package com.yomi.doggo.ui.model

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
data class BreedQuestion(
    val imageUrl: String,
    val options: ArrayList<Option>
)

data class Option(
    val name: String,
    val isCorrect: Boolean = false,
    var isSelected: Boolean = false
) {
    constructor(breed: BreedDetail) : this(breed.name)
}