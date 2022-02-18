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
        when (course.status) {
            0 -> {
                tvPrerequisite.text="None"
            }
            1 -> {
                tvPrerequisite.text="CourseId : "+course.prerequisiteOne
            }
            2 -> {
                tvPrerequisite.text="CourseId : "+course.prerequisiteOne+" or "+course.prerequisiteTwo
            }
            3 -> {
                tvPrerequisite.text="CourseId : "+course.prerequisiteOne+" and "+course.prerequisiteTwo
            }
        }

      tvTerm.text=course.term.toString()
      tvCourseDescription.text=course.courseDetails

      btnRegister.setOnClickListener {
            if(course.year==1){
               Toast.makeText(this,"You have already taken this course",Toast.LENGTH_LONG).show()
            }else{
                courses=db.getRegisterCourse(course.term)
                if(courses.size==3){
                    Toast.makeText(this,"Sorry you can't register.Already registered 3 courses",Toast.LENGTH_SHORT).show()
                }else{
                    var result=db.getExitedRegisterCourse(course.courseId)
                    if(result){
                        Toast.makeText(this,"You have already taken this course",Toast.LENGTH_LONG).show()
                    }else{
                        if(course.status==0){
                            var result= db.addRegisterCourse(course)
                            if (result){
                                Toast.makeText(this,"Your registration is successfully!!",Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(this,"Your registration isn't completed!!",Toast.LENGTH_LONG).show()
                            }
                        }else{
                            var result=db.getPrerequisiteCompletedCourseExit(course.prerequisiteOne,course.prerequisiteTwo,course.status)
                            if(result){
                               var result= db.addRegisterCourse(course)
                                if (result){
                                    Toast.makeText(this,"You registration is successfully!!",Toast.LENGTH_LONG).show()
                                }else{
                                    Toast.makeText(this,"You registration isn't completed!!",Toast.LENGTH_LONG).show()
                                }
                            }else{
                                Toast.makeText(this,"You have to completed first prerequisite",Toast.LENGTH_LONG).show()
                            }
                        }

                    }
                }

            }
      }

    }
}