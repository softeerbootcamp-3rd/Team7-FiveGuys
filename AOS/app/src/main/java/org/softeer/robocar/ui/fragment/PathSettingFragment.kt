package org.softeer.robocar.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.softeer.robocar.data.model.PlaceItem
import org.softeer.robocar.databinding.FragmentPathSettingBinding
import org.softeer.robocar.ui.adapter.ItemClickListener
import org.softeer.robocar.ui.adapter.PlaceSearchAdapter
import org.softeer.robocar.ui.viewmodel.PathSettingViewModel

@AndroidEntryPoint
class PathSettingFragment : Fragment(), ItemClickListener{
    private var _binding: FragmentPathSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel:PathSettingViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlaceSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPathSettingBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@PathSettingFragment
            pathSettingViewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.placeList
        adapter = PlaceSearchAdapter(this)
        recyclerView.adapter = adapter

        binding.editDestination.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    getSearchResult()
                    viewModel.placeList.observe(viewLifecycleOwner){
                        adapter.submitList(it)
                    }
                    true
                }
                else -> false
            }
        }


//        binding.editDestMap.setOnClickListener {
//            toSelectDestination()
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getSearchResult() {
        lifecycleScope.launch {
            viewModel.getSearchResult()

        }
    }

    override fun toSelectDestination(placeItem: PlaceItem){
        val action =
            PathSettingFragmentDirections.actionPathSettingFragmentToSelectDestinationFragment(placeItem)
       findNavController().navigate(action)
    }
}