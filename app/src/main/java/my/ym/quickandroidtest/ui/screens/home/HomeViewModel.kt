package my.ym.quickandroidtest.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import my.ym.quickandroidtest.domain.models.UIResult
import my.ym.quickandroidtest.domain.repos.RepoMovies
import my.ym.quickandroidtest.domain.utils.toUIResult
import javax.inject.Inject

// sadsa
@HiltViewModel
class HomeViewModel @Inject constructor(
	private val repoMovies: RepoMovies,
) : ViewModel() {

	private val _state = MutableStateFlow(value = HomeState())
	val state = _state.asStateFlow()

	private val _event = MutableSharedFlow<HomeEvent>(
		extraBufferCapacity = 1,
		onBufferOverflow = BufferOverflow.DROP_LATEST,
	)
	val event = _event.asSharedFlow()

	init {
		refreshData()
	}

	fun refreshData() {
		// Show Loading inshallah
		_state.update { state ->
			val containsData = (state.result as? UIResult.Success)
				?.data.orEmpty().isNotEmpty()

			state.copy(
				isRefreshing = true,

				result = if (containsData) state.result else UIResult.Loading(),
			)
		}

		viewModelScope.launch {
			val result = repoMovies.getNowPlayingMovies()

			_state.update {
				it.copy(
					isRefreshing = false,

					result = result.toUIResult()
				)
			}
		}
	}

	fun onMovieItemClick(id: Int) {
		_event.tryEmit(
			value = HomeEvent.GoToMovieDetailsScreen(id = id)
		)
	}

}
