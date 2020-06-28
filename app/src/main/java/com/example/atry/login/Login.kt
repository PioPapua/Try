package com.example.atry.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.atry.R
import com.example.atry.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*

class Login : Fragment() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        binding.buttonLogin.setOnClickListener {
            viewModel.onLogin(password.text.toString(), username.text.toString())
        }

        // Setting up LiveData observation relationship
        viewModel.eventLogin.observe(this, Observer { isLoggedIn ->
            if (isLoggedIn) {
                loggedIn()
                viewModel.onLoginComplete() // It assures that the eventLogin is not triggered again on recreating the fragment by rotating the phone
            }
        })
        return binding.root
    }

    fun loggedIn(){
        // Navigate to next screen
        view?.findNavController()?.navigate(R.id.action_login_to_cameraPicture)
    }
}
