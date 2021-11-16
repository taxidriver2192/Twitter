package com.example.myapplication

import android.os.Bundle
import android.os.Message
import com.example.myapplication.models.MessageAdapter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentSecondBinding
import com.example.myapplication.models.Messages
import com.example.myapplication.models.MessagesViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val messageViewModel: MessagesViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageViewModel.messageLiveData.observe(viewLifecycleOwner) { message ->
            binding.recyclerView.visibility = if (message == null) View.GONE else View.VISIBLE
            if (message != null) {
                binding.recyclerView.adapter = MessageAdapter<Messages>(message) {
                    val action = SecondFragmentDirections.actionSecondFragmentToCommentFragment(it)
                    findNavController().navigate(action)
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                //binding.recyclerView.adapter = adapter
            }
        }
        messageViewModel.reload()
            binding.swiperefresh.setOnRefreshListener {
                messageViewModel.reload()
                binding.swiperefresh.isRefreshing = false
            }
    }
         override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
