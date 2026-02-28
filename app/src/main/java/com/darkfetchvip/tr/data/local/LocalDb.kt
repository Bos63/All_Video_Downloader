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
data class DownloadEntity(@PrimaryKey val id: String, val title: String, val progress: Int, val quality: String, val state: String)

@Entity(tableName = "history")
data class HistoryEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0, val url: String, val createdAt: Long)

@Entity(tableName = "settings")
data class SettingsEntity(@PrimaryKey val key: String, val value: String)

@Entity(tableName = "queue")
data class QueueEntity(@PrimaryKey val id: String, val priority: Int, val createdAt: Long)

@Dao
interface DarkFetchDao {
    @Query("SELECT * FROM downloads") fun observeDownloads(): Flow<List<DownloadEntity>>
    @Insert suspend fun insertDownload(entity: DownloadEntity)
}

@Database(entities = [DownloadEntity::class, HistoryEntity::class, SettingsEntity::class, QueueEntity::class], version = 1)
abstract class DarkFetchDb : RoomDatabase() {
    abstract fun dao(): DarkFetchDao
}
