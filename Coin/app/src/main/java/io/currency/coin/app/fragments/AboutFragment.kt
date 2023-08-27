package io.currency.coin.app.fragments

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import io.currency.coin.app.R
import io.currency.coin.app.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about) {

    private lateinit var binding: FragmentAboutBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAboutBinding.bind(view)

        val asset = requireContext().assets.open("about.html")
        val bytes = ByteArray(asset.available())
        asset.read(bytes)
        val content = String(bytes)

        binding.content.text = Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY)
    }
}