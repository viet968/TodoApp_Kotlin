package com.example.studentmanakotlin.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanakotlin.Event.ItemClick
import com.example.studentmanakotlin.Model.Work
import com.example.studentmanakotlin.databinding.WorkItemBinding

class WorkAdapter(private var works:MutableList<Work>?,private var itemClick: ItemClick) : RecyclerView.Adapter<WorkAdapter.ViewHolder>(){

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): WorkAdapter.ViewHolder {
            val binding: WorkItemBinding = WorkItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: WorkAdapter.ViewHolder, position: Int) {
            val work = works!![position]
            holder.bindItem(work)
            holder.binding.cbIsCheck.setOnClickListener {
                itemClick.workItemClick(position)
            }
        }

        override fun getItemCount(): Int = works!!.size


        class ViewHolder(var binding: WorkItemBinding): RecyclerView.ViewHolder(binding.root)
        {
            fun bindItem(work:Work){
                binding.txtWorkName.text = work.workName
                binding.cbIsCheck.isChecked = work.isCheck
            }
        }
}