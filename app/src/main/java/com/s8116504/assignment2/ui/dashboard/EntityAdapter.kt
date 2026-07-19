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
    private val onItemClick: (Entity, Int) -> Unit
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

        val images = listOf(
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.img_5,
            R.drawable.img_6
        )
        holder.ivImage.setImageResource(images[position % images.size])

        holder.itemView.setOnClickListener {
            onItemClick(entity, position)
        }

        val titles = listOf(
            "bout' JWW",
            "bout' CBG",
            "040319.txt",
            "260515.svt",
            "film_jww",
            "film_cbg"
        )
        holder.tvTitle.text = titles[position % titles.size]
    }

    override fun getItemCount() = entities.size

    fun updateData(newEntities: List<Entity>) {
        entities = newEntities
        notifyDataSetChanged()
    }
}