package my.ym.quickandroidtest.ui.screens.home

sealed interface HomeEvent {

	data class GoToMovieDetailsScreen(val id: Int) : HomeEvent

}
