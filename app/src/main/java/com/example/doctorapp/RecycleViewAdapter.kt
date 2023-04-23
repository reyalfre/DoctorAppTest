package com.example.doctorapp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Date


class RecycleViewAdapter(private val personList: ArrayList<PersonModel>) :
    RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_row_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPerson = personList[position]
        holder.PersonName.text = currentPerson.iname
        holder.Date.text = currentPerson.idate
        holder.Phone.text = currentPerson.inumber
        holder.gender.text = currentPerson.igender
        holder.Desc.text = currentPerson.idesc
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val PersonName: TextView = itemView.findViewById(R.id.textView4)
        val Date: TextView = itemView.findViewById(R.id.textView5)
        val Phone: TextView = itemView.findViewById(R.id.textView6)
        val gender: TextView = itemView.findViewById(R.id.textView8)
        val Desc: TextView = itemView.findViewById(R.id.textView7)


    }

}





//var PersonName: TextView = itemView.findViewById(R.id.textView4)
//var Contact: TextView = itemView.findViewById(R.id.textView5)
//var gender: TextView = itemView.findViewById(R.id.textView6)
//var Desc: TextView = itemView.findViewById(R.id.textView7)
//var DOB: TextView = itemView.findViewById(R.id.textView8)

