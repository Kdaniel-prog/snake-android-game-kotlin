package hu.bme.aut.android.nagyhazi.fragment

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import hu.bme.aut.android.nagyhazi.data.TopScore
import hu.bme.aut.android.nagyhazi.databinding.DialogEditPlayerNameBinding

class EditTopScoreDialogFragment(item: TopScore) : DialogFragment() {
    companion object {
        const val TAG = "EditTopScoreDialogFragment"
    }

    private lateinit var listener: EditTopScoreDialogListener
    private var item = item
    private var _binding: DialogEditPlayerNameBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity: FragmentActivity = requireActivity()!!
        listener = if (activity is EditTopScoreDialogListener) {
            activity
        } else {
            throw RuntimeException("Activity must implement the EditTopScoreDialogListener interface!")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogEditPlayerNameBinding.inflate(LayoutInflater.from(context))
        binding.etName.text.append(item.name)
        binding.etScore.text.append(item.score)
        return AlertDialog.Builder(requireActivity())
            .setTitle("Edit player")
            .setView(binding.root)
            .setPositiveButton(R.string.ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onTopScoreEdited(getTopScore())
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }
    private fun isValid(): Boolean {
        return binding.etName.text.isNotEmpty()
    }

    private fun getTopScore(): TopScore {

        item.name = binding.etName.text.toString()
        item.score = binding.etScore.text.toString()
        return item

    }

    interface EditTopScoreDialogListener {
        fun onTopScoreEdited(topScore: TopScore)
    }
}