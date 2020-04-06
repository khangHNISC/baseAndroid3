package com.example.basemvvm3.classes.data

sealed class ResultData

class Success<out T>(val data: T? = null): ResultData()
class Loading<out T>(val data: T? = null, val progress: Float = 0f): ResultData()
class Error(val error: Throwable? = null): ResultData()