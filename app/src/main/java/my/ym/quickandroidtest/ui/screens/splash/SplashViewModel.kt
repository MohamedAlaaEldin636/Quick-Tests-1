package my.ym.quickandroidtest.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

	// One Shot Event
	private val _event = MutableSharedFlow<SplashEvent>()
	val event = _event.asSharedFlow()

	init {
		viewModelScope.launch {
			delay(1.5.seconds)

			_event.emit(value = SplashEvent.GoToNextScreen)
		}
	}

}
