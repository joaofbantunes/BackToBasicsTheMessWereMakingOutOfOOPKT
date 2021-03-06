package backtobasicsoop.shared

sealed class Either<out L, out R> {
    data class Left<out T>(val value: T) : Either<T, Nothing>()
    data class Right<out T>(val value: T) : Either<Nothing, T>()

    companion object {
        fun <R> right(value: R): Either<Nothing, R> =
            Right(value)

        fun <L> left(value: L): Either<L, Nothing> =
            Left(value)
    }
}

inline fun <L, R, T> Either<L, R>.fold(left: (L) -> T, right: (R) -> T): T =
    when (this) {
        is Either.Left -> left(value)
        is Either.Right -> right(value)
    }

inline fun <L, R, T> Either<L, R>.flatMap(f: (R) -> Either<L, T>): Either<L, T> =
    fold({ this as Either.Left }, f)

inline fun <L, R, T> Either<L, R>.map(f: (R) -> T): Either<L, T> =
    flatMap { Either.Right(f(it)) }

inline fun <L, R> Either<L, R>.exec(left: (L) -> Any, right: (R) -> Any): Either<L, R> {
    this.fold(left, right)
    return this
}

inline fun <L, R> Either<L, R>.exec(right: (R) -> Any): Either<L, R> {
    this.fold({ this as Either.Left }, right)
    return this
}
