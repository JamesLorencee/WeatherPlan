package ph.edu.dlsu.mobdeve.seril.james.weatherplan.data

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
    }

    inner class ViewHolder(private val itemBinding: ItemListBinding):
            RecyclerView.ViewHolder(itemBinding.root){

                @SuppressLint("SimpleDateFormat")
                fun bindItems (schedule: Schedule){
                    itemBinding.titleTv.text = schedule.title
                    itemBinding.locationTv.text = schedule.location
                    itemBinding.dateTv.text = schedule.getDateString()
                    itemBinding.timeTv.text = schedule.getTimeString()
                }
            }
}