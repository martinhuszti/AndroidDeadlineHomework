package huszti.martin.deadline.fragments


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import huszti.martin.deadline.R
import huszti.martin.deadline.data.Task
import kotlinx.android.synthetic.main.dialog_detail_task.view.*


class DetailsTaskDialogFragment : DialogFragment() {

    interface DetailsDialogListener {
        fun onModyfyClicked(newItem: Task)
    }


    companion object {
        val TAG = "DetailsTaskDialogFragment"
        var mytask: Task? = null
        fun newInstance(task: Task): DialogFragment {
            mytask = task
            return DetailsTaskDialogFragment()
        }

    }
    private lateinit var listener: DetailsDialogListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is DetailsDialogListener) {
            listener = activity as DetailsDialogListener
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
                .setTitle(R.string.details)
                .setView(getContentView())
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.modify) { _, _ ->
                    listener.onModyfyClicked(mytask!!)
                    dialog.cancel()
                }
                .create()


    }

    private fun getContentView(): View {
        val contentView: View = LayoutInflater.from(context).inflate(R.layout.dialog_detail_task, null)
        contentView.task_title_textview.text = mytask?.title
        contentView.expand_text_view.text = mytask?.description
        contentView.DueDateTextView.text = mytask?.dueDate.toString()
        return contentView
    }


}