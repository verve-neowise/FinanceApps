package io.venom.flip.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import io.venom.flip.game.databinding.DialogRestartBinding

class RestartDialog(
    private val restart: () -> Unit
) : DialogFragment(R.layout.dialog_restart) {

    private lateinit var binding: DialogRestartBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DialogRestartBinding.bind(view)

        binding.restart.setOnClickListener {
            restart()
            dismiss()
        }
    }

    override fun getTheme(): Int = R.style.RoundedCornersDialog
}