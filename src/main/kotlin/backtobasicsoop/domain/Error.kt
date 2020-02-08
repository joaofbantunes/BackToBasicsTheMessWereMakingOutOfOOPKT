package backtobasicsoop.domain

sealed class Error(val message: String) {
    class NotFound(message: String) : Error(message)
    class Invalid(message: String) : Error(message)
    //class Unexpected<TValue>(message: String) : Error(message)
}