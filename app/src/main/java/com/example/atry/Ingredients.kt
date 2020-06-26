package com.example.atry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.atry.database.Database
import com.example.atry.databinding.FragmentIngredientsBinding


class Ingredients : Fragment() {
    private lateinit var viewModel: IngredientsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentIngredientsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_ingredients,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = Database.getInstance(application).ingredientDatabaseDao
        val viewModelFactory = IngredientsViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(IngredientsViewModel::class.java)
        binding.ingredientsViewModel = viewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }
}
