package backtobasicsoop.domain.use_cases

import mu.KotlinLogging


class RequestHandlerLoggingDecorator<TIn : Any, TOut : Any>(
    private val next: RequestHandler<TIn, TOut>)
    : RequestHandler<TIn, TOut> {

    private val logger = KotlinLogging.logger {}

    override fun handle(input: TIn): TOut {
        try {
            logger.debug { "Handling ${input::class.simpleName}" }
            return next.handle(input);
        } finally {
            logger.debug { "Done handling ${input::class.simpleName}" }
        }
    }
}