package nl.hva.frontend.domain.model

data class Language(
    val id: String, val name: String
) {
    override fun toString(): String = this.name
}