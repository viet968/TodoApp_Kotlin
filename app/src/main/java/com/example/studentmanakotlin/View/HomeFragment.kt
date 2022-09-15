package com.example.studentmanakotlin.View

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentmanakotlin.Adapter.TaskAdapter
import com.example.studentmanakotlin.Adapter.TodayTasksAdapter
import com.example.studentmanakotlin.Event.ItemClick
import com.example.studentmanakotlin.Model.JoinPerson
import com.example.studentmanakotlin.Model.Task
import com.example.studentmanakotlin.Model.TodayTask
import com.example.studentmanakotlin.Model.Work
import com.example.studentmanakotlin.Presenter.IPresenter
import com.example.studentmanakotlin.R
import com.example.studentmanakotlin.ViewModel_LiveData.TaskViewModel
import com.example.studentmanakotlin.databinding.FragmentHomeBinding
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.*


class HomeFragment : Fragment(),ItemClick {
    private var fragBinding:FragmentHomeBinding? = null
    private val binding get() = fragBinding!!
    private val presenter: IPresenter = MainActivity.presenter
    // view
    private lateinit var taskAdapter: TaskAdapter
     private lateinit var todayTasksAdapter: TodayTasksAdapter
    // data
    private lateinit var tasks:MutableList<Task>
    private lateinit var todayTasks:MutableList<TodayTask>
    // task viewmodel
    private lateinit var taskViewMode : TaskViewModel
    var getTask:Task?=null

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result?.resultCode == Activity.RESULT_OK){
            getTask = result.data?.extras?.get("editedTask") as Task
            getTask!!.works = presenter.readWorksOfTask(getTask!!).toRealmList()
            getTask!!.personJoin = presenter.readJoinPersonOfTask(getTask!!).toRealmList()
            getTask!!.isInprogress = presenter.getIsInProgressForTask(getTask!!)
//            presenter.updateTask(getTask!!,getTask!!.works!!.toRealmList())
//            taskViewMode.setNewTasks(presenter.readTask())
            var newTasks:MutableList<Task> = presenter.readTask()
            var oldIndex:Int = newTasks.indexOf(getTask)
            newTasks.removeAt(oldIndex)
            newTasks.add(oldIndex,getTask!!)
            taskViewMode.setNewTasks(newTasks)
//            Toast.makeText(requireContext(),newTasks.size.toString(),Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragBinding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root

        anhXa()
        binding.recyInprogress.adapter = taskAdapter

        return view
    }



    private fun anhXa(){
//        loadTaskData()
        //task
        taskAdapter = TaskAdapter(presenter.readTask(),this)
        taskViewMode = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)
        taskViewMode.setNewTasks(presenter.readTask())
        taskViewMode.mTasks.observe(requireActivity(), Observer {
            taskAdapter = TaskAdapter(it,this)
            binding.recyInprogress.adapter = taskAdapter
        })
        //today task
//        loadTodayTaskData()
        getAllTodayTask()
    }


    private fun loadTaskData(){
        tasks = mutableListOf()
        val joins1: RealmList<JoinPerson> = realmListOf()
        joins1.add(JoinPerson().apply {
            personID = "ps1"
            name = "A"
            image = R.drawable.avt_1
        })
        joins1.add(JoinPerson().apply {
            personID = "ps2"
            name = "B"
            image = R.drawable.avt_2
        })
        joins1.add( JoinPerson().apply {
            personID = "ps3"
            name = "C"
            image = R.drawable.avt_3
        })

        val joins2:RealmList<JoinPerson> = realmListOf()
        joins2.add(JoinPerson().apply {
            personID = "ps4"
            name = "A"
            image = R.drawable.avt_3
        })
        joins2.add(JoinPerson().apply {
            personID = "ps5"
            name = "B"
            image = R.drawable.avt_2
        })
        joins2.add( JoinPerson().apply {
            personID = "ps6"
            name = "C"
            image = R.drawable.avt_4
        })

        val works1:RealmList<Work> = realmListOf()
        works1.add(Work().apply {
            workID = "wk1"
            taskID = "t01"
            workName = "Work1"
            isCheck = false
        })
        works1.add(Work().apply {
            workID = "wk2"
            taskID = "t01"
            workName = "Work2"
            isCheck = true
        })

        val works2:RealmList<Work> = realmListOf()
        works2.add(Work().apply {
            workID = "wk3"
            taskID = "t02"
            workName = "Work3"
            isCheck = true
        })
        works2.add(Work().apply {
            workID = "wk4"
            taskID = "t02"
            workName = "Work4"
            isCheck = false
        })

        tasks.add(Task().apply {
            taskID = "t01"
            taskName = "Visual"
            time = 3
            personJoin = joins1
            works = works1
            isInprogress = true
        })
        tasks.add(Task().apply {
            taskID = "t02"
            taskName = "Mind"
            time = 4
            personJoin = joins2
            works = works2
            isInprogress = false})

        presenter.writeMultiTask(tasks)
    }

    private  fun loadTodayTaskData(){
        todayTasks = mutableListOf()
        todayTasks.add(TodayTask().apply {
            todayTaskId = "td01"
            taskName = "Call"
            time = "06:00 pm"
             isComplete = false })

        todayTasks.add(TodayTask().apply {
            todayTaskId = "td02"
            taskName = "Weekly"
            time = "02:00 pm"
            isComplete = true })

        todayTasks.add(TodayTask().apply {
            todayTaskId = "td03"
            taskName = "Morning daily"
            time = "10:00 am"
            isComplete = true })

        todayTasks.add(TodayTask().apply {
            todayTaskId = "td04"
            taskName = "Home work"
            time = "7:00 pm"
            isComplete = false })

        todayTasks.add(TodayTask().apply {
            todayTaskId = "td05"
            taskName = "Game"
            time = "11:00 pm"
            isComplete = false })

        presenter.writeMultiTodayTask(todayTasks)
    }

    private  fun getAllTodayTask(){
        todayTasksAdapter = TodayTasksAdapter(presenter.readTodayTask())
        binding.recyTodayTasks.adapter = todayTasksAdapter
        binding.recyTodayTasks.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        fragBinding = null
    }

    override fun taskInProgressItemClick(position: Int) {
        val intent:Intent = Intent(activity, DetailsActivity::class.java)
//        data.putSerializable("taskData",tasks[position])
        intent.putExtra("taskData",presenter.readTask()[position])
//        startActivity(intent)
        resultLauncher.launch(intent)
//        Toast.makeText(requireContext(),presenter.readTask()[position].isInprogress.toString(),Toast.LENGTH_SHORT).show()

    }

    override fun workItemClick(position: Int) {
        TODO("Not yet implemented")
    }




}