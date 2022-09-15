package com.example.studentmanakotlin.View

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanakotlin.Adapter.WorkAdapter
import com.example.studentmanakotlin.Adapter.PersonJoinAdapter
import com.example.studentmanakotlin.Adapter.StackItem
import com.example.studentmanakotlin.Event.ItemClick
import com.example.studentmanakotlin.Model.JoinPerson
import com.example.studentmanakotlin.Model.Task
import com.example.studentmanakotlin.Model.Work
import com.example.studentmanakotlin.Presenter.IPresenter
import com.example.studentmanakotlin.databinding.ActivityDetailsBinding
import io.realm.kotlin.ext.toRealmList

class DetailsActivity : AppCompatActivity(),ItemClick{
    private var detailsBinding:ActivityDetailsBinding?=null
    //view
    private lateinit var joinPersonDetailsRecy :RecyclerView
    private lateinit var checkListRecy:RecyclerView
    //adapter
    private lateinit var joinPersonAdapter:PersonJoinAdapter
    private lateinit var checkListAdapter: WorkAdapter
    //presenter
    private val presenter:IPresenter = MainActivity.presenter
    //data
    private lateinit var getWorks:MutableList<Work>
    private lateinit var getTask:Task


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsBinding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(detailsBinding?.root)

        val width:Int = getWidth()

        //param
        val joinPersonDetailsViewParams= detailsBinding?.joinPersonDetailsView?.layoutParams
        val dateTimeViewParams= detailsBinding?.dateTimeDetailsView?.layoutParams

        val btDoneParam = detailsBinding?.btDoneTask?.layoutParams
        val btSetIsInProgressParam = detailsBinding?.btSetIsInprogress?.layoutParams

        joinPersonDetailsViewParams?.width = (width/ 2.3).toInt()
        dateTimeViewParams?.width = (width/ 2.3).toInt()

        btDoneParam?.width = (width/ 2.3).toInt()
        btSetIsInProgressParam?.width = (width/ 2.3).toInt()

        getTask = intent?.getParcelableExtra<Task>("taskData") as Task
        getWorks = presenter.readWorksOfTask(getTask)

        anhXa(getTask)
        detailsBinding?.txtTaskNameDetails?.text = getTask.taskName
//        Toast.makeText(this,presenter.readWorksOfTask(getTask).size.toString(),Toast.LENGTH_SHORT).show()
        detailsBinding?.imgBtBack?.setOnClickListener {
            val i :Intent = Intent()
            getTask.works = reloadWorks().toRealmList()
            i.putExtra("editedTask",getTask)
            setResult(Activity.RESULT_OK,i)
            finish()
        }

        detailsBinding?.btDoneTask?.setOnClickListener {
            Toast.makeText(this,reloadWorks().toString(),Toast.LENGTH_SHORT).show()
        }

    }

    private fun anhXa(task: Task){
        joinPersonDetailsRecy = detailsBinding!!.joinPersonRecyDetails
        joinPersonAdapter = PersonJoinAdapter(loadPersonsData(task))

        joinPersonDetailsRecy.adapter = joinPersonAdapter
        joinPersonDetailsRecy.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        joinPersonDetailsRecy.addItemDecoration(StackItem(40))

        checkListAdapter = WorkAdapter(getWorks,this)
        detailsBinding!!.checkListRecy.adapter = checkListAdapter
    }

    private fun loadPersonsData(task: Task): List<JoinPerson> {
       return presenter.readJoinPersonOfTask(task)
    }

    override fun onDestroy() {
        super.onDestroy()
        detailsBinding = null
    }

    private fun getWidth():Int{
        return this.resources.displayMetrics.widthPixels
    }

    override fun taskInProgressItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun workItemClick(position: Int) {
//        Toast.makeText(this,getWorks[position].workName,Toast.LENGTH_SHORT).show()
        presenter.updateIsCheckedForWork(getWorks[position])

    }

    fun reloadWorks():MutableList<Work>{
       return presenter.readWorksOfTask(getTask)
    }




}