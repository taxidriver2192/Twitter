package com.example.myapplication

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentCommentBinding
import com.example.myapplication.models.*

class commentFragment : Fragment() {
    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!
    val messagesViewModel: MessagesViewModel by activityViewModels()
    val authViewModel: AuthViewmodel by activityViewModels()
    val message: MutableLiveData<Messages> = MutableLiveData<Messages>()
    private val args: commentFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val x = commentFragmentArgs.fromBundle(requireArguments())
        message.value = x.position

        message.observe(viewLifecycleOwner,{
            binding.textview.text = message.value?.content
            messagesViewModel.loadcomments(args.position.id)
        })//Delete commentar, når du clicker på en kommentar checker den om du useren der postede den er = user der clicker på den
        messagesViewModel.commentsLiveData.observe(viewLifecycleOwner,{
            binding.recyclerViewto.adapter = CommentAdapter<Comments>(it) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

                builder.setMessage("Delete Comment?")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                        messagesViewModel.deleteComment(message.value?.id!!, it.id)
                    }).setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })
                if (authViewModel.userLiveData.value?.email == it.user) {
                    builder.show()
                }
            }
        })
        binding.makeComment.setOnClickListener{
            val builder : AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val input = EditText(requireContext())
            builder.setView(input)
            builder.setMessage("Add en comment").setPositiveButton("Comment", DialogInterface.OnClickListener{dialog, id ->
                val addComment = Comments(1,message.value?.id!!,input.text.toString(),authViewModel.userLiveData.value?.email.toString())
                messagesViewModel.addcom(message.value?.id!!,addComment)
            }).setNegativeButton("Cancel", DialogInterface.OnClickListener{dialog, id ->
                dialog.cancel()
            })
            builder.show()
        }
        binding.swiperefreshto.setOnRefreshListener{
            messagesViewModel.loadcomments(message.value?.id!!)
            binding.swiperefreshto.isRefreshing = false
        }
        binding.Deletebutton.setOnClickListener{
            val id: Int = message.value?.id ?: return@setOnClickListener
            if (message.value?.user==authViewModel.userLiveData.value?.email){
            messagesViewModel.delete(id)
                findNavController().popBackStack()
        }
            else{
                return@setOnClickListener

            }            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
