package com.example.atry.labelsTable

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
        val dataSource = ConzoomDatabase.getInstance(application).labelDao
        val viewModelFactory =
            LabelsTableViewModelFactory(
                dataSource,
                application
            )

        // Get safe arguments (idProduct)
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

        viewModel.labels.observe(this, Observer { labels ->
            val rowParams = TableRow.LayoutParams()
            for ((index, item) in labels.withIndex()) {

                val labelRow = TableRow(requireContext())

                val id = TextView(requireContext())
                id.text = labels.elementAt(index).id.toString()

                val description = TextView(requireContext())
                description.text = labels.elementAt(index).description
                if (description.text == "null") {description.text = "  -  "}

                val categoryType = TextView(requireContext())
                categoryType.text = labels.elementAt(index).categoryType

                labelRow.addView(id)
                labelRow.addView(description)
                labelRow.addView(categoryType)

                labelRow.layoutParams = rowParams
                labelRow.background = context?.getDrawable(R.drawable.table_border)

                tableHeader.addView(labelRow)
            }
        })
        Log.d("TAG: ", "Current id: ${args.idProduct}")
        return binding.root
    }

    private fun additionClicked () {
        view?.findNavController()?.navigate(R.id.action_labelsTable_to_labelAdd)
    }

    private fun navigationClicked () {
//        view?.findNavController()?.navigate(R.id.action_labelsTable_to_Home)
    }
}
