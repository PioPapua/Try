package com.example.atry

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.atry.database.IngredientDatabaseDao

class IngredientsViewModel (val database: IngredientDatabaseDao, application: Application) : AndroidViewModel(application) {


}