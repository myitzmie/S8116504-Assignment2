package com.s8116504.assignment2.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.s8116504.assignment2.R
import com.s8116504.assignment2.data.model.Entity
import android.widget.ImageView

class EntityAdapter(
    private var entities: List<Entity> = listOf(),
    private val onItemClick: (Entity) -> Unit
) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    class EntityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvEntityTitle)
        val ivImage: ImageView = view.findViewById(R.id.ivEntityImage)    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entity, parent, false)
        return EntityViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        val entity = entities[position]
        holder.tvTitle.text = entity.title

        // Cycle through images since API doesn't return images
        val images = listOf(
            R.drawable.background,
            R.drawable.background,
            R.drawable.background,
            R.drawable.background,
            R.drawable.background,
            R.drawable.background
        )
        holder.ivImage.setImageResource(images[position % images.size])

        holder.itemView.setOnClickListener {
            onItemClick(entity)
        }
    }

    override fun getItemCount() = entities.size

    fun updateData(newEntities: List<Entity>) {
        entities = newEntities
        notifyDataSetChanged()
    }
}