package com.example.data.repository

import com.example.data.local.SessionDao
import com.example.data.model.ChairSession
import kotlinx.coroutines.flow.Flow

class SessionRepository(private val sessionDao: SessionDao) {

    val allSessions: Flow<List<ChairSession>> = sessionDao.getAllSessions()
    val latestSession: Flow<ChairSession?> = sessionDao.getLatestSession()

    suspend fun getSessionById(id: Long): ChairSession? {
        return sessionDao.getSessionById(id)
    }

    suspend fun createSession(session: ChairSession): Long {
        return sessionDao.insertSession(session)
    }

    suspend fun updateSession(session: ChairSession) {
        sessionDao.updateSession(session)
    }

    suspend fun deleteSession(id: Long) {
        sessionDao.deleteSession(id)
    }

    suspend fun getSessionCount(): Int {
        return sessionDao.getSessionCount()
    }
}
