package com.example.basemvvm3.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.lang.Exception

abstract class UseCase<in P, R> {

    /**
     * Async
     */
    operator fun invoke(parameters: P, result: MutableLiveData<Result<R>>) = runBlocking {
        result.postValue(Result.Loading)

        val job = async {
            execute(parameters)
        }
        try {
            job.await().let { useCaseResult ->
                result.postValue(Result.Success(useCaseResult))
            }
        }catch (e: Exception){
            Timber.d(e)
            result.postValue(Result.Error(e))
        }
    }

    //invoke(C)
    operator fun invoke(parameters: P): LiveData<Result<R>> {
        val liveCallback: MutableLiveData<Result<R>> = MutableLiveData()
        this(parameters, liveCallback)
        return liveCallback
    }

    /**
     * Sync
     */
    fun executeNow(parameters: P): Result<R> {
        return try {
            Result.Success(execute(parameters))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}