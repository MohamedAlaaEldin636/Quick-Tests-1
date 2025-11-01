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

	sealed interface Event {
		data object GoToNextScreen : Event
	}

	// One Shot Event
	private val _events = MutableSharedFlow<Event>()
	val events = _events.asSharedFlow()

	init {
		viewModelScope.launch {
			delay(1.5.seconds)

			_events.emit(value = Event.GoToNextScreen)
		}
	}

}
