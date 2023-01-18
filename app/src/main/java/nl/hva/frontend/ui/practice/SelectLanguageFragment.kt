package nl.hva.frontend.ui.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nl.hva.frontend.R
import nl.hva.frontend.databinding.FragmentSelectLanguageBinding
import nl.hva.frontend.domain.model.Language
import nl.hva.frontend.ui.adapter.LanguagesAdapter
import nl.hva.frontend.ui.vm.SettingsViewModel

@AndroidEntryPoint
class SelectLanguageFragment : Fragment() {

    private var _binding: FragmentSelectLanguageBinding? = null
    private val binding get() = _binding!!

    private lateinit var languagesAdapter: LanguagesAdapter

    private val settingsViewModel: SettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectLanguageBinding.inflate(inflater, container, false)
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
        binding.header.tvTitle.text = resources.getString(R.string.select_language)

        // set navigation(s)
        binding.header.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_selectLanguageFragment_to_homeFragment)
        }

        // get data (languages) to fill the RecyclerView
        settingsViewModel.getAllLanguages()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingsViewModel.languages.collect {
                    languagesAdapter = LanguagesAdapter(it.languages) {
                            language: Language -> languageItemClicked(language)
                    }
                    initRv()
                }
            }
        }
    }

    private fun initRv() {
        binding.rvLanguages.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvLanguages.adapter = languagesAdapter
        binding.rvLanguages.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun languageItemClicked(language: Language) {
        settingsViewModel.selectedLanguage = language

        // navigate to the next screen (select difficulty level)
        findNavController().navigate(R.id.action_selectLanguageFragment_to_selectDifficultyLevelFragment)
    }
}