package nl.hva.frontend.data.repository

import nl.hva.frontend.common.api.VocabilityAPIService
import nl.hva.frontend.domain.model.HistoryEntry
import nl.hva.frontend.domain.repository.HistoryRepository
import java.time.LocalDateTime
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val api: VocabilityAPIService
) : HistoryRepository {

    override suspend fun getAllHistoryEntries(): ArrayList<HistoryEntry> {
        return api.getAllHistoryEntries()
    }

    override suspend fun addHistoryEntry(
        language: String,
        difficultyLevel: String,
        totalAnswers: Int,
        correctAnswers: Int,
        date: LocalDateTime
    ) {
        api.addHistoryEntry(language, difficultyLevel, totalAnswers, correctAnswers, date)
    }

    override suspend fun deleteAllHistoryEntries() {
        api.deleteAllHistoryEntries()
    }

    override suspend fun deleteHistoryEntryById(id: String) {
        api.deleteHistoryEntryById(id)
    }

}