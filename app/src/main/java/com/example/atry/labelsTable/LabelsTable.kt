package com.example.atry.labelsTable

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TableRow
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.atry.R
import com.example.atry.database.ConzoomDatabase
import com.example.atry.databinding.FragmentLabelsTableBinding
import kotlinx.android.synthetic.main.fragment_labels_table.*

class LabelsTable : Fragment() {
    private lateinit var viewModel: LabelsTableViewModel
    private lateinit var args: LabelsTableArgs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLabelsTableBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_labels_table,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = ConzoomDatabase.getInstance(application)
        val viewModelFactory =
            LabelsTableViewModelFactory(
                dataSource,
                application
            )

        // Get safe arguments (idProduct, textRecognized)
        args = LabelsTableArgs.fromBundle(requireArguments())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LabelsTableViewModel::class.java)
        binding.labelsTableViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.onAddButtonClicked.observe(this, Observer { addClicked ->
            if (addClicked) {
                additionClicked()
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

        viewModel.labels.observe(this, Observer { labels ->
            val rowParams = TableRow.LayoutParams()
            for ((index, item) in labels.withIndex()) {

                val labelRow = TableRow(requireContext())

                val selected = CheckBox(requireContext())
                selected.id = labels.elementAt(index).id
                selected.isChecked = false
                val checkBoxListener = View.OnClickListener { view ->
                    viewModel.onLabelClicked(selected.id, selected.isChecked)
                }

                selected.setOnClickListener(checkBoxListener)
                val id = TextView(requireContext())
                id.text = labels.elementAt(index).id.toString()

                val description = TextView(requireContext())
                description.text = labels.elementAt(index).description
                if (description.text == "null") {description.text = "  -  "}

                val categoryType = TextView(requireContext())
                categoryType.text = labels.elementAt(index).categoryType

                labelRow.addView(selected)
                labelRow.addView(id)
                labelRow.addView(description)
                labelRow.addView(categoryType)

                labelRow.layoutParams = rowParams
                labelRow.background = context?.getDrawable(R.drawable.table_border)

                tableHeader.addView(labelRow)
            }
        })
        return binding.root
    }

    private fun additionClicked () {
        val action = LabelsTableDirections.actionLabelsTableToLabelAdd(args.idProduct, args.textRecognized)
        view?.findNavController()?.navigate(action)
    }

    private fun navigationClicked () {
        Log.d("TAG: ", "Navigation completed")
        val action = LabelsTableDirections.actionLabelsTableToProductsTable(args.idProduct, args.textRecognized)
        view?.findNavController()?.navigate(action)
    }
}
