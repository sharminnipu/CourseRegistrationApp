package com.yosufzamil.courseregistrationapp.model

import com.yosufzamil.courseregistrationapp.ui.avaiable_course.AvailableCourseDetails

class Course {

    var id:Int=0
    var courseId:String?=null
    var courseName:String?=null
    var term:Int=0
    var prerequisiteOne:String?=null
    var prerequisiteTwo:String?=null
    var status:Int=0
    var year:Int=0
    var mandatory:Int=0
    var courseDetails:String?=null



    constructor(courseId: String?,courseName:String?,term:Int,prerequisiteOne:String?,prerequisiteTwo:String?,status:Int,year:Int,mandatory:Int,courseDetails: String?) {
       this.courseId=courseId
        this.courseName=courseName
        this.term=term
        this.prerequisiteOne=prerequisiteOne
        this.prerequisiteTwo=prerequisiteTwo
        this.status=status
        this.year=year
        this.mandatory=mandatory
        this.courseDetails=courseDetails
    }

    constructor(){
    }
}