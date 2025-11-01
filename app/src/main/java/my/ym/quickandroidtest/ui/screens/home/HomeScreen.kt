package my.ym.quickandroidtest.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import my.ym.quickandroidtest.domain.models.UIResult
import my.ym.quickandroidtest.R
import my.ym.quickandroidtest.domain.models.DomainMovieItem

@Composable
fun HomeScreen(
	goToMovieDetailsScreen: (id: Int) -> Unit,

	refreshData: () -> Unit,
	onMovieItemClick: (id: Int) -> Unit,

	state: HomeState,

	event: SharedFlow<HomeEvent>,
) {
	val lifecycleOwner = LocalLifecycleOwner.current
	LaunchedEffect(key1 = lifecycleOwner) {
		lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
			event.collectLatest { event ->
				when (event) {
					is HomeEvent.GoToMovieDetailsScreen -> {
						goToMovieDetailsScreen(event.id)
					}
				}
			}
		}
	}

	when (state.result) {
		is UIResult.Loading -> LoadingImpl()
		is UIResult.Failure -> FailureImpl(
			refreshData = refreshData,

			throwable = state.result.throwable,
		)
		is UIResult.Success -> SuccessImpl(
			refreshData = refreshData,
			onMovieItemClick = onMovieItemClick,

			isRefreshing = state.isRefreshing,
			data = state.result.data
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessImpl(
	refreshData: () -> Unit,
	onMovieItemClick: (id: Int) -> Unit,

	isRefreshing: Boolean,
	data: List<DomainMovieItem>,
) {
	if (data.isEmpty()) {
		DataEmptyImpl(refreshData = refreshData)
	}else {
		PullToRefreshBox(
			isRefreshing = isRefreshing,
			onRefresh = refreshData,
		) {
			LazyColumn(
				modifier = Modifier.fillMaxSize(),

				contentPadding = PaddingValues(all = 16.dp),

				verticalArrangement = Arrangement.spacedBy(
					space = 16.dp,
					alignment = Alignment.CenterVertically,
				),
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				items(items = data) { item ->
					Text(
						modifier = Modifier
							.fillMaxWidth()
							.background(
								color = MaterialTheme.colorScheme.primaryContainer,
								shape = RoundedCornerShape(size = 16.dp)
							)
							.clickable(
								onClick = {
									onMovieItemClick(item.id)
								}
							)
							.clip(
								RoundedCornerShape(size = 16.dp)
							)
							.padding(all = 16.dp),
						text = item.title,
						style = MaterialTheme.typography.titleLarge,
						textAlign = TextAlign.Center,
						color = MaterialTheme.colorScheme.onPrimaryContainer
					)
				}
			}
		}
	}
}

@Composable
private fun LoadingImpl() {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		CircularProgressIndicator()
	}
}

@Composable
private fun FailureImpl(
	refreshData: () -> Unit,

	throwable: Throwable?,
) {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {
		Surface(
			color = MaterialTheme.colorScheme.errorContainer,
			shape = RoundedCornerShape(size = 16.dp)
		) {
			Column(
				modifier = Modifier.padding(all = 16.dp),
				verticalArrangement = Arrangement.spacedBy(
					space = 16.dp,
					alignment = Alignment.CenterVertically,
				),
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				Text(
					text = throwable?.message.orEmpty().ifEmpty {
						stringResource(id = R.string.error_msg_unexpected)
					},
					textAlign = TextAlign.Center,
					style = MaterialTheme.typography.titleLarge,
				)

				Button(
					onClick = refreshData
				) {
					Text(
						text = stringResource(id = R.string.try_again),
						textAlign = TextAlign.Center,
					)
				}
			}
		}
	}
}

@Composable
private fun DataEmptyImpl(
	refreshData: () -> Unit,
) {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {
		Surface(
			color = MaterialTheme.colorScheme.primaryContainer,
			shape = RoundedCornerShape(size = 16.dp)
		) {
			Column(
				modifier = Modifier.padding(all = 16.dp),
				verticalArrangement = Arrangement.spacedBy(
					space = 16.dp,
					alignment = Alignment.CenterVertically,
				),
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				Text(
					text = stringResource(id = R.string.no_data_found),
					textAlign = TextAlign.Center,
					style = MaterialTheme.typography.titleLarge,
				)

				Button(
					onClick = refreshData
				) {
					Text(
						text = stringResource(id = R.string.refresh),
						textAlign = TextAlign.Center,
					)
				}
			}
		}
	}
}
