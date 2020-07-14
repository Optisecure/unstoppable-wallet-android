package io.horizontalsystems.indexwallet.entities

import io.horizontalsystems.core.entities.Currency
import java.math.BigDecimal

data class CurrencyValue(val currency: Currency, val value: BigDecimal)
