package my.ym.quickandroidtest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import my.ym.quickandroidtest.data.remote.repos.movies.RepoMoviesFakeImpl
import my.ym.quickandroidtest.domain.repos.RepoMovies
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
data object ModuleRepos {

	@Singleton
	@Provides
	fun provideRepoMovies(): RepoMovies {
		return RepoMoviesFakeImpl
	}

}
