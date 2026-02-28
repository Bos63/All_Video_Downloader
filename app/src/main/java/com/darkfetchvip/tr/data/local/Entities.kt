package com.darkfetchvip.tr.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "downloads")
data class DownloadEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val sourceUrl: String,
    val progress: Int,
    val status: String,
    val priority: String
)

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val url: String,
    val createdAt: Long
)

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val key: String,
    val value: String
)

@Dao
interface DarkFetchDao {
    @Query("SELECT * FROM downloads ORDER BY id DESC")
    fun observeDownloads(): Flow<List<DownloadEntity>>

    @Insert
    suspend fun insertDownload(downloadEntity: DownloadEntity)
}

@Database(entities = [DownloadEntity::class, HistoryEntity::class, SettingsEntity::class], version = 1)
abstract class DarkFetchDatabase : RoomDatabase() {
    abstract fun dao(): DarkFetchDao
}
