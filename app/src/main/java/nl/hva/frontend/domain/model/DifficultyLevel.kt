package nl.hva.frontend.domain.model

data class DifficultyLevel(
    val id: String,
    val name: String,
    val languageId: String
) {
    // needed to show correct string in the dropdown menu
    override fun toString(): String = this.name
}