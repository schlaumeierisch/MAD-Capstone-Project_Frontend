package nl.hva.frontend.domain.use_case

import nl.hva.frontend.domain.use_case.add_history_entry.AddHistoryEntryUseCase
import nl.hva.frontend.domain.use_case.delete_all_history_entries.DeleteAllHistoryEntriesUseCase
import nl.hva.frontend.domain.use_case.delete_history_entry_by_id.DeleteHistoryEntryByIdUseCase
import nl.hva.frontend.domain.use_case.get_all_history_entries.GetAllHistoryEntriesUseCase

data class HistoryUseCases(
    val getAllHistoryEntriesUseCase: GetAllHistoryEntriesUseCase,
    val addHistoryEntryUseCase: AddHistoryEntryUseCase,
    val deleteAllHistoryEntriesUseCase: DeleteAllHistoryEntriesUseCase,
    val deleteHistoryEntryByIdUseCase: DeleteHistoryEntryByIdUseCase
)