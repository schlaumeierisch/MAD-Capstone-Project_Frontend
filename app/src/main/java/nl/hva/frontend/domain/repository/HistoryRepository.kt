package nl.hva.frontend.domain.repository

import nl.hva.frontend.domain.model.HistoryEntry
import java.time.LocalDateTime

interface HistoryRepository {

    suspend fun getAllHistoryEntries(): ArrayList<HistoryEntry>

    suspend fun addHistoryEntry(
        language: String,
        difficultyLevel: String,
        totalAnswers: Int,
        correctAnswers: Int,
        date: LocalDateTime
    )

    suspend fun deleteAllHistoryEntries()

    suspend fun deleteHistoryEntryById(id: String)

}