package com.example.doctorapp.presentation.homeContainer.profile.favorite

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentFavoriteBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.presentation.adapter.FavoriteAdapter
import com.example.doctorapp.presentation.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>(R.layout.fragment_favorite) {


    @Inject
    lateinit var appNavigation: AppNavigation

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    private val viewModel: FavoriteViewModel by viewModels()
    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter(requireContext()) { doctor ->
            val bundle = Bundle()
            bundle.putParcelable("doctor", doctor)
            appNavigation.openFavoriteToDoctorDetailScreen(bundle)

        }
    }

    override fun getVM(): FavoriteViewModel = viewModel


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        viewModel.getListFavorite()
        binding.apply {
            favoriteAdapter.submitList(viewModel.favoriteDoctorList.value)
            rvFavorite.apply {
                adapter = favoriteAdapter
                layoutManager = LinearLayoutManager(context)
            }

        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.favoriteDoctorList.observe(viewLifecycleOwner) {
            favoriteAdapter.submitList(it)
        }
    }


}