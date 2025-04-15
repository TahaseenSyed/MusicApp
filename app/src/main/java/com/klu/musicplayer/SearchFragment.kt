package com.klu.musicplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchFragment : Fragment() {

    private lateinit var searchInput: EditText
    private lateinit var recentSearch1: TextView
    private lateinit var recentSearch2: TextView
    private lateinit var popularSearchRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        searchInput = view.findViewById(R.id.searchInput)
        recentSearch1 = view.findViewById(R.id.recentSearch1)
        recentSearch2 = view.findViewById(R.id.recentSearch2)
        popularSearchRecyclerView = view.findViewById(R.id.popularSearchRecyclerView)

        // Setup RecyclerView
        popularSearchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        popularSearchRecyclerView.adapter = PopularSearchAdapter(getPopularSearches())

        return view
    }

    private fun getPopularSearches(): List<String> {
        return listOf(
            "The Weeknd",
            "Drake",
            "Taylor Swift",
            "BTS",
            "Ed Sheeran",
            "Ariana Grande",
            "Imagine Dragons",
            "Billie Eilish",
            "Post Malone"
        )
    }
}
