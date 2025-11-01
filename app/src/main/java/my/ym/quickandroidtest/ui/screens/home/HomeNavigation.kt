package my.ym.quickandroidtest.ui.screens.home

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.composableOfHome(
	goToMovieDetailsScreen: (id: Int) -> Unit,
) {
	composable<HomeDestination> {
		val viewModel = hiltViewModel<HomeViewModel>()

		val state by viewModel.state.collectAsState()

		HomeScreen(
			goToMovieDetailsScreen = goToMovieDetailsScreen,

			refreshData = viewModel::refreshData,
			onMovieItemClick = viewModel::onMovieItemClick,

			state = state,

			event = viewModel.event
		)
	}
}

fun NavController.navigateToHome() {
	navigate(route = HomeDestination) {
		popUpTo(id = graph.id) {
			inclusive = true
		}
	}
}
