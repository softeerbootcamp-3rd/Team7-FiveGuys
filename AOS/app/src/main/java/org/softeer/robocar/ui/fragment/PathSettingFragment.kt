package org.softeer.robocar.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.softeer.robocar.databinding.FragmentPathSettingBinding
import org.softeer.robocar.ui.adapter.ItemClickListener
import org.softeer.robocar.ui.adapter.PlaceSearchAdapter
import org.softeer.robocar.ui.viewmodel.MapViewModel

@AndroidEntryPoint
class PathSettingFragment : Fragment(), ItemClickListener{
    private var _binding: FragmentPathSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlaceSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPathSettingBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@PathSettingFragment
            mapViewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.placeList
        adapter = PlaceSearchAdapter(this)
        recyclerView.adapter = adapter

        binding.editOrigin.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                viewModel.sheetUp()
            }
        }

        binding.editDestination.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                viewModel.sheetUp()
            }
        }

        binding.editDestination.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val ime = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    ime.hideSoftInputFromWindow(view.windowToken,0)

                    getSearchResult()
                    viewModel.placeList.observe(viewLifecycleOwner){
                        adapter.submitList(it)
                    }
                    true
                }

                else -> false
            }
        }
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

    override fun toSelectDestination(name: String, address: String){
        viewModel.sheetDown()
        viewModel.setDraggable(false)
        val action =
            PathSettingFragmentDirections.actionPathSettingFragmentToSelectDestinationFragment(name,address)
       findNavController().navigate(action)
    }
}