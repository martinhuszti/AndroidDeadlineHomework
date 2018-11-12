package huszti.martin.deadline.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.github.zagum.switchicon.SwitchIconView
import com.rengwuxian.materialedittext.MaterialEditText
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar
import huszti.martin.deadline.R
import huszti.martin.deadline.data.Task
import kotlinx.android.synthetic.main.dialog_new_task.view.*
import org.joda.time.DateTime


class NewTaskDialogFragment : DialogFragment() {


    companion object {
        val TAG = "NewTaskDialogFragment"
        var mytask: Task? = null

        fun newInstance(task: Task): DialogFragment {
            mytask = task
            return NewTaskDialogFragment()
        }

    }

    var nameEditText: MaterialEditText? = null
    var descriptionEditText: MaterialEditText? = null
    var datePicker: CollapsibleCalendar? = null
    var saveToCalendarSwitch: SwitchIconView? = null
    var saveToCalendarButton: LinearLayout? = null


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

        val day = datePicker!!.selectedDay
        task.dueDate = DateTime(day.year, day.month + 1, day.day, 23, 59)
        if (saveToCalendarSwitch!!.isIconEnabled) addEvent(task.title)

        return task
    }


    private fun addEvent(title: String) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
        }
        startActivity(intent)
    }


    private fun getContentView(): View {
        val contentView: View = LayoutInflater.from(context).inflate(R.layout.dialog_new_task, null)
        nameEditText = contentView.TaskTitleEditText
        descriptionEditText = contentView.TaskDescriptionEditText
        datePicker = contentView.datePicker
        saveToCalendarSwitch = contentView.saveToCalendarSwitchIcon
        saveToCalendarButton = contentView.saveToCalendarButton
        saveToCalendarButton!!.setOnClickListener {
            saveToCalendarSwitch!!
                    .switchState()
        }

        if (mytask != null) {
            nameEditText?.setText(mytask?.title)
            descriptionEditText?.setText(mytask?.description)
        }
        return contentView
    }
}