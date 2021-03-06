package com.udacity.asteroidProject3.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidProject3.repositories.AsteroidRepository
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }

        ViewModelProvider(this, MainViewModel.Factory(activity.application)).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter = AsteroidAdapter(AsteroidAdapter.AsteroidClickListener {
            viewModel.asteroidClicked(it)
        })

        viewModel.navigateToDetails.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let{
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_week_menu -> viewModel.onApplyFilter(AsteroidRepository.FilterType.WEEKLY)
            R.id.show_saved_menu -> viewModel.onApplyFilter(AsteroidRepository.FilterType.SAVED)
            R.id.show_today_menu -> viewModel.onApplyFilter(AsteroidRepository.FilterType.TODAY)
        }
        return true
    }
}
