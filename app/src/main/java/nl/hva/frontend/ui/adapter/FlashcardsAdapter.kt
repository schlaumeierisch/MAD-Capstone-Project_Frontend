package nl.hva.frontend.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.hva.frontend.R
import nl.hva.frontend.databinding.ItemFlashcardBinding
import nl.hva.frontend.domain.model.Flashcard

class FlashcardsAdapter(
    private val flashcards: List<Flashcard>
) : RecyclerView.Adapter<FlashcardsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFlashcardBinding.bind(itemView)

        fun databind(flashcard: Flashcard) {
            binding.tvFront.text = flashcard.front
            binding.tvBack.text = flashcard.back
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called item_flashcard.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_flashcard, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return flashcards.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(flashcards[position])
    }
}