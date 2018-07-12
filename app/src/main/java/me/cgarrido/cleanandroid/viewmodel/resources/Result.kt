package me.cgarrido.cleanandroid.viewmodel.resources

sealed class Result<out T : Any> {

    data class Success<T : Any>(val data: T) : Result<T>()
    data class Error<T : Any>(val throwable: Throwable) : Result<T>()
    data class Loading<T : Any>(val progress: Int = 0) : Result<T>()


}