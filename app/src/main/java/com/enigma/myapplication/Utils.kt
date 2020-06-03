package com.enigma.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

fun <T> LiveData<List<T>>.getDistinct(): LiveData<List<T>> {
    val distinctLiveData = MediatorLiveData<List<T>>()
    distinctLiveData.addSource(this, object : Observer<List<T>> {
        private var initialized = false
        private var lastObj: List<T> = emptyList()
        override fun onChanged(obj: List<T>) {
            if (!initialized) {
                initialized = true
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            } else if (obj != lastObj && obj.minus(lastObj).isNotEmpty()) {
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            }
        }
    })
    return distinctLiveData
}

