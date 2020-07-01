package com.example.atry.nutritionFactsTable

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
import com.example.atry.databinding.FragmentNutritionFactsTableBinding
import kotlinx.android.synthetic.main.fragment_labels_table.*

class NutritionFactsTable : Fragment() {
    private lateinit var viewModel: NutritionFactsTableViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNutritionFactsTableBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_nutrition_facts_table,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = ConzoomDatabase.getInstance(application).nutritionFactDao
        val viewModelFactory =
            NutritionFactsTableViewModelFactory(
                dataSource,
                application
            )

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NutritionFactsTableViewModel::class.java)
        binding.nutritionFactsTableViewModel = viewModel
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

        viewModel.nutritionFacts.observe(this, Observer { nutritionFacts ->
            val rowParams = TableRow.LayoutParams()
            Log.d("TAG: ", "Parametros de fila: $rowParams")
            // TODO Build the table with labels
            for ((index, item) in nutritionFacts.withIndex()) {

                val labelRow = TableRow(requireContext())

                val id = TextView(requireContext())
                id.text = nutritionFacts.elementAt(index).id.toString()

                val name = TextView(requireContext())
                name.text = nutritionFacts.elementAt(index).name

                val description = TextView(requireContext())
                description.text = nutritionFacts.elementAt(index).description
                if (description.text == "null") {description.text = "  -  "}

                val dairyRecommendation = TextView(requireContext())
                dairyRecommendation.text = nutritionFacts.elementAt(index).dairyRecommendation.toString()

                val portionType = TextView(requireContext())
                portionType.text = nutritionFacts.elementAt(index).portionType

                val informationLink = TextView(requireContext())
                informationLink.text = nutritionFacts.elementAt(index).informationLink

                labelRow.addView(id)
                labelRow.addView(name)
                labelRow.addView(description)
                labelRow.addView(dairyRecommendation)
                labelRow.addView(portionType)
                labelRow.addView(informationLink)

                labelRow.layoutParams = rowParams
                labelRow.background = context?.getDrawable(R.drawable.table_border)

                tableHeader.addView(labelRow)
            }
        })

        return binding.root
    }

    private fun additionClicked () {
        view?.findNavController()?.navigate(R.id.action_nutritionFactsTable_to_nutritionFactsAdd)
    }

    private fun navigationClicked () {
        // TODO Check args to be send to NutritionFacts Fragment - Use SafeArgs.
        view?.findNavController()?.navigate(R.id.action_nutritionFactsTable_to_nutritionFacts)
    }
}
