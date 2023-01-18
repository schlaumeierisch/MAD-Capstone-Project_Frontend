package nl.hva.frontend.ui.settings

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nl.hva.frontend.R
import nl.hva.frontend.databinding.FragmentDifficultyLevelSettingsBinding
import nl.hva.frontend.domain.model.DifficultyLevel
import nl.hva.frontend.domain.model.Flashcard
import nl.hva.frontend.domain.model.Language
import nl.hva.frontend.ui.adapter.FlashcardsAdapter
import nl.hva.frontend.ui.vm.SettingsViewModel

@AndroidEntryPoint
class DifficultyLevelSettingsFragment : Fragment() {

    private var _binding: FragmentDifficultyLevelSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectedLanguage: Language

    private var difficultyLevels = listOf<DifficultyLevel>()
    private var flashcards = listOf<Flashcard>()
    private lateinit var flashcardsAdapter: FlashcardsAdapter

    private val settingsViewModel: SettingsViewModel by activityViewModels()

    private lateinit var difficultyLevelSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDifficultyLevelSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        difficultyLevelSpinner = binding.spnSelectDifficultyLevel
        selectedLanguage = settingsViewModel.selectedLanguage!!

        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        // set page title
        binding.header.tvTitle.text =
            resources.getString(R.string.language_settings, selectedLanguage.name)

        // set navigation(s)
        binding.header.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_settingsAdvancedFragment_to_settingsFragment)
        }

        // initialize add flashcard icon button
        binding.fabAddFlashcard.setOnClickListener {
            if (binding.spnSelectDifficultyLevel.selectedItem != null) {
                // set difficulty level for next screen
                settingsViewModel.selectedDifficultyLevel = binding.spnSelectDifficultyLevel.selectedItem as DifficultyLevel

                findNavController().navigate(R.id.action_settingsAdvancedFragment_to_addFlashcardFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_difficulty_level_selected_toast),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // initialize add difficulty level icon button
        binding.fabAddDifficultyLevel.setOnClickListener {
            findNavController().navigate(R.id.action_settingsAdvancedFragment_to_addDifficultyLevelFragment)
        }

        // initialize delete language button
        binding.fabDeleteLanguage.setOnClickListener {
            openConfirmationDialog(true)
        }

        // initialize delete difficulty button (set disabled until a difficulty is selected)
        binding.fabDeleteDifficultyLevel.setOnClickListener {
            openConfirmationDialog(false)
        }

        // get data (difficulty levels of language) to fill the dropdown menu
        settingsViewModel.getDifficultyLevelsByLanguageId(selectedLanguage.id)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingsViewModel.difficultyLevels.collect {
                    if (it.difficultyLevels.isNotEmpty()) {
                        // enable dropdown menu & delete difficulty button
                        binding.spnSelectDifficultyLevel.isEnabled = true
                        binding.fabDeleteDifficultyLevel.isEnabled = true

                        difficultyLevels = it.difficultyLevels
                        initDifficultyLevelsDropdownItems()
                    } else {
                        // disable dropdown menu & delete difficulty button
                        binding.spnSelectDifficultyLevel.isEnabled = false
                        binding.fabDeleteDifficultyLevel.isEnabled = false
                    }
                }
            }
        }

        // listener for difficulty levels dropdown menu
        difficultyLevelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                // save selected difficulty level
                settingsViewModel.selectedDifficultyLevel = binding.spnSelectDifficultyLevel.selectedItem as DifficultyLevel

                // get data (flashcards) to fill the recycler view
                settingsViewModel.getFlashcardsByDifficultyLevelId(settingsViewModel.selectedDifficultyLevel!!.id)
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        settingsViewModel.flashcards.collect {
                            flashcardsAdapter = FlashcardsAdapter(it.flashcards)
                            flashcards = it.flashcards
                            initRv()
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun initDifficultyLevelsDropdownItems() {
        val adapter: ArrayAdapter<DifficultyLevel> = ArrayAdapter<DifficultyLevel>(
            requireContext(), android.R.layout.simple_spinner_dropdown_item, difficultyLevels
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        difficultyLevelSpinner.adapter = adapter
    }

    private fun initRv() {
        binding.rvFlashcards.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvFlashcards.adapter = flashcardsAdapter

        createItemTouchHelper().attachToRecyclerView(binding.rvFlashcards)
    }

    private fun openConfirmationDialog(deleteLanguage: Boolean) {
        val dialog = Dialog(requireContext()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.confirmation_dialog)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        // get cancel & confirm buttons
        val dialogCancelDeleteBtn = dialog.findViewById<MaterialButton>(R.id.btnDialogCancel)
        val dialogConfirmButton = dialog.findViewById<MaterialButton>(R.id.btnDialogConfirm)
        dialogConfirmButton.text = getString(R.string.confirmation_dialog_delete_button)

        // cancel button functionality
        dialogCancelDeleteBtn.setOnClickListener {
            dialog.dismiss()
        }

        // get dialog title & dialog message text views
        val dialogTitle = dialog.findViewById<TextView>(R.id.tvTitleOfDialog)
        val dialogMessage = dialog.findViewById<TextView>(R.id.tvMessageOfDialog)

        if (deleteLanguage) {
            // set dialog title
            dialogTitle.text = getString(R.string.confirmation_dialog_title_delete_language)

            // set dialog message
            dialogMessage.text = getString(
                R.string.confirmation_dialog_message_delete_language, selectedLanguage.name
            )

            // confirm button functionality
            dialogConfirmButton.setOnClickListener {
                dialog.dismiss()
                settingsViewModel.deleteLanguageById(selectedLanguage.id)
                findNavController().navigate(R.id.action_settingsAdvancedFragment_to_settingsFragment)
            }
        } else {
            // set dialog title
            dialogTitle.text = getString(R.string.confirmation_dialog_title_delete_difficulty_level)

            // set dialog message
            dialogMessage.text = getString(
                R.string.confirmation_dialog_message_delete_difficulty_level,
                settingsViewModel.selectedDifficultyLevel!!.name
            )

            // confirm button functionality
            dialogConfirmButton.setOnClickListener {
                dialog.dismiss()
                settingsViewModel.deleteDifficultyLevelById(settingsViewModel.selectedDifficultyLevel!!.id)
                findNavController().navigate(R.id.action_settingsAdvancedFragment_to_settingsFragment)
            }
        }

        dialog.show()
    }

    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {
        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val flashcard = flashcards[position]

                settingsViewModel.deleteFlashcardById(flashcard.id)
                initViews()
            }
        }

        return ItemTouchHelper(callback)
    }
}