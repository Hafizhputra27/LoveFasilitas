package com.example.lovefasilitas.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.lovefasilitas.HomeActivity
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lovefasilitas.LoveFasilitasApp
import com.example.lovefasilitas.R
import com.example.lovefasilitas.domain.common.toRupiahPerTrip
import com.example.lovefasilitas.domain.model.Facility
import com.example.lovefasilitas.domain.usecase.GetFacilitiesUseCase
import com.example.lovefasilitas.ui.booking.FacilityDetailFragment
import com.example.lovefasilitas.ui.common.FacilityAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by lazy {
        val app = requireActivity().application as LoveFasilitasApp
        val getFacilitiesUseCase = GetFacilitiesUseCase(app.container.facilityRepository)
        ViewModelProvider(this, HomeViewModelFactory(getFacilitiesUseCase))[HomeViewModel::class.java]
    }

    private val adapter by lazy {
        FacilityAdapter(
            onItemClick = { facility -> navigateToDetail(facility) },
            onBookClick = { facility -> navigateToDetail(facility) }
        )
    }

    private var allFacilities: List<Facility> = emptyList()
    private var currentFilterIndex = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(HomeActivity.EXTRA_USERNAME) ?: "User"
        view.findViewById<TextView>(R.id.tvUserName).text = username

        setupRecyclerView(view)
        setupCategoryChips(view)
        setupSearch(view)
        setupFeaturedBanner(view)
        observeViewModel()
    }

    private fun setupRecyclerView(view: View) {
        val rvFacilities = view.findViewById<RecyclerView>(R.id.rvFacilities)
        rvFacilities.layoutManager = LinearLayoutManager(requireContext())
        rvFacilities.adapter = adapter
    }

    private fun setupCategoryChips(view: View) {
        val chips = listOf(
            view.findViewById<Chip>(R.id.chipAll),
            view.findViewById<Chip>(R.id.chipHall),
            view.findViewById<Chip>(R.id.chipRoom),
            view.findViewById<Chip>(R.id.chipStudio),
            view.findViewById<Chip>(R.id.chipOutdoor)
        )
        chips.forEachIndexed { index, chip ->
            chip?.setOnClickListener {
                currentFilterIndex = index
                filterByCategory(index)
            }
        }
    }

    private fun setupSearch(view: View) {
        val etSearch = view.findViewById<android.widget.EditText>(R.id.etSearch)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim().lowercase()
                val filtered = if (query.isEmpty()) {
                    filterByCategoryOnly(currentFilterIndex)
                } else {
                    allFacilities.filter {
                        it.name.lowercase().contains(query) ||
                        it.description.lowercase().contains(query) ||
                        it.location.lowercase().contains(query)
                    }.let { categoryFiltered ->
                        if (currentFilterIndex == 0) categoryFiltered
                        else categoryFiltered.intersect(filterByCategoryOnly(currentFilterIndex).toSet()).toList()
                    }
                }
                adapter.submitList(filtered)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupFeaturedBanner(view: View) {
        view.findViewById<com.google.android.material.card.MaterialCardView>(R.id.featuredBanner)?.setOnClickListener {
            allFacilities.firstOrNull()?.let { navigateToDetail(it) }
        }
    }

    private fun filterByCategory(index: Int) {
        val query = requireView().findViewById<android.widget.EditText>(R.id.etSearch).text.toString().trim().lowercase()
        val filtered = if (query.isNotEmpty()) {
            val nameFiltered = allFacilities.filter {
                it.name.lowercase().contains(query) || it.description.lowercase().contains(query)
            }
            if (index == 0) nameFiltered
            else nameFiltered.intersect(filterByCategoryOnly(index).toSet()).toList()
        } else {
            filterByCategoryOnly(index)
        }
        adapter.submitList(filtered)
    }

    private fun filterByCategoryOnly(index: Int): List<Facility> = when (index) {
        0 -> allFacilities
        1 -> allFacilities.filter { it.name.contains("Hall", ignoreCase = true) }
        2 -> allFacilities.filter { it.name.contains("Room", ignoreCase = true) }
        3 -> allFacilities.filter { it.name.contains("Studio", ignoreCase = true) }
        4 -> allFacilities.filter { it.name.contains("Outdoor", ignoreCase = true) }
        else -> allFacilities
    }

    private fun navigateToDetail(facility: Facility) {
        val fragment = FacilityDetailFragment.newInstance(facility)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            val view = requireView()
            view.findViewById<View>(R.id.skeletonContainer).visibility = if (loading) View.VISIBLE else View.GONE
            view.findViewById<RecyclerView>(R.id.rvFacilities).visibility = if (loading) View.GONE else View.VISIBLE
        }

        viewModel.facilities.observe(viewLifecycleOwner) { facilities ->
            allFacilities = facilities
            adapter.submitList(facilities)

            val view = requireView()
            facilities.firstOrNull()?.let { featured ->
                view.findViewById<TextView>(R.id.tvFeaturedName)?.text = featured.name
                view.findViewById<TextView>(R.id.tvFeaturedPrice)?.text = featured.price.toRupiahPerTrip()
                view.findViewById<ImageView>(R.id.ivFeaturedImage)?.setImageResource(featured.imageResId)
            }

            if (facilities.isEmpty()) {
                view.findViewById<View>(R.id.emptyState).visibility = View.VISIBLE
            } else {
                view.findViewById<View>(R.id.emptyState).visibility = View.GONE
            }
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
