package io.horizontalsystems.indexwallet.entities

import io.horizontalsystems.indexwallet.entities.AccountType.Derivation

class DerivationSetting(val coinType: CoinType,
                        var derivation: Derivation)
