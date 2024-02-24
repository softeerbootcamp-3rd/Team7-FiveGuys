package org.softeer.robocar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import org.softeer.robocar.R

class RideTypeSelectionFragment : Fragment(R.layout.fragment_ride_type_selection){

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        // 탑승 유형에 따라서 fragment 이동
        val action = RideTypeSelectionFragmentDirections.actionRideTypeSelectionFragmentToCarPoolCallFragment()
        navController.navigate(action, NavOptions.Builder()
            .setPopUpTo(R.id.rideTypeSelectionFragment, true)
            .build()
        )
    }
}
