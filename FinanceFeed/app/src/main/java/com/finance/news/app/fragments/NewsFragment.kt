package com.finance.news.app.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.finance.news.app.adapters.TicketAdapter
import com.finance.news.app.data.Ticket
import com.finance.news.app.viewmodels.NewsViewModel
import com.fininace.news.app.R
import com.fininace.news.app.databinding.FragmentNewsBinding

enum class Source(@StringRes val titleRes: Int) {
    Remote(R.string.feed),
    Local(R.string.favorites)
}

class NewsFragment : Fragment(R.layout.fragment_news) {

    private val viewModel: NewsViewModel by activityViewModels()
    private val args: NewsFragmentArgs by navArgs()

    private lateinit var binding: FragmentNewsBinding

    private val adapter = TicketAdapter(
        switchFavorite = ::switchFavorite,
        readMore = ::readMore,
        share = ::share
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNewsBinding.bind(view)
        binding.title.setText(args.source.titleRes)

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        val source = if (args.source == Source.Remote) viewModel.news else viewModel.favorites

        source.observe(requireActivity()) { tickets ->
            adapter.setItems(tickets)
        }

        viewModel.fetch()
    }

    private fun switchFavorite(ticket: Ticket, position: Int) {
        if (ticket.isFavorite) {
            viewModel.removeFavorite(ticket)
        }
        else {
            viewModel.addFavorite(ticket)
        }
        adapter.update(
            index = position,
            ticket = ticket.apply {
                isFavorite = !ticket.isFavorite
            }
        )
    }

    private fun readMore(ticket: Ticket) {
        val intent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .build()
        intent.launchUrl(requireActivity(), Uri.parse(ticket.articleUrl));
    }

    private fun share(ticket: Ticket) {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, ticket.articleUrl)
            putExtra(Intent.EXTRA_TITLE, ticket.description)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }, null)
        startActivity(share)
    }
}