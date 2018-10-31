package huszti.martin.deadline.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.DialogFragment
import android.util.EventLog
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar
import huszti.martin.deadline.R
import huszti.martin.deadline.data.Task
import kotlinx.android.synthetic.main.dialog_new_task.view.*
import java.util.*
import java.util.concurrent.TimeUnit


class NewTaskDialogFragment : DialogFragment() {

    companion object {
        val TAG = "NewTaskDialogFragment"
    }

    var nameEditText: EditText? = null
    var descriptionEditText: EditText? = null
    var ddp: CollapsibleCalendar? = null


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
                .setPositiveButton(R.string.ok) { _, _ ->
                    listener.onTaskCreated(getTask())
                }
                .setNegativeButton(R.string.cancel, null)
                .create()
    }

    private fun getTask(): Task {
        val task = Task()
        task.title = nameEditText?.text.toString()
        task.description = descriptionEditText?.text.toString()


        var dS = Date() //dateSelected
        dS.date = ddp!!.selectedDay.day
        dS.month = ddp!!.selectedDay.month // mert 0-tól 11ig megy
        dS.year = ddp!!.year-1900
        dS.hours = 23
        dS.minutes = 59

        var today = Date()
        today.year

        task.dueDate = (dS.year+1900).toString() + "-" + dS.month.toString() + "-" + dS.date
                .toString()
        task.remanindays = TimeUnit.MILLISECONDS.toDays(dS.time - today.time).toInt()
        addEvent(task.title, dS.time )
        return task
    }

    fun addEvent(title: String, begin: Long) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)

        }

            startActivity(intent)

    }


    private fun getContentView(): View {
        val contentView: View = LayoutInflater.from(context).inflate(R.layout.dialog_new_task, null)
        nameEditText = contentView.TaskTitleEditText
        descriptionEditText = contentView.TaskDescriptionEditText
        ddp = contentView.datePicker


        return contentView
    }
}