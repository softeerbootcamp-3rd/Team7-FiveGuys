package org.softeer.robocar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.softeer.robocar.databinding.FragmentNoAvailableCarPoolBinding

class NoAvailableCarPoolFragment : Fragment() {

    private var _binding : FragmentNoAvailableCarPoolBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoAvailableCarPoolBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toHomeButton.setOnClickListener {
            val action = NoAvailableCarPoolFragmentDirections.actionNoAvailableCarPoolFragmentToHomeActivity()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}