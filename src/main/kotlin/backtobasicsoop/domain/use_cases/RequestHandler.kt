package backtobasicsoop.domain.use_cases

interface RequestHandler<TIn : Any, TOut : Any> {
    fun handle(input: TIn): TOut
}