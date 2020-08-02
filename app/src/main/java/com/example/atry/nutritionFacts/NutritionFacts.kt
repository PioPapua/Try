package com.example.atry.nutritionFacts

import android.os.Bundle
import android.text.Editable
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.atry.R
import com.example.atry.database.ConzoomDatabase
import com.example.atry.databinding.FragmentNutritionFactsBinding
import kotlinx.android.synthetic.main.fragment_nutrition_facts.*

class NutritionFacts : Fragment() {
    private lateinit var viewModel: NutritionFactsViewModel
    private lateinit var args: NutritionFactsArgs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNutritionFactsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_nutrition_facts,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = ConzoomDatabase.getInstance(application)
        val viewModelFactory =
            NutritionFactsViewModelFactory(
                dataSource,
                application
            )

        // Get safe arguments (idProduct, textRecognized)
        args = NutritionFactsArgs.fromBundle(requireArguments())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NutritionFactsViewModel::class.java)
        binding.nutritionFactsViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.onClearTable.observe(viewLifecycleOwner, Observer { onClearClicked ->
            if (onClearClicked) {
                onAddNutritionFactsClicked()
                viewModel.onAddNutritionFactsCompleted()
            }
        })

        // Add Nutrition Facts button allows us to navigate to Nutrition Facts table to add new entries to our list.
        viewModel.onAddNutritionFacts.observe(viewLifecycleOwner, Observer { addNutritionFactsClicked ->
            if (addNutritionFactsClicked) {
                viewModel.onClearTable()
            }
        })

        // Add Button stores the values on the BD
        viewModel.onNextButtonClicked.observe(viewLifecycleOwner, Observer { nextClicked ->
            if (nextClicked) {
                viewModel.saveValues(args.idProduct,
                    edit_calories.text.toString(),
                    edit_carbohydrate.text.toString(),
                    edit_proteins.text.toString(),
                    edit_fat_total.text.toString(),
                    edit_fat_saturated.text.toString(),
                    edit_fat_trans.text.toString(),
                    edit_fiber.text.toString(),
                    edit_sodium.text.toString())
            }
        })

        viewModel.onSaveValuesComplete.observe(viewLifecycleOwner, Observer {completed ->
            if (completed) {
                navigationClicked()
                viewModel.onNavigationCompleted()
            }
        })

        viewModel.nutritionFacts.observe(viewLifecycleOwner, Observer { nutritionFacts ->
            val rowParams = TableRow.LayoutParams()
            rowParams.setMargins(16,0,16,0)
            for ((index, item) in nutritionFacts.withIndex()) {

                val labelRow = TableRow(requireContext())

                val name = TextView(requireContext())
                name.id = nutritionFacts.elementAt(index).id
                name.text = nutritionFacts.elementAt(index).name
                name.textSize = 18F
                name.gravity = Gravity.END

                val value = AppCompatEditText(requireContext())
                value.id = nutritionFacts.elementAt(index).id
                value.inputType = 2 // 2 Stands for "number"
                value.text = "0".toEditable()
                value.textSize = 18F
                value.gravity = Gravity.CENTER_HORIZONTAL
                value.doAfterTextChanged { viewModel.onValueChange(value.id, value.text.toString(), args.idProduct)}

                val portionType = TextView(requireContext())
                portionType.id = nutritionFacts.elementAt(index).id
                portionType.text = nutritionFacts.elementAt(index).portionType
                portionType.textSize = 18F

                labelRow.addView(name)
                labelRow.addView(value)
                labelRow.addView(portionType)

                labelRow.layoutParams = rowParams

                labelRow.background = context?.getDrawable(R.drawable.table_border)

                tableHeader.addView(labelRow)
            }
        })
        return binding.root
    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    // Allows us to manually add optional Nutrition Facts that are not listed by default.
    private fun onAddNutritionFactsClicked () {
        val action = NutritionFactsDirections.actionNutritionFactsToNutritionFactsTable(args.idProduct, args.textRecognized)
        view?.findNavController()?.navigate(action)
    }

    private fun navigationClicked () {
        val action = NutritionFactsDirections.actionNutritionFactsToPackaging(args.idProduct, args.textRecognized)
        view?.findNavController()?.navigate(action)
    }
}
