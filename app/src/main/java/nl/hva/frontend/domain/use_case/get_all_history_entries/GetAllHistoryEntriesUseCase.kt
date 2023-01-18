package nl.hva.frontend.domain.use_case.get_all_history_entries

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nl.hva.frontend.common.response.Resource
import nl.hva.frontend.domain.model.HistoryEntry
import nl.hva.frontend.domain.repository.HistoryRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllHistoryEntriesUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    operator fun invoke(): Flow<Resource<List<HistoryEntry>>> = flow {
        try {
            emit(Resource.Loading())
            val historyEntries: List<HistoryEntry> = repository.getAllHistoryEntries()
            emit(Resource.Success(historyEntries))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}