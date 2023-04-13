package ph.edu.dlsu.mobdeve.seril.james.weatherplan.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.ViewScheduleActivity
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.ScheduleDAOFirebaseImplementation
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ItemListBinding
import java.text.SimpleDateFormat

class ScheduleAdapter (private val context: Context,
                       private var scheduleList: ArrayList<Schedule>)
    : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount() = scheduleList.size

    override fun onBindViewHolder(holder: ScheduleAdapter.ViewHolder,
                                  position: Int) {
        holder.bindItems(scheduleList[position])

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ViewScheduleActivity::class.java)

            intent.putExtra("id", scheduleList[position].id)
            intent.putExtra("title", scheduleList[position].title)
            intent.putExtra("eventtype", scheduleList[position].event.toString().replace('_', ' '))
            intent.putExtra("location", scheduleList[position].location)
            intent.putExtra("time", scheduleList[position].time)
            intent.putExtra("date", scheduleList[position].date)
            intent.putExtra("notes", scheduleList[position].notes)
            intent.putExtra("position", position)

            context.startActivity(intent)
        }
    }

    inner class ViewHolder(private val itemBinding: ItemListBinding):
            RecyclerView.ViewHolder(itemBinding.root){

                @SuppressLint("SimpleDateFormat")
                fun bindItems (schedule: Schedule){
                    val datetime = SimpleDateFormat("yyyy-MM-dd HH:mm").parse("${schedule.date} ${schedule.time}")

                    itemBinding.titleTv.text = schedule.title
                    itemBinding.locationTv.text = schedule.location
                    itemBinding.dateTv.text = SimpleDateFormat("MMMM dd, yyyy").format(datetime!!)
                    itemBinding.timeTv.text = SimpleDateFormat("hh:mm a").format(datetime)
                }
            }

    fun addSchedule(schedule: Schedule) {
        val scheduleDAO = ScheduleDAOFirebaseImplementation()
        scheduleDAO.addSchedule(schedule)

        scheduleList.add(scheduleList.size, schedule)
        notifyItemInserted(scheduleList.size)
        Log.d("ADD", scheduleList.size.toString())
    }

    fun deleteSchedule(scheduleID: String) {
        var pos: Int = -1

        scheduleList.forEachIndexed {index, it ->
            if (it.id == scheduleID)
                pos = index
        }

        val scheduleDAO = ScheduleDAOFirebaseImplementation()
        scheduleDAO.removeSchedule(scheduleList[pos].id!!)

        scheduleList.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun editSchedule(schedule: Schedule, position: Int) {
        val scheduleDAO = ScheduleDAOFirebaseImplementation()
        scheduleDAO.updateSchedule(schedule)

        notifyItemChanged(position)
    }
}