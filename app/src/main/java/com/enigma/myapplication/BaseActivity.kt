package com.enigma.myapplication

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

abstract class BaseActivity : AppCompatActivity() {

    private val scope = MainScope()

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        scope.launch { block() }
    }
}
