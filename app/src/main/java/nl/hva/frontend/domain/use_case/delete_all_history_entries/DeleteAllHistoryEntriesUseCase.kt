package nl.hva.frontend.domain.use_case.delete_all_history_entries

import nl.hva.frontend.domain.repository.HistoryRepository
import javax.inject.Inject

class DeleteAllHistoryEntriesUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllHistoryEntries()
    }
}