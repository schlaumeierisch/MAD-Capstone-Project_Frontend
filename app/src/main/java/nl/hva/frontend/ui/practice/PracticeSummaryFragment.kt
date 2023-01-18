package nl.hva.frontend.ui.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.hva.frontend.R
import nl.hva.frontend.databinding.FragmentPracticeSummaryBinding
import nl.hva.frontend.ui.vm.HistoryViewModel
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class PracticeSummaryFragment : Fragment() {

    private var _binding: FragmentPracticeSummaryBinding? = null
    private val binding get() = _binding!!

    private val historyViewModel: HistoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPracticeSummaryBinding.inflate(inflater, container, false)
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
        binding.header.tvTitle.text = resources.getString(R.string.practice_summary)

        // set navigation(s)
        binding.fabHome.setOnClickListener {
            findNavController().navigate(R.id.action_practiceSummaryFragment_to_homeFragment)
        }

        // disable back button
        requireActivity().onBackPressedDispatcher.addCallback(this) {}

        // define custom format
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")

        // calculate percentage of correct answers
        val totalAnswers: String = historyViewModel.totalAnswers.toString()
        val correctAnswers: String = historyViewModel.correctAnswers.toString()
        val percent: Int = (correctAnswers.toDouble() / totalAnswers.toDouble() * 100).toInt()

        // set data
        binding.tvLanguage.text = historyViewModel.language
        binding.tvDifficultyLevel.text = historyViewModel.difficultyLevel
        binding.tvDateAndTime.text = historyViewModel.date.format(formatter)
        binding.tvCorrectAnswers.text = getString(
            R.string.item_history_entry_correct_answers, correctAnswers, totalAnswers, percent
        )
    }
}