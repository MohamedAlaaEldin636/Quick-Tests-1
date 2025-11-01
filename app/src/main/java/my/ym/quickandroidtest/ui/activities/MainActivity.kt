package my.ym.quickandroidtest.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import my.ym.quickandroidtest.ui.screens.home.composableOfHome
import my.ym.quickandroidtest.ui.screens.home.navigateToHome
import my.ym.quickandroidtest.ui.screens.splash.SplashDestination
import my.ym.quickandroidtest.ui.screens.splash.composableOfSplash
import my.ym.quickandroidtest.ui.theme.QuickAndroidTestTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		enableEdgeToEdge()

		setContent {
			QuickAndroidTestTheme {
				Scaffold(
					modifier = Modifier.fillMaxSize()
				) { innerPadding ->
					val navHostController = rememberNavController()
					NavHost(
						modifier = Modifier
							.fillMaxSize()
							.padding(paddingValues = innerPadding),

						navController = navHostController,

						startDestination = SplashDestination
					) {
						composableOfSplash(
							goToNextScreen = {
								navHostController.navigateToHome()
							}
						)

						composableOfHome(
							goToMovieDetailsScreen = {
								// todo
								Toast.makeText(
									this@MainActivity,
									"Triggered $it Successfully El7mdullah",
									Toast.LENGTH_SHORT
								).show()
							}
						)
					}
				}
			}
		}
	}

}