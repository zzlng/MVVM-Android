package io.zzl.app.model

import io.zzl.app.model.Resource.Success

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Resource<out R> {

    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading<out T>(val data: T) : Resource<T>()
    data class Error(
            val message: String, val exception: Exception,
            val resolve: () -> Error = {
                Error(message, exception)
            }) : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Loading -> "Loading[cache=$data]"
            is Error -> "Error[message=$message\nexception=$exception]"
        }
    }
}

/**
 * `true` if [Resource] is of type [Success] & holds non-null [Success.data].
 */
val Resource<*>.succeeded
    get() = this is Success && data != null
