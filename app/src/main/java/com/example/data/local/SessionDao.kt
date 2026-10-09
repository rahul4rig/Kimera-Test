package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ChairSession
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Query("SELECT * FROM chair_sessions ORDER BY createdAt DESC")
    fun getAllSessions(): Flow<List<ChairSession>>

    @Query("SELECT * FROM chair_sessions WHERE id = :id LIMIT 1")
    suspend fun getSessionById(id: Long): ChairSession?

    @Query("SELECT * FROM chair_sessions ORDER BY createdAt DESC LIMIT 1")
    fun getLatestSession(): Flow<ChairSession?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: ChairSession): Long

    @Update
    suspend fun updateSession(session: ChairSession)

    @Query("DELETE FROM chair_sessions WHERE id = :id")
    suspend fun deleteSession(id: Long)

    @Query("SELECT COUNT(*) FROM chair_sessions")
    suspend fun getSessionCount(): Int
}
