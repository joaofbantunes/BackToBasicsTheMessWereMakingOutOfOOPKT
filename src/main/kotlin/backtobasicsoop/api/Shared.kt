package backtobasicsoop.api

import backtobasicsoop.domain.Error
import backtobasicsoop.shared.Either
import backtobasicsoop.shared.fold
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

suspend fun <TResult : Any> ApplicationCall.respondWithResult(result: Either<Error, TResult>) =
    result.fold(
        { error ->
            when (error) {
                is Error.Invalid -> this.respond(HttpStatusCode.BadRequest, ErrorResponse(error.message))
                is Error.NotFound -> this.respond(HttpStatusCode.NotFound, ErrorResponse(error.message))
            }.exhaust
        },
        { result ->
            if (result is Unit)
                this.respond(HttpStatusCode.NoContent)
            else
                this.respond(HttpStatusCode.OK, result)
        }
    )

data class ErrorResponse(val reason: String)

// trickery to force the above when to be exhaustive
val Any.exhaust: Unit
    get() = Unit