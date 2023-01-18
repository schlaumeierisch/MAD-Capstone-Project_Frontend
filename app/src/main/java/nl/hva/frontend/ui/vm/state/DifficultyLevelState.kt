package nl.hva.frontend.ui.vm.state

import nl.hva.frontend.domain.model.DifficultyLevel

data class DifficultyLevelState(
    val isLoading: Boolean = false,
    val difficultyLevel: DifficultyLevel? = null,
    val difficultyLevels: List<DifficultyLevel> = emptyList(),
    val error: String = ""
)