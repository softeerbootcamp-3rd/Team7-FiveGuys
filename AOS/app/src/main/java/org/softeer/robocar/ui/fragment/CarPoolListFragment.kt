package org.softeer.robocar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.databinding.FragmentCarPoolListBinding
import org.softeer.robocar.ui.activity.RequestCarPoolActivity
import org.softeer.robocar.ui.adapter.CarPoolAdapter
import org.softeer.robocar.ui.adapter.CarPoolListClickListener
import org.softeer.robocar.ui.custom.MarginItemDecoration
import org.softeer.robocar.ui.viewmodel.CarPoolListViewModel

@AndroidEntryPoint
class CarPoolListFragment : Fragment(), CarPoolListClickListener {

    private val viewModel: CarPoolListViewModel by activityViewModels()
    private lateinit var adapter: CarPoolAdapter
    private var _binding: FragmentCarPoolListBinding? = null
    private lateinit var navController: NavController
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCarPoolListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@CarPoolListFragment
            listViewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        adapter = CarPoolAdapter(this)
        viewModel.carPoolList.observe(viewLifecycleOwner) {
            adapter.submitList(it.carPoolList)
        }

        with(binding) {
            carPoolList.addItemDecoration(MarginItemDecoration(ITEM_MARGIN))
            carPoolList.adapter = adapter
        }
        showBackButtonInActivity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickDetailButton(position: Int) {
        val carPool = viewModel.carPoolList.value?.carPoolList?.get(position)!!
        val originalCharge = viewModel.carPoolList.value!!.originalCharge
        val action = CarPoolListFragmentDirections.actionCarPoolListToCarPoolRequestDetail(carPool, originalCharge)
        navController.navigate(action)
    }

    override fun onClickRequestCarPoolButton(carPoolId: Long) {
        val action = CarPoolListFragmentDirections.actionCarPoolListToCarPoolRequestDialog()
        navController.navigate(action)
    }

    private fun showBackButtonInActivity() {
        val activity = activity as RequestCarPoolActivity
        activity.binding.backButton.visibility = View.VISIBLE
    }

    companion object {
        const val ITEM_MARGIN = 32
    }
}

