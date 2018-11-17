package huszti.martin.deadline


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.arch.persistence.room.Room
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import huszti.martin.deadline.adapter.TaskAdapter
import huszti.martin.deadline.data.*
import huszti.martin.deadline.fragments.DetailsTaskDialogFragment
import huszti.martin.deadline.fragments.NewTaskDialogFragment
import kotlinx.android.synthetic.main.activity_tasks.*
import kotlinx.android.synthetic.main.content_tasks.*


class MainActivity : AppCompatActivity(), TaskAdapter.taskItemClickListener,
        NewTaskDialogFragment.NewTaskDialogListener, DetailsTaskDialogFragment.DetailsDialogListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var taskadapter: TaskAdapter
    private lateinit var database: TaskDatabase

    private val CHANNEL_ID = "10"
    private val notificationId = 10145

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        toolbar.dismissPopupMenus()
        setSupportActionBar(toolbar)


        fab.setOnClickListener {
            NewTaskDialogFragment().show(supportFragmentManager, NewTaskDialogFragment.TAG)
        }

        database = Room.databaseBuilder(applicationContext, TaskDatabase::class.java, "task-list").build()
        initRecycleView()

       // createNotificationChannel()
    }


    override fun onResume() {
        super.onResume()
//        with(NotificationManagerCompat.from(this)) {
//            notify(notificationId, createNotification())
//        }
    }

    private fun initRecycleView() {
        recyclerView = MainRecyclerView
        taskadapter = TaskAdapter(this)
        LoadTasksAsync(database, taskadapter).execute()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskadapter

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onTaskCreated(newItem: Task) {
        AddTaskAsync(newItem, database, taskadapter).execute()
    }

    override fun onItemChanged(item: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemDeleted(item: Task?) {
        DeleteTaskAsync(item, database).execute()
    }

    override fun onItemDetailsClicked(item: Task) {
        DetailsTaskDialogFragment.newInstance(item).show(supportFragmentManager,
                DetailsTaskDialogFragment.TAG)
    }

    override fun onModyfyClicked(newItem: Task) {
        NewTaskDialogFragment.newInstance(newItem).show(supportFragmentManager, NewTaskDialogFragment.TAG)
        taskadapter.deleteItem(newItem)
        DeleteTaskAsync(newItem, database).execute()
    }


}
