package com.example.atry.ingredientsTable

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
import androidx.navigation.findNavController
import com.example.atry.R
import com.example.atry.database.ConzoomDatabase
import com.example.atry.databinding.FragmentIngredientsTableBinding
import kotlinx.android.synthetic.main.fragment_ingredients_table.*

class IngredientsTable : Fragment() {
    private lateinit var viewModel: IngredientsTableViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentIngredientsTableBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_ingredients_table,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = ConzoomDatabase.getInstance(application).ingredientDao
        val viewModelFactory =
            IngredientsTableViewModelFactory(
                dataSource,
                application
            )

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(IngredientsTableViewModel::class.java)
        binding.ingredientsTableViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.onAddButtonClicked.observe(this, Observer { addClicked ->
            if (addClicked) {
                additionClicked()
                viewModel.onAdditionCompleted()
            }
        })

        viewModel.onNextButtonClicked.observe(this, Observer { nextClicked ->
            if (nextClicked) {
                navigationClicked()
                viewModel.onNavigationCompleted()
            }
        })

        viewModel.ingredients.observe(this, Observer { ingredients ->
            val rowParams = TableRow.LayoutParams()
            var textParams = text_id.layoutParams
            Log.d("TAG: ", "Parametros de fila: $rowParams")
            // TODO Build the table with ingredients
            for ((index, item) in ingredients.withIndex()) {

                val ingredientRow = TableRow(requireContext())

                val id = TextView(requireContext())
                id.text = ingredients.elementAt(index).id.toString()

                val name = TextView(requireContext())
                name.text = ingredients.elementAt(index).name

                val description = TextView(requireContext())
                description.text = ingredients.elementAt(index).description
                if (description.text == "null") {description.text = "  -  "}

                val warning = TextView(requireContext())
                if (ingredients.elementAt(index).warning) {
                    warning.text = context?.getString(R.string.yes)
                } else {
                    warning.text = context?.getString(R.string.no)
                }

                val categoryType = TextView(requireContext())
                categoryType.text = ingredients.elementAt(index).categoryType

                ingredientRow.addView(id)
                ingredientRow.addView(name)
                ingredientRow.addView(description)
                ingredientRow.addView(warning)
                ingredientRow.addView(categoryType)

                ingredientRow.layoutParams = rowParams
                ingredientRow.background = context?.getDrawable(R.drawable.table_border)

                tableHeader.addView(ingredientRow)
            }
        })

        return binding.root
    }

    private fun additionClicked () {
        view?.findNavController()?.navigate(R.id.action_ingredientsTable_to_ingredientAdd)
    }

    private fun navigationClicked () {
        view?.findNavController()?.navigate(R.id.action_ingredientsTable_to_labelsTable)
    }
}