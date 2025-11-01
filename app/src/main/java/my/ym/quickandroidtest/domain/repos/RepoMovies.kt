package my.ym.quickandroidtest.domain.repos

import my.ym.quickandroidtest.domain.models.DomainMovieItem

interface RepoMovies {

	suspend fun getNowPlayingMovies(): Result<List<DomainMovieItem>>

}
