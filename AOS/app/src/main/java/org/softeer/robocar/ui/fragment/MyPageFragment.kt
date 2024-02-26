package org.softeer.robocar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.databinding.FragmentMyPageBinding
import org.softeer.robocar.ui.viewmodel.MyPageViewModel

@AndroidEntryPoint
class MyPageFragment : Fragment() {


    private val viewModel: MyPageViewModel by viewModels()
    private var _binding: FragmentMyPageBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@MyPageFragment
            myPageViewModel = viewModel
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
