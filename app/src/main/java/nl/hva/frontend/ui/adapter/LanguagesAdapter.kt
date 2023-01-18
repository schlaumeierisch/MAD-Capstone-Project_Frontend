package nl.hva.frontend.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.hva.frontend.R
import nl.hva.frontend.databinding.ItemLanguageOrDifficultyLevelBinding
import nl.hva.frontend.domain.model.Language

class LanguagesAdapter(
    private val languages: List<Language>, private val clickListener: (Language) -> Unit
) : RecyclerView.Adapter<LanguagesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLanguageOrDifficultyLevelBinding.bind(itemView)

        fun databind(language: Language) {
            binding.tvLanguageOrDifficultyLevel.text = language.name

            // apply click listener
            binding.root.setOnClickListener {
                clickListener(language)
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
        return languages.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(languages[position])
    }
}