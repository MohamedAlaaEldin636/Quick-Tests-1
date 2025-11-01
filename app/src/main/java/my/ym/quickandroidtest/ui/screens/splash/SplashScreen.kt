package my.ym.quickandroidtest.ui.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import my.ym.quickandroidtest.R
import my.ym.quickandroidtest.ui.theme.QuickAndroidTestTheme

@Composable
fun SplashScreen(
	goToNextScreen: () -> Unit,

	event: SharedFlow<SplashEvent>,
) {
	val lifecycleOwner = LocalLifecycleOwner.current
	LaunchedEffect(key1 = lifecycleOwner) {
		lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
			event.collectLatest { event ->
				when (event) {
					SplashEvent.GoToNextScreen -> goToNextScreen()
				}
			}
		}
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(all = 16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(
			space = 16.dp,
			alignment = Alignment.CenterVertically,
		)
	) {
		Text(
			text = stringResource(id = R.string.app_name),
			style = MaterialTheme.typography.titleLarge,
		)

		LinearProgressIndicator()
	}
}

@Preview(showBackground = true)
@Composable
private fun PreviewSplashScreen() {
	QuickAndroidTestTheme {
		SplashScreen(
			goToNextScreen = {},

			event = MutableSharedFlow(),
		)
	}
}
