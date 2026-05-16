package com.example.lovefasilitas.ui.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lovefasilitas.LoveFasilitasApp
import com.example.lovefasilitas.R
import com.example.lovefasilitas.domain.usecase.GetChatListUseCase
import com.example.lovefasilitas.ui.common.ChatListAdapter
import com.google.android.material.snackbar.Snackbar

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val viewModel: ChatViewModel by lazy {
        val app = requireActivity().application as LoveFasilitasApp
        val getChatListUseCase = GetChatListUseCase(app.container.chatRepository)
        ViewModelProvider(this, ChatViewModelFactory(getChatListUseCase))[ChatViewModel::class.java]
    }

    private val adapter = ChatListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvChats = view.findViewById<RecyclerView>(R.id.rvChats)
        rvChats.layoutManager = LinearLayoutManager(requireContext())
        rvChats.adapter = adapter

        setupSearch(view)
        observeViewModel()
    }

    private fun setupSearch(view: View) {
        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim().lowercase()
                val list = viewModel.chatList.value ?: emptyList()
                val filtered = if (query.isEmpty()) {
                    list
                } else {
                    list.filter { it.senderName.lowercase().contains(query) || it.message.lowercase().contains(query) }
                }
                adapter.submitList(filtered)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeViewModel() {
        viewModel.chatList.observe(viewLifecycleOwner) { chats ->
            val view = requireView()
            val recyclerView = view.findViewById<RecyclerView>(R.id.rvChats)
            val emptyState = view.findViewById<View>(R.id.emptyState)
            if (chats.isEmpty()) {
                recyclerView.visibility = View.GONE
                emptyState.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                emptyState.visibility = View.GONE
                adapter.submitList(chats)
            }
        }
        viewModel.errorEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
