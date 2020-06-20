package com.example.atry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.atry.databinding.FragmentProductBinding


class Product : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentProductBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        binding.buttonNext.setOnClickListener {view : View ->
            // TODO Jump to next view -> view.findNavController().navigate(R.id.action_login_to_cameraPicture2)
        }
        return binding.root
    }

}
