package com.example.digitalcoin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.digitalcoin.R
import com.example.digitalcoin.databinding.FragmentEmptyComposeBinding
import com.example.digitalcoin.ui.CoinDetailScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CoinDetailFragment : Fragment() {
    private lateinit var args: CoinDetailFragmentArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = CoinDetailFragmentArgs.fromBundle(requireArguments())
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
                CoinDetailScreen(
                    id = args.id
                )
            }
        }

        return binding.root
    }
}