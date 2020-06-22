package com.example.atry

import android.os.Bundle
import android.text.Editable
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

        val args = ProductArgs.fromBundle(requireArguments()) // Get the barcode string in args.barcode

        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        viewModel.onBarcodeReceived(args.barcode)

        binding.productViewModel = viewModel
        binding.setLifecycleOwner(this) // Allows to use LiveData to automatically update DataBinding layouts

        // TODO Jump to next view -> view.findNavController().navigate(R.id.action_product_to_x)
        // onClickListener moved to xml through binding
        return binding.root
    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}
