package nl.hva.frontend.domain.use_case.get_difficulty_levels_by_language_id

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nl.hva.frontend.common.response.Resource
import nl.hva.frontend.domain.model.DifficultyLevel
import nl.hva.frontend.domain.repository.SettingsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDifficultyLevelsByLanguageIdUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(id: String): Flow<Resource<List<DifficultyLevel>>> = flow {
        try {
            emit(Resource.Loading())
            val difficultyLevels: List<DifficultyLevel> = repository.getDifficultyLevelsByLanguageId(id)
            emit(Resource.Success(difficultyLevels))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}