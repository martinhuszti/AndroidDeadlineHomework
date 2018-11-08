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

        fun newInstance(task: Task): DetailsTaskDialogFragment {
            val frag = DetailsTaskDialogFragment()
            val args = Bundle()
            args.putString("title", "DETAILS")
            frag.setArguments(args)
            mytask=task
            return frag
        }

    }

    private val task: Task? = null

    var task_title: TextView? = null
    var description_text: ExpandableTextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
                .setTitle(R.string.details)
                .setView(getContentView())
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, null)
                .create()


    }

    private fun getContentView(): View {
        val contentView: View = LayoutInflater.from(context).inflate(R.layout.dialog_detail_task, null)
        task_title = contentView.task_title_textview
        task_title?.text= mytask?.title
        description_text = contentView.expand_text_view
        description_text?.setText("A hiedelemmel ellentétben a Lorem Ipsum nem véletlenszerû szöveg. Gyökerei egy Kr. E. 45-ös latin irodalmi klasszikushoz nyúlnak. Richarrd McClintock a virginiai Hampden-Sydney egyetem professzora kikereste az ismeretlenebb latin szavak közül az egyiket (consectetur) egy Lorem Ipsum részletbõl, és a klasszikus irodalmat átkutatva vitathatatlan forrást talált. A Lorem Ipsum az 1.10.32 és 1.10.33-as de Finibus Bonoruem et Malorum részleteibõl származik (A Jó és Rossz határai - Cicero), Kr. E. 45-bõl. A könyv az etika elméletét tanulmányozza, ami nagyon népszerû volt a reneszánsz korban. A Lorem Ipsum elsõ sora, Lorem ipsum dolor sit amet.. a 1.10.32-es bekezdésbõl származik")

        return contentView
    }


}