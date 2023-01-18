package nl.hva.frontend.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.hva.frontend.R
import nl.hva.frontend.databinding.FragmentAddFlashcardBinding
import nl.hva.frontend.ui.vm.SettingsViewModel

@AndroidEntryPoint
class AddFlashcardFragment : Fragment() {

    private var _binding: FragmentAddFlashcardBinding? = null
    private val binding get() = _binding!!

    private val settingsViewModel: SettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFlashcardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        // set page title
        binding.header.tvTitle.text = resources.getString(R.string.add_flashcard)

        // set navigation(s)
        binding.header.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_addFlashcardFragment_to_settingsAdvancedFragment)
        }

        // set selected language & difficulty level
        binding.tvSelectedLanguage.text = settingsViewModel.selectedLanguage!!.name
        binding.tvSelectedDifficulty.text = settingsViewModel.selectedDifficultyLevel!!.name

        // initialize add flashcard button
        binding.fabAddFlashcard.setOnClickListener {
            addFlashcard()
        }
    }

    private fun addFlashcard() {
        val front = binding.tietFrontWord.text.toString()
        val back = binding.tietBackWord.text.toString()

        if (front.isNotBlank() && back.isNotBlank()) {
            settingsViewModel.addFlashcard(
                front, back, settingsViewModel.selectedDifficultyLevel!!.id
            )

            resetInputFields()
            Toast.makeText(
                requireContext(), getString(R.string.add_flashcard_success_toast), Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_in_front_and_back_toast),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun resetInputFields() {
        binding.tietFrontWord.setText("")
        binding.tietBackWord.setText("")
    }
}