package com.example.studentmanakotlin.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanakotlin.Event.ItemClick
import com.example.studentmanakotlin.Model.Task
import com.example.studentmanakotlin.R
import com.example.studentmanakotlin.databinding.InProgressItemBinding
import com.example.studentmanakotlin.databinding.InprogressPersonItemBinding

class TaskAdapter(private var tasks:List<Task>,private var itemClick: ItemClick) :RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ViewHolder {
        val binding:InProgressItemBinding = InProgressItemBinding.
        inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        val task:Task = tasks[position]
        holder.bindItem(task)
        holder.binding.TaskItemContainer.setOnClickListener {
            run {
                itemClick.taskInProgressItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class ViewHolder(val binding:InProgressItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindItem(task:Task){
            if(task.isInprogress){
                binding.TaskItemContainer.setBackgroundResource(R.drawable.coner_active_inprogress_background)
            }else{
                binding.TaskItemContainer.setBackgroundResource(R.drawable.coner_non_active_inprogress_background)
            }
            binding.txtTaskName.text = task.taskName
            binding.txtTaskTime.text = task.time.toString() + "h"
            binding.processBar.progress = task.calcuProcess()
            binding.txtPercent.text = task.calcuProcess().toString()+"%"
            val personJoinAdapter:PersonJoinAdapter = PersonJoinAdapter(task.personJoin)
            binding.recyPersonsJoin.adapter = personJoinAdapter
            binding.recyPersonsJoin.addItemDecoration(StackItem(40))
        }


    }



}