package nl.hva.frontend.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.hva.frontend.common.api.VocabilityAPIService
import nl.hva.frontend.data.repository.HistoryRepositoryImpl
import nl.hva.frontend.domain.repository.HistoryRepository
import nl.hva.frontend.domain.use_case.HistoryUseCases
import nl.hva.frontend.domain.use_case.add_history_entry.AddHistoryEntryUseCase
import nl.hva.frontend.domain.use_case.delete_all_history_entries.DeleteAllHistoryEntriesUseCase
import nl.hva.frontend.domain.use_case.delete_history_entry_by_id.DeleteHistoryEntryByIdUseCase
import nl.hva.frontend.domain.use_case.get_all_history_entries.GetAllHistoryEntriesUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {

    @Provides
    @Singleton
    fun provideHistoryRepository(api: VocabilityAPIService): HistoryRepository {
        return HistoryRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideHistoryUseCases(repository: HistoryRepository): HistoryUseCases {
        return HistoryUseCases(
            getAllHistoryEntriesUseCase = GetAllHistoryEntriesUseCase(repository),
            addHistoryEntryUseCase = AddHistoryEntryUseCase(repository),
            deleteAllHistoryEntriesUseCase = DeleteAllHistoryEntriesUseCase(repository),
            deleteHistoryEntryByIdUseCase = DeleteHistoryEntryByIdUseCase(repository)
        )
    }

}