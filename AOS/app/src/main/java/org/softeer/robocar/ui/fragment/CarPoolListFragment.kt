package org.softeer.robocar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.databinding.FragmentCarPoolListBinding
import org.softeer.robocar.ui.adapter.CarPoolAdapter
import org.softeer.robocar.ui.adapter.CarPoolListClickListener
import org.softeer.robocar.ui.custom.MarginItemDecoration
import org.softeer.robocar.ui.viewmodel.CarPoolListViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CarPoolListFragment : Fragment(), CarPoolListClickListener {

    @Inject
    lateinit var viewModel: CarPoolListViewModel
    private lateinit var adapter: CarPoolAdapter
    private var _binding: FragmentCarPoolListBinding? = null
    lateinit var navController: NavController
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCarPoolList("startLocation", "destinationLocation")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCarPoolListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        adapter = CarPoolAdapter(this)
        viewModel.carPoolList.observe(viewLifecycleOwner) {
            val availableCarPoolCount = it.carPoolList.size
            adapter.submitList(it.carPoolList)
            binding.resultCountTitle.text = getResultCountTitle(availableCarPoolCount)
        }

        with(binding) {
            carPoolList.addItemDecoration(MarginItemDecoration(ITEM_MARGIN))
            carPoolList.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getResultCountTitle(availableCarPoolCount: Int): String {
        return "총 ${availableCarPoolCount}대의 동승 가능한 차량을 찾았어요"
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

    companion object {
        const val ITEM_MARGIN = 32
    }
}
