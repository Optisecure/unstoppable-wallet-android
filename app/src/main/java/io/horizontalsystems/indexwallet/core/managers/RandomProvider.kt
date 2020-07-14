package io.horizontalsystems.indexwallet.core.managers

import io.horizontalsystems.indexwallet.core.IRandomProvider
import java.util.*

class RandomProvider : IRandomProvider {

    override fun getRandomIndexes(count: Int, maxIndex: Int): List<Int> {
        val indexes = mutableListOf<Int>()

        val random = Random()

        while (indexes.size < count) {
            val index = random.nextInt(maxIndex) + 1

            if (!indexes.contains(index)) {
                indexes.add(index)
            }
        }

        return indexes
    }

}
