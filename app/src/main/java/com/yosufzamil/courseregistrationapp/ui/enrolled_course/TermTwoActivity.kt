package com.yosufzamil.courseregistrationapp.ui.enrolled_course

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.yosufzamil.courseregistrationapp.R
import com.yosufzamil.courseregistrationapp.adapter.TermAdapter
import com.yosufzamil.courseregistrationapp.databse.DBHelper
import com.yosufzamil.courseregistrationapp.model.Course
import kotlinx.android.synthetic.main.activity_term_one.*
import kotlinx.android.synthetic.main.activity_term_two.*

class TermTwoActivity : AppCompatActivity() {

    private lateinit var adapter: TermAdapter

    private lateinit var db: DBHelper
    private var courses:List<Course> = ArrayList<Course>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_term_two)
        db = DBHelper(this)
        fetchData()
    }

    private fun fetchData(){

        courses=db.getRegisterCourse(2)

        Log.e("course size check",courses.size.toString())

        if(courses.isNotEmpty()){
            adapter = TermAdapter(courses as MutableList<Course>)
            val llm = GridLayoutManager(applicationContext, 1)
            llm.orientation = GridLayoutManager.VERTICAL
            rvEnrolledCourseTermTwo.layoutManager = llm
            rvEnrolledCourseTermTwo.adapter = adapter

            adapter.onDelete={modelList,position->
                var result=db.deleteRegisterCourse(modelList[position].courseId.toString())
                if(result){
                    modelList.removeAt(position)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this,"Delete successfully!!",Toast.LENGTH_SHORT).show()
                }

            }
        }else{
            emtyMsg2.visibility= View.VISIBLE
        }

    }
}