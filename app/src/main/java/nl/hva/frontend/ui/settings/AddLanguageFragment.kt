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
import nl.hva.frontend.databinding.FragmentAddLanguageBinding
import nl.hva.frontend.ui.vm.SettingsViewModel

@AndroidEntryPoint
class AddLanguageFragment : Fragment() {

    private var _binding: FragmentAddLanguageBinding? = null
    private val binding get() = _binding!!

    private val settingsViewModel: SettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddLanguageBinding.inflate(inflater, container, false)
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
        binding.header.tvTitle.text = resources.getString(R.string.add_language)

        // set navigation(s)
        binding.header.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_addLanguageFragment_to_settingsFragment)
        }

        // initialize add language button
        binding.fabAddLanguage.setOnClickListener {
            addLanguage()
        }
    }

    private fun addLanguage() {
        val languageName = binding.tietLanguageName.text.toString()
        if (languageName.isNotBlank()) {
            settingsViewModel.addLanguage(languageName)

            findNavController().navigate(R.id.action_addLanguageFragment_to_settingsFragment)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_in_difficulty_level_name_toast),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}