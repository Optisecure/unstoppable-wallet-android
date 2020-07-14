package io.horizontalsystems.indexwallet.modules.ratelist

import androidx.annotation.StringRes
import io.horizontalsystems.indexwallet.R

sealed class TopListSortType(@StringRes val titleRes: Int) {

    object Rank: TopListSortType(R.string.RateList_Rank)
    object Winners: TopListSortType(R.string.RateList_TopWinners)
    object Losers: TopListSortType(R.string.RateList_TopLosers)

}
