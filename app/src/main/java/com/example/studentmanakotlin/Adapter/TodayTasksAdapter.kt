package com.example.studentmanakotlin.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanakotlin.Model.TodayTask
import com.example.studentmanakotlin.Presenter.IPresenter
import com.example.studentmanakotlin.View.MainActivity
import com.example.studentmanakotlin.databinding.TodayTaskItemBinding

class TodayTasksAdapter(private var tasks:MutableList<TodayTask>): RecyclerView.Adapter<TodayTasksAdapter.ViewHolder>(){

    val presenter:IPresenter = MainActivity.presenter

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodayTasksAdapter.ViewHolder {
       val binding:TodayTaskItemBinding = TodayTaskItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodayTasksAdapter.ViewHolder, position: Int) {
        val getTodayTask = tasks[position]
        holder.bindItem(getTodayTask)
        holder.binding.cbIsComplete.setOnClickListener {
            presenter.updateIsCompleteStateForTodayTask(getTodayTask)
        }
    }

    override fun getItemCount(): Int = tasks.size



    class ViewHolder(var binding:TodayTaskItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bindItem(todayTask:TodayTask){
            binding.txtTodayTaskName.text = todayTask.taskName
            binding.txtTodayTaskTime.text = todayTask.time
            binding.cbIsComplete.isChecked = todayTask.isComplete
        }
    }

}