package hu.bme.aut.android.nagyhazi.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topscore")
data class TopScore(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "score") var score: String,
)
