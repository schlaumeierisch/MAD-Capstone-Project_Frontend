package nl.hva.frontend.common.response

/**
 * You can consider a sealed class as an enum++
 * Sealed classes can also contain state - making it very useful for different network response
 * @author Pim Meijer
 */
sealed class Resource<T>(
    val data: T? = null, val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}