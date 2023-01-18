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
import nl.hva.frontend.databinding.FragmentAddDifficultyLevelBinding
import nl.hva.frontend.domain.model.Language
import nl.hva.frontend.ui.vm.SettingsViewModel

@AndroidEntryPoint
class AddDifficultyLevelFragment : Fragment() {

    private var _binding: FragmentAddDifficultyLevelBinding? = null
    private val binding get() = _binding!!

    private val settingsViewModel: SettingsViewModel by activityViewModels()

    private lateinit var selectedLanguage: Language

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddDifficultyLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedLanguage = settingsViewModel.selectedLanguage!!
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        // set page title
        binding.header.tvTitle.text = resources.getString(R.string.add_difficulty_level)

        // set navigation(s)
        binding.header.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_addDifficultyLevelFragment_to_settingsAdvancedFragment)
        }

        // set selected language
        binding.tvSelectedLanguage.text = selectedLanguage.name

        // initialize add difficulty level button
        binding.fabAddDifficultyLevel.setOnClickListener {
            addDifficultyLevel()
        }
    }

    private fun addDifficultyLevel() {
        val difficultyLevelName = binding.tietDifficultyLevelName.text.toString()
        if (difficultyLevelName.isNotBlank()) {
            settingsViewModel.addDifficultyLevel(difficultyLevelName, selectedLanguage.id)

            findNavController().navigate(R.id.action_addDifficultyLevelFragment_to_settingsAdvancedFragment)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_in_difficulty_level_name_toast),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}