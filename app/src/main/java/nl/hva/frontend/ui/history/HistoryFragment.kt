package nl.hva.frontend.ui.history

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
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
import nl.hva.frontend.databinding.FragmentHistoryBinding
import nl.hva.frontend.domain.model.HistoryEntry
import nl.hva.frontend.ui.adapter.HistoryEntriesAdapter
import nl.hva.frontend.ui.vm.HistoryViewModel

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private var historyEntries = listOf<HistoryEntry>()
    private lateinit var historyEntriesAdapter: HistoryEntriesAdapter

    private val historyViewModel: HistoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
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
        binding.header.tvTitle.text = resources.getString(R.string.history)

        // set navigation(s)
        binding.header.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_homeFragment)
        }

        // initialize delete all history entries button
        binding.fabDeleteAllHistoryEntries.setOnClickListener {
            openConfirmationDialog()
        }

        // get data (history entries) to fill the RecyclerView
        historyViewModel.getAllHistoryEntries()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                historyViewModel.historyEntries.collect {
                    // enable/disable delete all entries button
                    binding.fabDeleteAllHistoryEntries.isEnabled = it.historyEntries.isNotEmpty()

                    historyEntriesAdapter = HistoryEntriesAdapter(it.historyEntries)
                    historyEntries = it.historyEntries
                    initRv()
                }
            }
        }
    }

    private fun initRv() {
        binding.rvHistoryEntries.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvHistoryEntries.adapter = historyEntriesAdapter

        createItemTouchHelper().attachToRecyclerView(binding.rvHistoryEntries)
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
        dialogConfirmButton.text = getString(R.string.confirmation_dialog_delete_button)

        // cancel button functionality
        dialogCancelDeleteBtn.setOnClickListener {
            dialog.dismiss()
        }

        // get dialog title & dialog message text views
        val dialogTitle = dialog.findViewById<TextView>(R.id.tvTitleOfDialog)
        val dialogMessage = dialog.findViewById<TextView>(R.id.tvMessageOfDialog)

        // set dialog title
        dialogTitle.text = getString(R.string.confirmation_dialog_title_delete_history_entries)

        // set dialog message
        dialogMessage.text = getString(R.string.confirmation_dialog_message_delete_history_entries)

        // confirm button functionality
        dialogConfirmButton.setOnClickListener {
            dialog.dismiss()
            historyViewModel.deleteAllHistoryEntries()
            findNavController().navigate(R.id.action_historyFragment_to_homeFragment)
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
                val historyEntry = historyEntries[position]

                historyViewModel.deleteHistoryEntryById(historyEntry.id)
                initViews()
            }
        }

        return ItemTouchHelper(callback)
    }
}