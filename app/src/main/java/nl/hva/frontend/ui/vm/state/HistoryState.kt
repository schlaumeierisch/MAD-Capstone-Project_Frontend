package nl.hva.frontend.ui.vm.state

import nl.hva.frontend.domain.model.HistoryEntry

data class HistoryState(
    val isLoading: Boolean = false,
    val historyEntries: List<HistoryEntry> = emptyList(),
    val error: String = ""
)