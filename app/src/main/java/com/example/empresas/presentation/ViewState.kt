package com.example.empresas.presentation

class ViewState<T>(
    var data: T? = null,
    val state: State,
    val isLoading: Boolean = false,
    val error: Throwable? = null
) {
    enum class State {
        LOADING, SUCCESS, ERROR
    }

    companion object {
        fun <T> loading(isLoading: Boolean) =
            ViewState<T>(isLoading = isLoading, state = State.LOADING)

        fun <T> error(error: Throwable?) =
            ViewState<T>(state = State.ERROR, error = error)

        fun <T> success(data: T?) =
            ViewState(data = data, state = State.SUCCESS)
    }
}
