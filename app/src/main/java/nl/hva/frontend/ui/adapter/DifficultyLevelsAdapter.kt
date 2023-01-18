package nl.hva.frontend.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.hva.frontend.R
import nl.hva.frontend.databinding.ItemLanguageOrDifficultyLevelBinding
import nl.hva.frontend.domain.model.DifficultyLevel

class DifficultyLevelsAdapter(
    private val difficultyLevels: List<DifficultyLevel>, private val clickListener: (DifficultyLevel) -> Unit
) : RecyclerView.Adapter<DifficultyLevelsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLanguageOrDifficultyLevelBinding.bind(itemView)

        fun databind(difficultyLevel: DifficultyLevel) {
            binding.tvLanguageOrDifficultyLevel.text = difficultyLevel.name

            // apply click listener
            binding.root.setOnClickListener {
                clickListener(difficultyLevel)
            }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called item_language_or_difficulty_level.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_language_or_difficulty_level, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return difficultyLevels.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(difficultyLevels[position])
    }
}