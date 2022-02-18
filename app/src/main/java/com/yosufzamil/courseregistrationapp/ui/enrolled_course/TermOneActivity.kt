package com.yosufzamil.courseregistrationapp.ui.enrolled_course

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.yosufzamil.courseregistrationapp.R
import com.yosufzamil.courseregistrationapp.adapter.AvailableCourseAdapter
import com.yosufzamil.courseregistrationapp.adapter.TermAdapter
import com.yosufzamil.courseregistrationapp.databse.DBHelper
import com.yosufzamil.courseregistrationapp.model.Course
import com.yosufzamil.courseregistrationapp.ui.avaiable_course.AvailableCourseDetails
import com.yosufzamil.courseregistrationapp.utils.AppConstant
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_term_one.*

class TermOneActivity : AppCompatActivity() {
    private lateinit var adapter: TermAdapter

    private lateinit var db: DBHelper
    private var courses:List<Course> = ArrayList<Course>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_term_one)
        db = DBHelper(this)
        fetchData()
    }

    private fun fetchData(){

        courses=db.getRegisterCourse(1)

        Log.e("course size check",courses.size.toString())

        if(courses.isNotEmpty()){
            adapter = TermAdapter(courses as MutableList<Course>)
            val llm = GridLayoutManager(applicationContext, 1)
            llm.orientation = GridLayoutManager.VERTICAL
            rvEnrolledCourseTermOne.layoutManager = llm
            rvEnrolledCourseTermOne.adapter = adapter

            adapter.onDelete={modelList,position->
                var result=db.getExitedInPrerequisiteColumn(modelList[position].courseId.toString())

                Log.e("result checking", result.courseId.toString())
                if(result!=null){
                    Log.e("dlete option","helloone")
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Warning!!")
                    builder.setMessage("If you delete this course so remove will be also ${result.courseId.toString()}")

                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                       var prerequisiteCourseDelete= db.deleteRegisterCourse(result.courseId.toString())
                        var prerequisiteCourseDeleteOne= db.deleteRegisterCourse(modelList[position].courseId.toString())
                        if(prerequisiteCourseDelete && prerequisiteCourseDeleteOne){
                            modelList.removeAt(position)
                            adapter.notifyDataSetChanged()
                            Toast.makeText(applicationContext,
                                    "Delete successfully!!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    builder.setNegativeButton(android.R.string.no) { dialog, which ->
                        Toast.makeText(applicationContext,
                                android.R.string.no, Toast.LENGTH_SHORT).show()
                    }
                    builder.show()
                }else{
                    Log.e("dlete option","hello")
                    var prerequisiteCourseDelete= db.deleteRegisterCourse(modelList[position].courseId.toString())
                    modelList.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            }
        }else{
            emtyMsg.visibility=View.VISIBLE
        }

    }


}