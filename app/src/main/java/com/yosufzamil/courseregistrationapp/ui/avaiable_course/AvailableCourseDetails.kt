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
                      var result=db.getExitedRegisterCourse(course.courseId.toString())
                      if(result){
                          Toast.makeText(this,"Sorry you can't register.this course  is already registered",Toast.LENGTH_SHORT).show()

                      }else{
                         var insert=db.addRegisterCourse(course)
                          if(insert){
                              Toast.makeText(this,"Your Registration is successfully!!",Toast.LENGTH_SHORT).show()
                          }else{
                              Toast.makeText(this,"Something is wrong",Toast.LENGTH_SHORT).show()
                          }
                      }

                  }else
                  {
                     /* if(course.prerequisite!!.contains("or")){
                          var strs = course.prerequisite!!.split(" or ")
                        // var prerequirment=strs
                         // var check :MutableList<String> =ArrayList<String>()
                        //  for (i in prerequirment){
                             // check.add(i)
                        //  }
                             // check
                       //   Log.e("testing dta",prerequirment.toString())
                          prerequirmentOne=strs[0].toString()
                        //  prerequirmentOne.split(" ")
                          Log.e("testing one",prerequirmentOne.toString())
                          prerequirmentTwo=strs[1].toString()
                          Log.e("testing two one",prerequirmentTwo)

                          Log.e("testing dta",result.toString())
                      }
                      Toast.makeText(this,prerequirmentOne,Toast.LENGTH_SHORT).show()  */

                      var result=db.getExitedRegisterCourse(course.prerequisite.toString())

                      if(result){

                          var insert= db.addRegisterCourse(course)
                          if(insert){
                              Toast.makeText(this,"Your Registration is successfully!!",Toast.LENGTH_SHORT).show()
                              courses=db.getRegisterCourse("1")
                          }else{
                              Toast.makeText(this,"YOu have already taken this course",Toast.LENGTH_SHORT).show()
                          }
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
                      var result = db.getExitedRegisterCourse(course.courseId)
                      Log.e("result",result.toString())

                      if (result) {
                          Toast.makeText(
                              this,
                              "Sorry you can't register.this course  is already registered",
                              Toast.LENGTH_SHORT
                          ).show()

                      } else {
                         var insert= db.addRegisterCourse(course)
                          if(insert){
                              Toast.makeText(
                                      this,
                                      "Your registration is successfully!!",
                                      Toast.LENGTH_SHORT
                              ).show()
                          }else{
                              Toast.makeText(
                                      this,
                                      "Something is wrong!!",
                                      Toast.LENGTH_SHORT
                              ).show()
                          }


                      }

                  } else {
                      var result = db.getExitedRegisterCourse(course.prerequisite.toString())

                      if (result) {
                         var insert=db.addRegisterCourse(course)
                          if(insert){
                              Toast.makeText(
                                      this,
                                      "Your registration is successfully!!",
                                      Toast.LENGTH_SHORT
                              ).show()
                          }else{
                              Toast.makeText(
                                      this,
                                      "You already taken this course",
                                      Toast.LENGTH_SHORT
                              ).show()
                          }
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