package com.example.atry.productsTable

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.atry.R
import com.example.atry.database.ConzoomDatabase
import com.example.atry.databinding.FragmentProductsTableBinding
import kotlinx.android.synthetic.main.fragment_labels_table.*

class ProductsTable : Fragment() {
    private lateinit var viewModel: ProductsTableViewModel
    private lateinit var args: ProductsTableArgs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProductsTableBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_products_table,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = ConzoomDatabase.getInstance(application)
        val viewModelFactory =
            ProductsTableViewModelFactory(
                dataSource,
                application
            )

        // Get safe arguments (idProduct, textRecognized)
        args = ProductsTableArgs.fromBundle(requireArguments())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductsTableViewModel::class.java)
        binding.productsTableViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.onAddButtonClicked.observe(this, Observer { addClicked ->
            if (addClicked) {
                viewModel.onAdditionCompleted()
            }
        })

        viewModel.onSaveValuesComplete.observe(this, Observer { nextClicked ->
            if (nextClicked) {
                navigationClicked()
                viewModel.onNavigationCompleted()
            }
        })

        viewModel.onNextButtonClicked.observe(this, Observer { nextClicked ->
            if (nextClicked) {
                viewModel.onSaveValues(args.idProduct)
            }
        })

        viewModel.products.observe(this, Observer { products ->
            val rowParams = TableRow.LayoutParams()
            for ((index, item) in products.withIndex()) {

                val productRow = TableRow(requireContext())

                val id = TextView(requireContext())
                id.text = products.elementAt(index).id.toString()

                val name = TextView(requireContext())
                name.text = products.elementAt(index).name
                if (name.text == "null") {name.text = "  -  "}

                val user = TextView(requireContext())
                user.text = products.elementAt(index).user.toString() //TODO Pick username instead of user ID.

                val actions = TextView(requireContext())
                actions.text = "Modificar | Eliminar" //TODO Place buttons to edit or delete row.

                productRow.addView(id)
                productRow.addView(name)
                productRow.addView(user)
                productRow.addView(actions)

                productRow.layoutParams = rowParams
                productRow.background = context?.getDrawable(R.drawable.table_border)

                tableHeader.addView(productRow)
            }
        })
        return binding.root
    }

    private fun navigationClicked () {
        Log.d("TAG: ", "Navigation completed")
    }
}
