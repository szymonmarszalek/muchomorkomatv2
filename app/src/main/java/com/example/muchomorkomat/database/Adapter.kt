package com.vocale.vocale.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muchomorkomat.R
import com.example.muchomorkomat.database.MushroomEntity


class Adapter() : RecyclerView.Adapter<Adapter.MushroomHolder>() {
    private val mushrooms = mutableListOf<MushroomEntity>()

    fun setList(mushroomData: List<MushroomEntity>) {
        if (mushrooms.isNotEmpty())
            mushrooms.clear()
        mushrooms.addAll(mushroomData)
        notifyDataSetChanged()
    }

    class MushroomHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {
        fun bind(mushroomData: MushroomEntity) {
           view.findViewById<TextView>(R.id.mushroomElement).text = mushroomData.name
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MushroomHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(
            R.layout.element,
            parent,
            false
        )


        return MushroomHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: MushroomHolder, position: Int) {
        holder.bind(mushrooms[position])
    }

    override fun getItemCount(): Int {
        return mushrooms.size
    }
}

