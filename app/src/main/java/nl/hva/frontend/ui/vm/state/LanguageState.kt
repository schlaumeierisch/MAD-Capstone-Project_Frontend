package nl.hva.frontend.ui.vm.state

import nl.hva.frontend.domain.model.Language

data class LanguageState(
    val isLoading: Boolean = false,
    val language: Language? = null,
    val languages: List<Language> = emptyList(),
    val error: String = ""
)