package org.softeer.robocar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.R
import org.softeer.robocar.ui.viewmodel.CarPoolListViewModel

@AndroidEntryPoint
class CarPoolCallFragment : Fragment(R.layout.fragment_car_pool_call) {

    private val viewModel: CarPoolListViewModel by activityViewModels()
    private lateinit var navController : NavController
    private lateinit var action : NavDirections

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCarPoolList("서울 강남구 학동로 180","서울 강남구 학동로 119",2,2)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        viewModel.carPoolList.observe(viewLifecycleOwner){ res ->
            if(res.carPoolList.isEmpty()){
                action = CarPoolCallFragmentDirections.actionCarPoolCallFragmentToNoAvailableCarPoolFragment()
            } else {
                action = CarPoolCallFragmentDirections.actionCarPoolCallFragmentToCarPoolList()
            }

            navController.navigate(action, NavOptions.Builder()
                .setPopUpTo(R.id.carPoolCallFragment, true)
                .build()
            )
        }
    }
}