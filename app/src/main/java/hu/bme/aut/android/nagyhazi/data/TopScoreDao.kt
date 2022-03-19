package hu.bme.aut.android.nagyhazi.data


import androidx.room.*

@Dao
interface TopScoreDao {
    @Query("SELECT * FROM topscore")
    suspend fun getAll(): List<TopScore>

    @Insert
    suspend fun insert(topscoreItem: TopScore): Long

    @Update
    suspend fun update(topscoreItem: TopScore)

    @Delete
    suspend fun deleteItem(topscoreItem: TopScore)
}