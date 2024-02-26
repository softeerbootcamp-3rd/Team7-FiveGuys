package org.softeer.robocar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.R
import org.softeer.robocar.data.model.CarPoolType
import org.softeer.robocar.ui.viewmodel.CarPoolRequestViewModel

@AndroidEntryPoint
class RideTypeSelectionFragment : Fragment(R.layout.fragment_ride_type_selection){

    private lateinit var navController: NavController
    private val viewModel: CarPoolRequestViewModel by activityViewModels()
    lateinit var action: NavDirections

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        val carPoolInfo = viewModel.carPoolInfo.value!!
        val carPoolType = carPoolInfo.carPoolType
        when(carPoolType){
            CarPoolType.JOIN -> {
                action = RideTypeSelectionFragmentDirections.actionRideTypeSelectionFragmentToCarPoolCallFragment(
                    countOfMen = carPoolInfo.countMale.toInt(),
                    countOfWomen = carPoolInfo.countFemale.toInt(),
                    startLocation = carPoolInfo.departureRoadAddressName,
                    destinationLocation = carPoolInfo.destRoadAddressName,
                    taxiType = carPoolInfo.taxiType
                )
            }
            CarPoolType.ALONE ->{
                action = RideTypeSelectionFragmentDirections.actionRideTypeSelectionFragmentToTaxiCallFragment(
                    countOfMen = carPoolInfo.countMale.toInt(),
                    countOfWomen = carPoolInfo.countFemale.toInt(),
                    startLocation = carPoolInfo.departureRoadAddressName,
                    destinationLocation = carPoolInfo.destRoadAddressName,
                    taxiType = carPoolInfo.taxiType
                )
            }
            CarPoolType.RECRUIT->{
                action = RideTypeSelectionFragmentDirections.actionRideTypeSelectionFragmentToCarPoolMatchingFragment(
                    countOfMen = carPoolInfo.countMale.toInt(),
                    countOfWomen = carPoolInfo.countFemale.toInt(),
                    startLocation = carPoolInfo.departureRoadAddressName,
                    destinationLocation = carPoolInfo.destRoadAddressName,
                    taxiType = carPoolInfo.taxiType
                )
            }
        }

        navController.navigate(action, NavOptions.Builder()
            .setPopUpTo(R.id.rideTypeSelectionFragment, true)
            .build()
        )
    }
}