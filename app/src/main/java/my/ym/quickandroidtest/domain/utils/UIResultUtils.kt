package my.ym.quickandroidtest.domain.utils

import my.ym.quickandroidtest.domain.models.UIResult

fun <T> Result<T>?.toUIResult(): UIResult<T> {
	val data = this?.getOrNull()
	return when {
		this == null -> UIResult.Loading()
		data != null -> UIResult.Success(data = data)
		else -> UIResult.Failure(throwable = exceptionOrNull())
	}
}
