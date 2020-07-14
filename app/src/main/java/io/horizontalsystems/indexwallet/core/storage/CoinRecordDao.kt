package io.horizontalsystems.indexwallet.core.storage

import androidx.room.*
import io.horizontalsystems.indexwallet.entities.CoinRecord

@Dao
interface CoinRecordDao {

    @Query("SELECT * FROM CoinRecord")
    fun coinRecords(): List<CoinRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coinRecord: CoinRecord)

    @Query("DELETE FROM CoinRecord")
    fun deleteAll()

    @Query("DELETE FROM CoinRecord WHERE coinId = :coinId")
    fun delete(coinId: String)

}
