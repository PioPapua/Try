package com.example.atry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.atry.databinding.FragmentProductBinding


class Product : Fragment() {
    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentProductBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_product,
            container,
            false
        )

        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        binding.productViewModel = viewModel
        binding.setLifecycleOwner(this) // Allows to use LiveData to automatically update DataBinding layouts

        // TODO Jump to next view -> view.findNavController().navigate(R.id.action_login_to_cameraPicture2)
        // onClickListener moved to xml through binding
        return binding.root
    }
}
