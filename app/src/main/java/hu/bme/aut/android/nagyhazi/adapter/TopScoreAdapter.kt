package hu.bme.aut.android.nagyhazi.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.nagyhazi.GameBeginActivity
import hu.bme.aut.android.nagyhazi.MenuActivity
import hu.bme.aut.android.nagyhazi.R
import hu.bme.aut.android.nagyhazi.TopScoreActivity
import hu.bme.aut.android.nagyhazi.data.TopScore
import hu.bme.aut.android.nagyhazi.databinding.PlayerListBinding


class TopScoreAdapter(private val listener: TopScoreActivity) :
    RecyclerView.Adapter<TopScoreAdapter.TopScoreViewHolder>() {
    private val items = mutableListOf<TopScore>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TopScoreViewHolder(
        PlayerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: TopScoreAdapter.TopScoreViewHolder, position: Int) {
        val TopScore = items[position]
        holder.binding.ivIcon.setImageResource(R.drawable.snake_head_green)
        holder.binding.tvName.text = TopScore.name
        holder.binding.tvScore.text = "Score: ${TopScore.score}"

        holder.binding.ibRemove.setOnClickListener{
            listener.onItemRemoved(TopScore)
        }
        holder.binding.ibEdit.setOnClickListener{
            listener.onItemChanged(TopScore)
        }
    }


    override fun getItemCount(): Int = items.size

    interface TopScoreClickListener {
        fun onItemChanged(item: TopScore)
        fun onItemRemoved(item: TopScore)
    }

    inner class TopScoreViewHolder(val binding: PlayerListBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    fun update(topscores: List<TopScore>) {
        items.clear()
        items.addAll(topscores)
        notifyDataSetChanged()
    }

    fun removeItem(item: TopScore){
        var id = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(id)
    }
}