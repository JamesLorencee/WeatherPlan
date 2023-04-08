package ph.edu.dlsu.mobdeve.seril.james.weatherplan.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.ViewScheduleActivity
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.ScheduleDAOFFirebaseImplementation
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ItemListBinding

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
                    itemBinding.titleTv.text = schedule.title
                    itemBinding.locationTv.text = schedule.location
                    itemBinding.dateTv.text = schedule.date
                    itemBinding.timeTv.text = schedule.time
                }
            }

    fun addSchedule(schedule: Schedule) {
        val scheduleDAO = ScheduleDAOFFirebaseImplementation()
        scheduleDAO.addSchedule(schedule)

        scheduleList.add(scheduleList.size, schedule)
        notifyItemInserted(scheduleList.size)
    }

    fun deleteSchedule(position: Int) {
        val scheduleDAO = ScheduleDAOFFirebaseImplementation()
        scheduleDAO.removeSchedule(scheduleList[position].id!!)

        scheduleList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun editSchedule(schedule: Schedule, position: Int) {
        val scheduleDAO = ScheduleDAOFFirebaseImplementation()
        scheduleDAO.updateSchedule(schedule)

        notifyItemChanged(position)
    }
}