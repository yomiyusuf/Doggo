package com.yomi.doggo.ui.feature.breeds

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.yomi.doggo.common.createRule
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module

@RunWith(AndroidJUnit4ClassRunner::class)
class BreedsFragmentTest {

    private val viewmodel: BreedsViewModel = mockk(relaxed = true)
    private val fragment = BreedsFragment()

    @get:Rule
    val fragmentRule = createRule(fragment, module {
        single(override = true) { viewmodel }
    })
}