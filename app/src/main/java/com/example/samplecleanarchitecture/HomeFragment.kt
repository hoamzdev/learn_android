package com.example.samplecleanarchitecture

import com.example.samplecleanarchitecture.base.BaseFragment
import com.example.samplecleanarchitecture.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun inflateViewBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)

    //setup view
    override fun initView() {

    }

    //listener event click
    override fun bindView() {

    }

    //handle observer data
    override fun bindViewModel() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}