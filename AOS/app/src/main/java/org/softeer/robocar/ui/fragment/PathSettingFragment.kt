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
import kotlinx.coroutines.launch
import org.softeer.robocar.BuildConfig
import org.softeer.robocar.databinding.FragmentPathSettingBinding
import org.softeer.robocar.ui.viewmodel.PathSettingViewModel

class PathSettingFragment : Fragment() {
    private var _binding: FragmentPathSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel:PathSettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPathSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pathSettingViewModel = viewModel
        binding.lifecycleOwner = this@PathSettingFragment
        binding.editDestination.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    getSearchResult(BuildConfig.kakao_rest_api_key,binding.editDestination.text.toString())
                    true
                }
                else -> false
            }
        }

        binding.editDestMap.setOnClickListener {
            val action =
                PathSettingFragmentDirections.actionPathSettingFragmentToSelectDestinationFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getSearchResult(key: String, query: String) {
        lifecycleScope.launch {
            viewModel.getSearchResult(key, query)
        }
    }
}