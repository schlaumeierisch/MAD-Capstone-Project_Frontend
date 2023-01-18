package nl.hva.frontend.ui.practice

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nl.hva.frontend.R
import nl.hva.frontend.databinding.FragmentPracticeBinding
import nl.hva.frontend.domain.model.DifficultyLevel
import nl.hva.frontend.domain.model.Flashcard
import nl.hva.frontend.domain.model.Language
import nl.hva.frontend.ui.vm.HistoryViewModel
import nl.hva.frontend.ui.vm.SettingsViewModel
import java.time.LocalDateTime

@AndroidEntryPoint
class PracticeFragment : Fragment() {

    private var _binding: FragmentPracticeBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectedLanguage: Language
    private lateinit var selectedDifficultyLevel: DifficultyLevel
    private lateinit var flashcards: List<Flashcard>

    private val settingsViewModel: SettingsViewModel by activityViewModels()
    private val historyViewModel: HistoryViewModel by activityViewModels()

    private var flashcardCounter: Int = -1
    private var correctAnswers: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPracticeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedLanguage = settingsViewModel.selectedLanguage!!
        selectedDifficultyLevel = settingsViewModel.selectedDifficultyLevel!!

        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        // set page title
        binding.header.tvTitle.text = resources.getString(
            R.string.practice_title, selectedLanguage.name, selectedDifficultyLevel.name
        )

        // disable back button
        requireActivity().onBackPressedDispatcher.addCallback(this) {}

        // button functionality
        binding.btnCancel.setOnClickListener {
            openConfirmationDialog()
        }
        binding.btnCheckFlashcard.setOnClickListener {
            checkFlashcard()
        }
        binding.btnNextFlashcard.setOnClickListener {
            initFlashcard()
        }

        // get data (languages) to fill the RecyclerView
        settingsViewModel.getFlashcardsByDifficultyLevelId(selectedDifficultyLevel.id)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingsViewModel.flashcards.collect {
                    flashcards = it.flashcards

                    if (flashcards.isNotEmpty()) {
                        initFlashcard()
                    }
                }
            }
        }
    }

    private fun initFlashcard() {
        // set current counter
        flashcardCounter++

        if (flashcardCounter == flashcards.size - 1) {
            // change next button to finish at last flashcard
            binding.btnNextFlashcard.text = resources.getString(R.string.finish_button)
        } else if (flashcardCounter == flashcards.size) {
            // save data in view model (for the next summary fragment)
            historyViewModel.language = selectedLanguage.name
            historyViewModel.difficultyLevel = selectedDifficultyLevel.name
            historyViewModel.totalAnswers = flashcards.size
            historyViewModel.correctAnswers = correctAnswers
            historyViewModel.date = LocalDateTime.now()

            // add new history entry
            historyViewModel.addHistoryEntry(
                selectedLanguage.name,
                selectedDifficultyLevel.name,
                flashcards.size,
                correctAnswers,
                LocalDateTime.now()
            )

            // navigate to summary if all flashcards are practiced
            findNavController().navigate(R.id.action_practiceFragment_to_practiceSummaryFragment)
            return
        }

        binding.tvFlashcardCounter.text = resources.getString(
            R.string.flashcard_counter, flashcardCounter + 1, flashcards.size
        )

        val currentFlashcard = flashcards[flashcardCounter]

        // set front word, clear back input & make card view invisible
        binding.tvFrontWord.text = currentFlashcard.front
        binding.tietBackWord.setText("")
        binding.tietBackWord.isEnabled = true
        binding.cvSolution.isVisible = false

        // change button visibility
        binding.btnNextFlashcard.visibility = View.INVISIBLE
        binding.btnCheckFlashcard.visibility = View.VISIBLE
    }

    private fun checkFlashcard() {
        if (binding.tietBackWord.text.toString().isBlank()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_in_back_word_phrase_toast),
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        // disable input field
        binding.tietBackWord.isEnabled = false

        if (binding.tietBackWord.text.toString()
                .uppercase() == flashcards[flashcardCounter].back.uppercase()
        ) {
            // correct
            binding.cvSolution.setCardBackgroundColor(resources.getColor(R.color.light_green))
            binding.tvSolutionTitle.text = resources.getString(R.string.correct_translation_title)
            binding.tvSolutionMessage.text =
                resources.getString(R.string.correct_translation_message)

            correctAnswers++
        } else {
            // incorrect
            binding.cvSolution.setCardBackgroundColor(resources.getColor(R.color.light_red))
            binding.tvSolutionTitle.text = resources.getString(R.string.incorrect_translation_title)
            binding.tvSolutionMessage.text = resources.getString(
                R.string.incorrect_translation_message, flashcards[flashcardCounter].back
            )
        }

        binding.cvSolution.isVisible = true

        // change button visibility
        binding.btnNextFlashcard.visibility = View.VISIBLE
        binding.btnCheckFlashcard.visibility = View.INVISIBLE
    }

    private fun openConfirmationDialog() {
        val dialog = Dialog(requireContext()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.confirmation_dialog)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        // get cancel & confirm buttons
        val dialogCancelDeleteBtn = dialog.findViewById<MaterialButton>(R.id.btnDialogCancel)
        val dialogConfirmButton = dialog.findViewById<MaterialButton>(R.id.btnDialogConfirm)
        dialogConfirmButton.text = getString(R.string.confirmation_dialog_leave_button)

        // cancel button functionality
        dialogCancelDeleteBtn.setOnClickListener {
            dialog.dismiss()
        }

        // get dialog title & dialog message text views
        val dialogTitle = dialog.findViewById<TextView>(R.id.tvTitleOfDialog)
        val dialogMessage = dialog.findViewById<TextView>(R.id.tvMessageOfDialog)

        // set dialog title
        dialogTitle.text = getString(R.string.confirmation_dialog_title_leave_session)

        // set dialog message
        dialogMessage.text = getString(R.string.confirmation_dialog_message_leave_session)

        // confirm button functionality
        dialogConfirmButton.setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.action_practiceFragment_to_homeFragment)
        }

        dialog.show()
    }
}