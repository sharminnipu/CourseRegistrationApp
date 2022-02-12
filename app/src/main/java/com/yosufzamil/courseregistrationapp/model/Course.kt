package com.yosufzamil.courseregistrationapp.model

import com.yosufzamil.courseregistrationapp.ui.avaiable_course.AvailableCourseDetails

class Course {

    var id:Int=0
    var courseId:String?=null
    var courseName:String?=null
    var prerequisite:String?=null
    var term:String?=null
    var courseDetails:String?=null



    constructor(courseId: String?,courseName:String?,prerequisite:String?,term:String?,courseDetails: String?) {
       this.courseId=courseId
        this.courseName=courseName
        this.prerequisite=prerequisite
        this.term=term
        this.courseDetails=courseDetails
    }
    constructor(id:Int,courseId: String?,courseName:String?,prerequisite:String?,term:String?,courseDetails: String?){
        this.id=id
        this.courseId=courseId
        this.courseName=courseName
        this.prerequisite=prerequisite
        this.term=term
        this.courseDetails=courseDetails
    }

    constructor(){
    }
}