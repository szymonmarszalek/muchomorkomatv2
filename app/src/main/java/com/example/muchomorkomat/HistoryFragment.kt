package com.example.muchomorkomat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muchomorkomat.database.AppDatabase
import com.example.muchomorkomat.database.MushroomEntity
import com.vocale.vocale.adapters.Adapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    private val adapter = Adapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = AppDatabase.create(requireContext())
        GlobalScope.launch {
            adapter.setList(database.mushroomDao().getAll())
        }
        view.findViewById<RecyclerView>(R.id.listView).let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(context)
        }


    }
}