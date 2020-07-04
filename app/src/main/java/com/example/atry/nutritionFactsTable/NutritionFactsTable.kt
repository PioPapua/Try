package com.example.atry.nutritionFactsTable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TableRow
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
        val dataSource = ConzoomDatabase.getInstance(application)
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
            for ((index, item) in nutritionFacts.withIndex()) {

                val labelRow = TableRow(requireContext())

                val selected = CheckBox(requireContext())
                selected.id = nutritionFacts.elementAt(index).id
                selected.isChecked = false
                val checkBoxListener = View.OnClickListener { view ->
                    viewModel.onNutritionFactClicked(selected.id, selected.isChecked)
                    }
                selected.setOnClickListener(checkBoxListener)

                val id = TextView(requireContext())
                id.id = nutritionFacts.elementAt(index).id
                id.text = id.id.toString()

                val name = TextView(requireContext())
                name.id = nutritionFacts.elementAt(index).id
                name.text = nutritionFacts.elementAt(index).name

                val description = TextView(requireContext())
                description.text = nutritionFacts.elementAt(index).description
                if (description.text == "null") {description.text = "  -  "}

                val dairyRecommendation = TextView(requireContext())
                dairyRecommendation.text = nutritionFacts.elementAt(index).dairyRecommendation.toString()

                val portionType = TextView(requireContext())
                portionType.id = nutritionFacts.elementAt(index).id
                portionType.text = nutritionFacts.elementAt(index).portionType

                val informationLink = TextView(requireContext())
                informationLink.text = nutritionFacts.elementAt(index).informationLink
                if (informationLink.text == "null") {informationLink.text = "  -  "}

                labelRow.addView(selected)
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
        view?.findNavController()?.navigate(R.id.action_nutritionFactsTable_to_nutritionFacts)
    }
}