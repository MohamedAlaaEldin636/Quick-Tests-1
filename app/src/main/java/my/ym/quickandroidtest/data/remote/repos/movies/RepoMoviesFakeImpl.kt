package my.ym.quickandroidtest.data.remote.repos.movies

import kotlinx.coroutines.delay
import my.ym.quickandroidtest.domain.models.DomainMovieItem
import my.ym.quickandroidtest.domain.repos.RepoMovies
import kotlin.time.Duration.Companion.seconds

data object RepoMoviesFakeImpl : RepoMovies {

	private enum class ResultStatus {
		Error, Empty, Filled;

		fun next(): ResultStatus {
			return when (this) {
				Error -> Empty
				Empty -> Filled
				Filled -> Error
			}
		}
	}

	private var resultStatus = ResultStatus.Error

	override suspend fun getNowPlayingMovies(): Result<List<DomainMovieItem>> {
		delay(2.seconds)

		val result = when (resultStatus) {
			ResultStatus.Error -> Result.failure(
				exception = RuntimeException("Unexpected Error, Please Try Again")
			)
			ResultStatus.Empty -> {
				Result.success(value = emptyList())
			}
			ResultStatus.Filled -> {
				val data = List(size = 6) {
					val id = it.inc()

					DomainMovieItem(
						id = id,
						title = "# $id",
						imageBackdropUrl = "",
						voteAverage = 2.5 + id
					)
				}

				Result.success(value = data)
			}
		}

		resultStatus = resultStatus.next()

		return result
	}

}
