package huszti.martin.deadline.fragments


import android.app.AlertDialog
import android.app.Dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View

import android.widget.TextView
import com.ms.square.android.expandabletextview.ExpandableTextView

import huszti.martin.deadline.R
import huszti.martin.deadline.data.Task
import kotlinx.android.synthetic.main.dialog_detail_task.view.*


class DetailsTaskDialogFragment : DialogFragment() {

    companion object {
        val TAG = "DetailsTaskDialogFragment"
        var mytask: Task? = null
        private const val KEY_TODO_DESCRIPTION = "KEY_TODO_DESCRIPTION"

        fun newInstance(task: Task): DialogFragment {
            mytask = task
            return DetailsTaskDialogFragment()
        }

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
                .setTitle(R.string.details)
                .setView(getContentView())
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.modify, null)
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