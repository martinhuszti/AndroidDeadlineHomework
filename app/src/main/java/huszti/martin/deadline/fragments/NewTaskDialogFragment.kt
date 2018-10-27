package huszti.martin.deadline.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import huszti.martin.deadline.R
import huszti.martin.deadline.data.Task


class NewTaskDialogFragment : DialogFragment() {

    companion object {
        val TAG = "NewTaskDialogFragment"
    }

    var nameEditText: EditText? = null
    var descriptionEditText: EditText? = null
    var dueDateEditText: EditText? = null


    interface NewTaskDialogListener {
        fun onTaskCreated(newItem: Task)
    }

    private lateinit var listener: NewTaskDialogListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is NewTaskDialogListener) {
            listener = activity as NewTaskDialogListener
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
                .setTitle(R.string.new_task)
                .setView(getContentView())
                .setPositiveButton(R.string.ok) { dialog, which ->
                    listener.onTaskCreated(getTask())
                }
                .setNegativeButton(R.string.cancel, null)
                .create()
    }

    private fun getTask(): Task {
        val task: Task = Task()
        task.title = nameEditText?.text.toString()
        task.description = descriptionEditText?.text.toString()
        task.dueDate = dueDateEditText?.text.toString()

        return task


    }


    private fun getContentView(): View {
        val contentView : View = LayoutInflater.from(context).inflate(R.layout.dialog_new_task, null)
        nameEditText = contentView.findViewById<EditText>(R.id.TaskTitleEditText)
        dueDateEditText = contentView.findViewById<EditText>(R.id.TaskDueDate)
        descriptionEditText = contentView.findViewById<EditText>(R.id.TaskDescriptionEditText)


        return contentView
    }
}