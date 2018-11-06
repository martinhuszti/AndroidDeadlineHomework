package huszti.martin.deadline.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.github.zagum.switchicon.SwitchIconView
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar
import huszti.martin.deadline.R
import huszti.martin.deadline.data.Task
import kotlinx.android.synthetic.main.dialog_new_task.*
import kotlinx.android.synthetic.main.dialog_new_task.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class NewTaskDialogFragment : DialogFragment() {

    companion object {
        val TAG = "NewTaskDialogFragment"
    }

    var nameEditText: EditText? = null
    var descriptionEditText: EditText? = null
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

        var dateSelected = Date(datePicker!!.year - 1900, datePicker!!.selectedDay.month, datePicker!!.selectedDay.day, 23, 59)


        var today = Date()
        val df = SimpleDateFormat("yyyy-MM-dd")

        task.dueDate = df.format(dateSelected)
        task.remanindays = TimeUnit.MILLISECONDS.toDays(dateSelected.time - today.time).toInt()
        if(saveToCalendarSwitch!!.isIconEnabled) addEvent(task.title, dateSelected.time)
        return task
    }

    fun addEvent(title: String, begin: Long) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY,true)

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




        return contentView
    }
}