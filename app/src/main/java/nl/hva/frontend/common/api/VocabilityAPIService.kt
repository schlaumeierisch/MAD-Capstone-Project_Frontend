package nl.hva.frontend.common.api

import nl.hva.frontend.domain.model.DifficultyLevel
import nl.hva.frontend.domain.model.Flashcard
import nl.hva.frontend.domain.model.HistoryEntry
import nl.hva.frontend.domain.model.Language
import retrofit2.http.*
import java.time.LocalDateTime

interface VocabilityAPIService {

    @GET("/rest/languages/getAll")
    suspend fun getAllLanguages(): ArrayList<Language>

    @GET("/rest/languages/getById/{id}")
    suspend fun getLanguageById(@Path("id") id: String): Language

    @POST("/rest/languages/add")
    suspend fun addLanguage(@Query("name") name: String)

    @DELETE("/rest/languages/deleteById/{id}")
    suspend fun deleteLanguageById(@Path("id") id: String)

    @GET("/rest/difficultyLevels/getByLanguageId/{id}")
    suspend fun getDifficultyLevelsByLanguageId(@Path("id") id: String): ArrayList<DifficultyLevel>

    @POST("/rest/difficultyLevels/add")
    suspend fun addDifficultyLevel(
        @Query("name") name: String, @Query("languageId") languageId: String
    )

    @DELETE("/rest/difficultyLevels/deleteById/{id}")
    suspend fun deleteDifficultyLevelById(@Path("id") id: String)

    @GET("/rest/flashcards/getByDifficultyLevelId/{id}")
    suspend fun getFlashcardsByDifficultyLevelId(@Path("id") id: String): ArrayList<Flashcard>

    @POST("/rest/flashcards/add")
    suspend fun addFlashcard(
        @Query("front") front: String,
        @Query("back") back: String,
        @Query("difficultyLevelId") difficultyLevelId: String
    )

    @DELETE("/rest/flashcards/deleteById/{id}")
    suspend fun deleteFlashcardById(@Path("id") id: String)

    @GET("/rest/historyEntries/getAll")
    suspend fun getAllHistoryEntries(): ArrayList<HistoryEntry>

    @POST("/rest/historyEntries/add")
    suspend fun addHistoryEntry(
        @Query("language") language: String,
        @Query("difficultyLevel") difficultyLevel: String,
        @Query("totalAnswers") totalAnswers: Int,
        @Query("correctAnswers") correctAnswers: Int,
        @Query("date") date: LocalDateTime
    )

    @DELETE("/rest/historyEntries/deleteAll")
    suspend fun deleteAllHistoryEntries()

    @DELETE("rest/historyEntries/deleteById/{id}")
    suspend fun deleteHistoryEntryById(@Path("id") id: String)
}