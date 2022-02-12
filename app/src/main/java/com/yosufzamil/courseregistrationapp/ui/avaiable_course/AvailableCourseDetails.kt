package com.yosufzamil.courseregistrationapp.ui.avaiable_course

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yosufzamil.courseregistrationapp.R
import com.yosufzamil.courseregistrationapp.databse.DBHelper
import com.yosufzamil.courseregistrationapp.model.Course
import com.yosufzamil.courseregistrationapp.utils.AppConstant
import com.yosufzamil.courseregistrationapp.utils.AppConstant.courseDetails
import kotlinx.android.synthetic.main.activity_available_course_details.*

class AvailableCourseDetails : AppCompatActivity() {

    lateinit var course:Course
    private var courses:List<Course> = ArrayList<Course>()
    private lateinit var db: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_course_details)
        initState()
    }


    private fun initState(){
      course=courseDetails!!
        db = DBHelper(this)
        val strs = "name or 2017".split("or")
        Log.e("split",strs.toString())
      tvAvailableCourseName.text=course.courseName
      tvAvailableCourseID.text="CourseId : "+course.courseId
      tvPrerequisite.text="CourseId : "+course.prerequisite
      tvTerm.text=course.term
      tvCourseDescription.text=course.courseDetails

      btnRegister.setOnClickListener {
          val course=Course(
              course.id,
              course.courseId,
              course.courseName,
              course.prerequisite,
              course.term,
              course.courseDetails)

          if(course.term=="1"){
              courses=db.getRegisterCourse("1")
              if(courses.size==3){

                  Toast.makeText(this,"Sorry you can't register for term 1.Already register 3 course",Toast.LENGTH_SHORT).show()

              }else{
                  if(course.prerequisite=="None"){
                      var result=db.exitedRegisterCourse(course.courseId)
                      if(result){
                          Toast.makeText(this,"Sorry you can't register.this course  is already registered",Toast.LENGTH_SHORT).show()

                      }else{
                          db.addRegisterCourse(course)
                      }

                  }else
                  {
                      var result=db.exitedRegisterCourse(course.prerequisite)

                      if(result){
                          db.addRegisterCourse(course)
                      }else{
                          Toast.makeText(this,"you have take first prerequisite course then you can register for this course",Toast.LENGTH_SHORT).show()

                      }

                  }

              }


          }
          else if(course.term=="2") {
              courses = db.getRegisterCourse("2")
              if (courses.size == 3) {

                  Toast.makeText(
                      this,
                      "Sorry you can't register for term 1.Already register 3 course",
                      Toast.LENGTH_SHORT
                  ).show()

              } else {
                  if (course.prerequisite == "None") {
                      var result = db.exitedRegisterCourse(course.courseId)

                     /* if (result) {
                          Toast.makeText(
                              this,
                              "Sorry you can't register.this course  is already registered",
                              Toast.LENGTH_SHORT
                          ).show()

                      } else {
                          db.addRegisterCourse(course)
                      }  */

                  } else {
                      var result = db.exitedRegisterCourse(course.prerequisite)

                      if (result) {
                          db.addRegisterCourse(course)
                      } else {
                          Toast.makeText(
                              this,
                              "you have take first prerequisite course then you can register for this course",
                              Toast.LENGTH_SHORT
                          ).show()

                      }

                  }

              }
          }







      }

    }
}