package my.ym.quickandroidtest.ui.screens.splash

sealed interface SplashEvent {

	data object GoToNextScreen : SplashEvent

}
