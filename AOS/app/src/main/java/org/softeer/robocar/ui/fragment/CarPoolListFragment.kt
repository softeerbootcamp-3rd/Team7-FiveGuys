package org.softeer.robocar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.softeer.robocar.databinding.FragmentCarPoolListBinding
import org.softeer.robocar.ui.adapter.CarPoolAdapter
import org.softeer.robocar.ui.custom.MarginItemDecoration

class CarPoolListFragment : Fragment() {

    private lateinit var adapter: CarPoolAdapter
    private var _binding: FragmentCarPoolListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCarPoolListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CarPoolAdapter()

        with(binding) {
            carPoolList.addItemDecoration(MarginItemDecoration(ITEM_MARGIN))
            carPoolList.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ITEM_MARGIN = 32
    }
}
