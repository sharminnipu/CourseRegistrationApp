package com.yosufzamil.courseregistrationapp.ui.avaiable_course

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.GridLayoutManager
import com.yosufzamil.courseregistrationapp.R
import com.yosufzamil.courseregistrationapp.adapter.AvailableCourseAdapter
import com.yosufzamil.courseregistrationapp.databse.DBHelper
import com.yosufzamil.courseregistrationapp.model.Course
import com.yosufzamil.courseregistrationapp.ui.enrolled_course.TermOneActivity
import com.yosufzamil.courseregistrationapp.ui.enrolled_course.TermTwoActivity
import com.yosufzamil.courseregistrationapp.utils.AppConstant.courseDetails
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: AvailableCourseAdapter

    private lateinit var db: DBHelper
    private var courses:List<Course> = ArrayList<Course>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = DBHelper(this)
        insertedAvailableCoursesInDb()
        fetchData()
        selectTerm()
    }

    private fun selectTerm(){
        selectTerm.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this,selectTerm)
            popupMenu.menuInflater.inflate(R.menu.select_course_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.item1->{
                        val intent = Intent(applicationContext,TermOneActivity::class.java)
                        startActivity(intent)

                    }
                    R.id.item2 -> {
                        val intent = Intent(applicationContext, TermTwoActivity::class.java)
                        startActivity(intent)

                    }

                }
                true
            })
            popupMenu.show()
        }

    }
    private fun insertedAvailableCoursesInDb(){
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        if(!sharedPreference.contains("insertedInDB")){
            db.addCourse("CS128","Introduction to Coding","None","2","Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS161","Introduction to Programming","None","1","Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS162","Programming and Data Structure","CS161","2","Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS225","Health Analytic","CS161 or CS128","1","Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
        }

            var editor = sharedPreference.edit()
            editor.putBoolean("insertedInDB", true)
            editor.commit()
        }
    private fun fetchData(){

    courses=db.availableAllCourse
        adapter = AvailableCourseAdapter(courses)
    val llm = GridLayoutManager(applicationContext, 1)
    llm.orientation = GridLayoutManager.VERTICAL
    rvAvailableCourse.layoutManager = llm
    rvAvailableCourse.adapter = adapter
        adapter.onItemAction = {model,positon ->
            courseDetails=model
            val intent= Intent(applicationContext,AvailableCourseDetails::class.java)
            startActivity(intent) }

        adapter.onListViewAction={model, position ->
            courseDetails=model
            val intent= Intent(applicationContext,AvailableCourseDetails::class.java)
            startActivity(intent)
        }
    }
}

