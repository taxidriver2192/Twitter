package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.models.AuthViewmodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel: AuthViewmodel by activityViewModels()
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        viewModel.errorMessage.observe(this, {message ->
            binding.messageView.text = message
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.buttonFirst.setOnClickListener {
        //  findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        //}
        binding.Register.setOnClickListener {
            val email = binding.EditUsername.text.toString().trim()
            val password = binding.EditPassword.text.toString().trim()
            if (email.isNullOrEmpty()){
                binding.EditUsername.error = "Enter a valid Username"
                return@setOnClickListener
            }
            if (password.isNullOrEmpty()){
                binding.EditPassword.error = "Enter a valid password"
                return@setOnClickListener
            }
            viewModel.register(email, password)


        }

        binding.SigninLogin.setOnClickListener{
            val email = binding.EditUsername.text.toString().trim()
            val password = binding.EditPassword.text.toString().trim()
            if (email.isNullOrEmpty()){
                binding.EditUsername.error = "Enter a valid Username"
                return@setOnClickListener
            }
            if (password.isNullOrEmpty()){
                binding.EditPassword.error = "Enter a valid password"
                return@setOnClickListener
            }
            viewModel.signIn(email, password)
            if (viewModel.loggedOutData.value == false){
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}