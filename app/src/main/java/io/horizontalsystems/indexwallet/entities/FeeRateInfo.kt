package io.horizontalsystems.indexwallet.entities

import io.horizontalsystems.indexwallet.core.FeeRatePriority

data class FeeRateInfo(val priority: FeeRatePriority, var feeRate: Long, val duration: Long?)
