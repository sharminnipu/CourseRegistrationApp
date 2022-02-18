package com.yosufzamil.courseregistrationapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yosufzamil.courseregistrationapp.R
import com.yosufzamil.courseregistrationapp.model.Course
import kotlinx.android.synthetic.main.activity_available_course_details.*
import kotlinx.android.synthetic.main.registered_course_view.view.*

class TermAdapter(val dataList: MutableList<Course>): RecyclerView.Adapter<TermAdapter.FeedViewHolder>() {

    var onDelete: ((dataList:MutableList<Course>, position: Int) -> Unit)? = null


    var context: Context?=null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedViewHolder {
        context=parent.context
        return FeedViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.registered_course_view, parent, false)

        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }



    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {

        val course = dataList[position]
        holder.tvCourseName.text=course.courseName
        holder.tvCourseId.text="CourseId :  "+course.courseId


        when (course.status) {
            0 -> {
                holder.tvCoursePrerequisite.text = "Prerequisite : None"
            }
            1 -> {
                holder.tvCoursePrerequisite.text = "Prerequisite : " + course.prerequisiteOne
            }
            2 -> {
                holder.tvCoursePrerequisite.text = "Prerequisite : " + course.prerequisiteOne + " or " + course.prerequisiteTwo
            }
            3 -> {
                holder.tvCoursePrerequisite.text = "Prerequisite : " + course.prerequisiteOne + " and " + course.prerequisiteTwo
            }
        }

        if(course.mandatory==0){
            holder.btnDelete.setOnClickListener {
                onDelete?.invoke(dataList,position)
            }
        }else{
            holder.tvMandatoryMsg.visibility=View.VISIBLE
            holder.btnDelete.visibility=View.INVISIBLE
        }


        holder.tvCourseTerm.text="Term :  "+course.term.toString()


    }

    inner class FeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCourseName=view.tvRegisterCourseName
        val tvCourseId=view.tvRegisterCourseId
        val tvCoursePrerequisite=view.tvRegisterPrerequisite
        val tvCourseTerm=view.tvRegisterTerm
        val btnDelete=view.deleteCourseBtn
        val tvMandatoryMsg=view.tvMandatoryMsg

    }




}