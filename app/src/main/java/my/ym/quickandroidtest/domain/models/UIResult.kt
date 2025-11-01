package my.ym.quickandroidtest.domain.models

sealed class UIResult<T> {

	class Loading<T> : UIResult<T>()

	data class Failure<T>(val throwable: Throwable?) : UIResult<T>()

	data class Success<T>(val data: T) : UIResult<T>()

}
