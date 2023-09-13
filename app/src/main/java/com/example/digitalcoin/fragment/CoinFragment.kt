package com.example.digitalcoin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.digitalcoin.R
import com.example.digitalcoin.databinding.FragmentEmptyComposeBinding
import com.example.digitalcoin.ui.CoinUI
import com.example.digitalcoin.ui.viewmodel.CoinsListViewModel


class CoinFragment : Fragment() {
    private lateinit var args: CoinFragmentArgs
    private lateinit var viewModel: CoinsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = CoinFragmentArgs.fromBundle(requireArguments())
        viewModel = ViewModelProvider(requireActivity())[CoinsListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentEmptyComposeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_empty_compose, container, false)
        binding.composeContainer.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CoinUI(viewModel.state)
            }
        }

        return binding.root
    }
}