package nl.hva.frontend.domain.use_case.get_flashcards_by_difficulty_level_id

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nl.hva.frontend.common.response.Resource
import nl.hva.frontend.domain.model.Flashcard
import nl.hva.frontend.domain.repository.SettingsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFlashcardsByDifficultyLevelIdUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(difficultyLevelId: String): Flow<Resource<List<Flashcard>>> = flow {
        try {
            emit(Resource.Loading())
            val flashcards: List<Flashcard> = repository.getFlashcardsByDifficultyLevelId(difficultyLevelId)
            emit(Resource.Success(flashcards))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}