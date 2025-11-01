package my.ym.quickandroidtest.ui.screens.home

import my.ym.quickandroidtest.domain.models.DomainMovieItem
import my.ym.quickandroidtest.domain.models.UIResult

data class HomeState(
	val isRefreshing: Boolean = true,
	val result: UIResult<List<DomainMovieItem>> = UIResult.Loading(),
)
