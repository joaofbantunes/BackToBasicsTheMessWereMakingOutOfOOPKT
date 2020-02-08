package backtobasicsoop.api

import backtobasicsoop.shared.Result
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

suspend fun <TResult : Any> ApplicationCall.respondWithResult(result: Result<TResult>) {
    when (result) {
        is Result.Success -> if (result.value is Unit) this.respond(HttpStatusCode.NoContent) else this.respond(
            HttpStatusCode.OK,
            result.value
        )
        is Result.Error.Invalid -> this.respond(HttpStatusCode.BadRequest,
            ErrorResponse(result.message)
        )
        is Result.Error.NotFound -> this.respond(HttpStatusCode.NotFound,
            ErrorResponse(result.message)
        )
    }.exhaust
}

data class ErrorResponse(val reason: String)

// trickery to force the above when to be exhaustive
val Any.exhaust: Unit
    get() = Unit