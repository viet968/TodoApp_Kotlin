package com.example.studentmanakotlin.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanakotlin.Model.JoinPerson
import com.example.studentmanakotlin.databinding.InprogressPersonItemBinding

class PersonJoinAdapter(var persons:List<JoinPerson>?) : RecyclerView.Adapter<PersonJoinAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:InprogressPersonItemBinding = InprogressPersonItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(persons){
                with(persons!![position]){
                    binding.imgPersonInProgress.setImageResource(this.image)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return persons!!.size
    }

    class ViewHolder(var binding: InprogressPersonItemBinding) : RecyclerView.ViewHolder(binding.root)
}