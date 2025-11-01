package my.ym.quickandroidtest.ui.screens.splash

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.composableOfSplash(
	goToNextScreen: () -> Unit,
) {
	composable<SplashDestination> {
		val viewModel = hiltViewModel<SplashViewModel>()

		SplashScreen(
			goToNextScreen = goToNextScreen,

			event = viewModel.event,
		)
	}
}
