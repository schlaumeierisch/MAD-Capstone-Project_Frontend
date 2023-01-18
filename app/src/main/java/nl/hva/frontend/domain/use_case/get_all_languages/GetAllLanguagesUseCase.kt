package nl.hva.frontend.domain.use_case.get_all_languages

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nl.hva.frontend.common.response.Resource
import nl.hva.frontend.domain.model.Language
import nl.hva.frontend.domain.repository.SettingsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllLanguagesUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<Resource<List<Language>>> = flow {
        try {
            emit(Resource.Loading())
            val languages: List<Language> = repository.getAllLanguages()
            emit(Resource.Success(languages))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}