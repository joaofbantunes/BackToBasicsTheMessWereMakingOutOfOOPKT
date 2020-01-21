package backtobasicsoop.shared

sealed class Result<TValue> {
    class Success<TValue>(val value: TValue): Result<TValue>()
    sealed class Error<TValue>(val message: String) : Result<TValue>() {
        class NotFound<TValue>(message: String) : Error<TValue>(message)
        class Invalid<TValue>(message: String) : Error<TValue>(message)
        //class Unexpected<TValue>(message: String) : Error<TValue>(message)

        fun <TOtherValue>asErrorOf() : Error<TOtherValue> = when(this){
            is NotFound -> NotFound(message)
            is Invalid -> Invalid(message)
        }
    }
}