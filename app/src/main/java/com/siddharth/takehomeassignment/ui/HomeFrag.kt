package com.siddharth.takehomeassignment.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.siddharth.takehomeassignment.R
import com.siddharth.takehomeassignment.adapter.HomeAdapter
import com.siddharth.takehomeassignment.constants.Constants.LANDSCAPE_IMAGE
import com.siddharth.takehomeassignment.databinding.FragmentHomeBinding
import com.siddharth.takehomeassignment.utils.Response
import com.siddharth.takehomeassignment.viewmodel.HomeViewModel

class HomeFrag : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = HomeAdapter()
    private var sharedPreferences: SharedPreferences? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferences = requireActivity().getSharedPreferences("user", MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        subscribeToObservers()
    }

    private fun setupUi() {
        inflateAdIfNotCancelled()
        binding.rvHomepage.apply {
            adapter = this@HomeFrag.adapter
            itemAnimator = null
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun inflateAdIfNotCancelled() {

        val isCancelled = sharedPreferences!!.getBoolean("isAdCancelled", false)
        if (isCancelled) {
            val adView = binding.viewStubAd.inflate()
            setupUiForAd(adView)
            setupOnAdCloseListener(adView)
        }
    }

    private fun setupUiForAd(adView: View) {
        Log.d("HomeFrag","69")
        val ivAd = adView.findViewById<ImageView>(R.id.iv_ad)
        Glide.with(requireContext())
            .load(LANDSCAPE_IMAGE)
            .into(ivAd)
    }

    private fun setupOnAdCloseListener(adView: View) {
        val ivAdClose: ImageView = adView.findViewById(R.id.iv_close)
        ivAdClose.setOnClickListener {
            adView.animate().alphaBy(-1F).setDuration(1000).withEndAction {
                adView.isVisible = false
                setIsCancelBooleanInPrefs(true)
            }
        }
    }

    private fun setIsCancelBooleanInPrefs(isCancelled: Boolean) {
        sharedPreferences!!.edit {
            putBoolean("isAdCancelled", isCancelled)
            apply()
        }
    }

    private fun subscribeToObservers() {
        viewModel.userList.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    binding.progressCircular.isVisible = false
                    it.data?.let { it1 ->
                        adapter.setUserList(it1)
                        adapter.notifyDataSetChanged()      // only fetching one time hence refresh whole
                    }
                }
                is Response.Loading -> {
                    binding.progressCircular.isVisible = true
                }
                is Response.Error -> {
                    Snackbar.make(requireView(), "some error occurred", Snackbar.LENGTH_SHORT).show()
                    binding.rvHomepage.isVisible = false
                    binding.progressCircular.isVisible = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}