package nl.hva.frontend.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.hva.frontend.R
import nl.hva.frontend.databinding.ItemHistoryEntryBinding
import nl.hva.frontend.domain.model.HistoryEntry
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoryEntriesAdapter(
    private val historyEntries: List<HistoryEntry>
) : RecyclerView.Adapter<HistoryEntriesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryEntryBinding.bind(itemView)

        fun databind(historyEntry: HistoryEntry) {
            // parse string to LocalDateTime & define custom format
            val date = LocalDateTime.parse(historyEntry.date)
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")

            // calculate percentage of correct answers
            val totalAnswers: String = historyEntry.totalAnswers.toString()
            val correctAnswers: String = historyEntry.correctAnswers.toString()
            val percent: Int = (correctAnswers.toDouble() / totalAnswers.toDouble() * 100).toInt()

            binding.tvLanguage.text = historyEntry.language
            binding.tvDifficultyLevel.text = historyEntry.difficultyLevel
            binding.tvDate.text = date.format(formatter)
            binding.tvCorrectAnswers.text = itemView.context.resources.getString(
                R.string.item_history_entry_correct_answers, correctAnswers, totalAnswers, percent
            )
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called item_history_entry.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_entry, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return historyEntries.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(historyEntries[position])
    }
}