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
        completedCourseLastYear()
        mandatoryCourseFor2ndYear()
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
        //status 0=no prerequisite,1=single prerequisite,2=or condition,3=and condition
        //mandatory 0=no,1=yes


        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        if(!sharedPreference.contains("insertedInDB")){

            db.addCourse("CS128","Introduction to Coding",2,"None","None",0,2,0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS161","Introduction to Programming",1,"None","None",0,1,0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS162","Programming and Data Structure",2,"CS161","None",1,1,0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS215","Social Issues",2,"None","None",0,2,0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS225","Health Analytic",1,"CS161","cs128",2,2,0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS223","Data Science",1,"CS161","None",1,2,0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS255","Advanced Data Structure",1,"CS162","None",1,2,1,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS263","Computer Architecture and Organization",2,"CS255","None",1,2,1,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS275","Database Management Systems",2,"CS162","None",1,2,0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS277","Discrete Structure",1,"Math101","CS162",2,2,0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS340","Evolutionary Computation",1,"None","None",0,2,0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS356","Theory of Computing",1,"CS255","CS277",3,2,0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS356","Theory of Computing",2,"CS255","CS277",3,2,0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS364","Mobile App Development",1,"CS162","None",1,2,0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS368","Data Communications and Networking",1,"CS255","None",1,2,0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addCourse("CS375","Operating Systems",1,"CS255","None",1,2,0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")


        }

            var editor = sharedPreference.edit()
            editor.putBoolean("insertedInDB", true)
            editor.commit()
        }
    private fun completedCourseLastYear(){

        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        if(!sharedPreference.contains("insertedCompletedCourseInDB")) {
            val courseOne = Course(
                    "CS161", "Introduction to Programming", 1, "None", "None", 0, 1, 0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            val courseTwo = Course(
                    "CS162", "Programming and Data Structure", 2, "CS161", "None", 1, 1, 0,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
          db.addRegisterCourse(courseOne)
          //  if(result){
              //  Toast.makeText(this,"yes register one",Toast.LENGTH_SHORT).show()
          //  }
            db.addRegisterCourse(courseTwo)
            var editor = sharedPreference.edit()
            editor.putBoolean("insertedCompletedCourseInDB", true)
            editor.commit()
        }
    }
    private fun mandatoryCourseFor2ndYear(){
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        if(!sharedPreference.contains("insertedMandatoryCourseInDB")) {
            val courseOne = Course(
                    "CS255", "Advanced Data Structure", 1, "CS162", "None", 1, 2, 1,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            val courseTwo = Course(
                    "CS263", "Computer Architecture and Organization", 2, "CS255", "None", 1, 2, 1,
                    "Coding is telling a computer what to do, in a way that, with a bit of translation, it can understand. You give computers instructions in what is known as 'code', in a similar way to how you might have a recipe for how to cook something.")
            db.addRegisterCourse(courseOne)
            db.addRegisterCourse(courseTwo)
            var editor = sharedPreference.edit()
            editor.putBoolean("insertedMandatoryCourseInDB", true)
            editor.commit()
        }
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

