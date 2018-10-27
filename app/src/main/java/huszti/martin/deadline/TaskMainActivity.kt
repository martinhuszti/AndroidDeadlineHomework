package huszti.martin.deadline


import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import huszti.martin.deadline.adapter.TaskAdapter
import huszti.martin.deadline.data.*
import huszti.martin.deadline.fragments.NewTaskDialogFragment
import kotlinx.android.synthetic.main.activity_tasks.*


class TaskMainActivity : AppCompatActivity(), TaskAdapter.taskItemClickListener, NewTaskDialogFragment.NewTaskDialogListener{



    private lateinit var recyclerView: RecyclerView
    private lateinit var taskadapter: TaskAdapter

    private lateinit var database: TaskDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        setSupportActionBar(toolbar)

        //floatingActionButton
        fab.setOnClickListener {
            NewTaskDialogFragment().show(supportFragmentManager, NewTaskDialogFragment.TAG)
        }

        database = Room.databaseBuilder(applicationContext, TaskDatabase::class.java, "task-list").build()
        initRecycleView()

    }


    private fun initRecycleView() {
        recyclerView = findViewById(R.id.MainRecyclerView)
        taskadapter = TaskAdapter(this)
        LoadTasksAsync(database, taskadapter).execute()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskadapter

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_tasks, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onTaskCreated(newItem: Task) {
        AddTaskAsync(newItem,database,taskadapter).execute()
    }

    override fun onItemChanged(item: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemDeleted(item: Task?) {
        DeleteTaskAsync(item,database).execute()
    }

}
