package com.yomi.doggo.ui.common

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Created by Yomi Joseph on 2020-07-24.
 */
open class CoroutineContextProvider {
    open val Main: CoroutineContext by lazy { Dispatchers.Main }
    open val IO: CoroutineContext by lazy {Dispatchers.IO}
}