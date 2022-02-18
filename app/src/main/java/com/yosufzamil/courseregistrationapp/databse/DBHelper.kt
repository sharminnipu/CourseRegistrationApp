package com.yosufzamil.courseregistrationapp.databse

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.yosufzamil.courseregistrationapp.model.Course
import kotlinx.android.synthetic.main.activity_term_one.view.*

class DBHelper (context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VAR) {


    companion object {
        private val DATABASE_VAR=1
        private val DATABASE_NAME="myDbclass"

        private val TABLE_NAME="AvailableCourse"
        private val TABLE_REGIDTER_COURSE_NAME="RegisterCourse"

        private val Col_ID="Id"
        private val Col_COURSE_ID="CourseId"
        private val Col_COURSE_NAME="CourseName"
        private val COL_COURSE_PREREQUISITE_ONE="Prerequisite_One"
        private val COL_COURSE_PREREQUISITE_TWO="Prerequisite_Two"
        private val COL_COURSE_STATUS="Status"
        private val COL_TERM="Term"
        private val COL_COURSE_DETAILS="CourseDescription"
        private val COL_YEAR="Year"
        private val COL_MANDATORY="Mandatory"
        private val a="a"
        private val b="b"


    }



    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY:String=("CREATE TABLE $TABLE_NAME($Col_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$Col_COURSE_ID TEXT,$Col_COURSE_NAME TEXT,$COL_TERM TEXT,$COL_COURSE_PREREQUISITE_ONE TEXT," +
                "$COL_COURSE_PREREQUISITE_TWO TEXT,$COL_COURSE_STATUS INTEGER,$COL_YEAR INTEGER,$COL_MANDATORY INTEGER,$COL_COURSE_DETAILS TEXT)")

        val CREATE_TABLE_REGISTER:String=("CREATE TABLE $TABLE_REGIDTER_COURSE_NAME($Col_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$Col_COURSE_ID TEXT,$Col_COURSE_NAME TEXT,$COL_TERM TEXT,$COL_COURSE_PREREQUISITE_ONE TEXT," +
                "$COL_COURSE_PREREQUISITE_TWO TEXT,$COL_COURSE_STATUS INTEGER,$COL_YEAR INTEGER,$COL_MANDATORY INTEGER,$COL_COURSE_DETAILS TEXT)")

        db!!.execSQL(CREATE_TABLE_QUERY)
        db!!.execSQL(CREATE_TABLE_REGISTER)


    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_REGIDTER_COURSE_NAME")
        onCreate(db!!)
    }

    @SuppressLint("Range")
    fun getRegisterCourse(term: Int):List<Course>{

            val  registerCourse=ArrayList<Course>()
            val selectQuery="SELECT * FROM $TABLE_REGIDTER_COURSE_NAME WHERE $COL_TERM=$term AND $COL_YEAR=2 "
            val db: SQLiteDatabase =this.writableDatabase
            val cursor: Cursor =db.rawQuery(selectQuery, null)

            if(cursor.moveToFirst()) {

                do {
                    val course = Course()
                    course.id=cursor.getInt(cursor.getColumnIndex(Col_ID))
                    course.courseId = cursor.getString(cursor.getColumnIndex(Col_COURSE_ID))
                    course.courseName = cursor.getString(cursor.getColumnIndex(Col_COURSE_NAME))
                    course.term = cursor.getInt(cursor.getColumnIndex(COL_TERM))
                    course.prerequisiteOne = cursor.getString(cursor.getColumnIndex(COL_COURSE_PREREQUISITE_ONE))
                    course.prerequisiteTwo = cursor.getString(cursor.getColumnIndex(COL_COURSE_PREREQUISITE_TWO))
                    course.status=cursor.getInt(cursor.getColumnIndex(COL_COURSE_STATUS))
                    course.year=cursor.getInt(cursor.getColumnIndex(COL_YEAR))
                    course.mandatory=cursor.getInt(cursor.getColumnIndex(COL_MANDATORY))
                    course.courseDetails = cursor.getString(cursor.getColumnIndex(COL_COURSE_DETAILS))

                    registerCourse.add(course)

                } while (cursor.moveToNext())
            }
             return registerCourse
        }
    fun getExitedRegisterCourse(courseId:String?): Boolean {
             var flag = false
             val selectQuery="SELECT * FROM $TABLE_REGIDTER_COURSE_NAME WHERE $Col_COURSE_ID=? "
             val db: SQLiteDatabase =this.writableDatabase
             val cursor: Cursor =db.rawQuery(selectQuery, arrayOf(courseId))
             flag = cursor.moveToFirst() && cursor!=null && cursor.count>0

        return flag
    }
    @SuppressLint("Range")
    fun getExitedInPrerequisiteColumn(courseId:String?):Course {

        var course=Course()
        val selectQuery="SELECT * FROM $TABLE_REGIDTER_COURSE_NAME WHERE $COL_COURSE_PREREQUISITE_ONE=? OR $COL_COURSE_PREREQUISITE_TWO=?"
        val db: SQLiteDatabase =this.writableDatabase
        val cursor: Cursor =db.rawQuery(selectQuery, arrayOf(courseId,courseId))
        if(cursor.moveToFirst() && cursor!=null && cursor.count>0){
            course.id=cursor.getInt(cursor.getColumnIndex(Col_ID))
            course.courseId = cursor.getString(cursor.getColumnIndex(Col_COURSE_ID))
            course.courseName = cursor.getString(cursor.getColumnIndex(Col_COURSE_NAME))
            course.term = cursor.getInt(cursor.getColumnIndex(COL_TERM))
            course.prerequisiteOne = cursor.getString(cursor.getColumnIndex(COL_COURSE_PREREQUISITE_ONE))
            course.prerequisiteTwo = cursor.getString(cursor.getColumnIndex(COL_COURSE_PREREQUISITE_TWO))
            course.status=cursor.getInt(cursor.getColumnIndex(COL_COURSE_STATUS))
            course.year=cursor.getInt(cursor.getColumnIndex(COL_YEAR))
            course.mandatory=cursor.getInt(cursor.getColumnIndex(COL_MANDATORY))
            course.courseDetails = cursor.getString(cursor.getColumnIndex(COL_COURSE_DETAILS))
            cursor.close()

        }else{
            Log.e("check for data",course.courseId.toString())
        }
        return course

    }
    @SuppressLint("Range")
    fun getPrerequisiteCompletedCourseExit(courseIdOne:String?, courseIdTwo: String?, status: Int): Boolean {
        var flag = false
            //1=Single ,2=or option 3=and option
        when (status) {
            1 -> {
                val selectQuery="SELECT * FROM $TABLE_REGIDTER_COURSE_NAME WHERE $Col_COURSE_ID=? "
                val db: SQLiteDatabase =this.writableDatabase
                val cursor: Cursor =db.rawQuery(selectQuery, arrayOf(courseIdOne))

                flag = cursor.moveToFirst() && cursor!=null && cursor.count >0

            }
            2 -> {
                val selectQuery="SELECT * FROM $TABLE_REGIDTER_COURSE_NAME WHERE $Col_COURSE_ID=? OR $Col_COURSE_ID=?"
                val db: SQLiteDatabase =this.writableDatabase
                val cursor: Cursor =db.rawQuery(selectQuery, arrayOf(courseIdOne,courseIdTwo))
                flag = cursor.moveToFirst() && cursor!=null && cursor.count >0
            }
            3 -> {
                val  registerCourse=ArrayList<Course>()
                val selectQuery="SELECT * FROM $TABLE_REGIDTER_COURSE_NAME WHERE $Col_COURSE_ID IN('$courseIdOne','$courseIdTwo')"
                val db: SQLiteDatabase =this.writableDatabase
                val cursor: Cursor =db.rawQuery(selectQuery, null)
                if(cursor.moveToFirst()) {
                    do {
                        val course = Course()
                        course.id=cursor.getInt(cursor.getColumnIndex(Col_ID))
                        course.courseId = cursor.getString(cursor.getColumnIndex(Col_COURSE_ID))
                        course.courseName = cursor.getString(cursor.getColumnIndex(Col_COURSE_NAME))
                        course.term = cursor.getInt(cursor.getColumnIndex(COL_TERM))
                        course.prerequisiteOne = cursor.getString(cursor.getColumnIndex(COL_COURSE_PREREQUISITE_ONE))
                        course.prerequisiteTwo = cursor.getString(cursor.getColumnIndex(COL_COURSE_PREREQUISITE_TWO))
                        course.status=cursor.getInt(cursor.getColumnIndex(COL_COURSE_STATUS))
                        course.year=cursor.getInt(cursor.getColumnIndex(COL_YEAR))
                        course.mandatory=cursor.getInt(cursor.getColumnIndex(COL_MANDATORY))
                        course.courseDetails = cursor.getString(cursor.getColumnIndex(COL_COURSE_DETAILS))

                        registerCourse.add(course)
                    } while (cursor.moveToNext())
                }

                if(registerCourse.size==2){
                    flag=true
                    Log.e("course checking",registerCourse[0].courseId.toString())
                    Log.e("course checking2",registerCourse[1].courseId.toString())
                }
                Log.e("and",flag.toString())
            }
        }


        return flag
    }
    fun addRegisterCourse(course: Course):Boolean {
        var flag = false
        val db:SQLiteDatabase=this.writableDatabase
        val values= ContentValues()
        values.put(Col_COURSE_ID,course.courseId)
        values.put(Col_COURSE_NAME,course.courseName)
        values.put(COL_TERM, course.term)
        values.put(COL_COURSE_PREREQUISITE_ONE,course.prerequisiteOne)
        values.put(COL_COURSE_PREREQUISITE_TWO,course.prerequisiteTwo)
        values.put(COL_COURSE_STATUS,course.status)
        values.put(COL_YEAR, course.year)
        values.put(COL_MANDATORY, course.mandatory)
        values.put(COL_COURSE_DETAILS,course.courseDetails)
       var result= db.insert(TABLE_REGIDTER_COURSE_NAME, null,values)

        Log.e("database",result.toString())

        flag = result>0
        db.close()
       return flag
    }
    fun deleteRegisterCourse(courseId:String):Boolean
    {
        var flag=false
        val db:SQLiteDatabase=this.writableDatabase
       var result= db.delete(TABLE_REGIDTER_COURSE_NAME, "$Col_COURSE_ID=?", arrayOf(courseId))
        Log.e("result",result.toString())

        flag = result>0
        db.close()
        return flag
    }

    val availableAllCourse:List<Course>
        @SuppressLint("Range")
        get(){

            val  allCourse=ArrayList<Course>()
            val selectQuery="SELECT * FROM $TABLE_NAME"
            val db: SQLiteDatabase =this.writableDatabase
            val cursor: Cursor =db.rawQuery(selectQuery,null)

            if(cursor.moveToFirst()) {

                do {
                    val course = Course()
                    course.id=cursor.getInt(cursor.getColumnIndex(Col_ID))
                    course.courseId = cursor.getString(cursor.getColumnIndex(Col_COURSE_ID))
                    course.courseName = cursor.getString(cursor.getColumnIndex(Col_COURSE_NAME))
                    course.term = cursor.getInt(cursor.getColumnIndex(COL_TERM))
                    course.prerequisiteOne = cursor.getString(cursor.getColumnIndex(COL_COURSE_PREREQUISITE_ONE))
                    course.prerequisiteTwo = cursor.getString(cursor.getColumnIndex(COL_COURSE_PREREQUISITE_TWO))
                    course.status=cursor.getInt(cursor.getColumnIndex(COL_COURSE_STATUS))
                    course.year=cursor.getInt(cursor.getColumnIndex(COL_YEAR))
                    course.mandatory=cursor.getInt(cursor.getColumnIndex(COL_MANDATORY))
                    course.courseDetails = cursor.getString(cursor.getColumnIndex(COL_COURSE_DETAILS))

                    allCourse.add(course)

                } while (cursor.moveToNext())

            }
            return allCourse

        }
    fun addCourse(courseId:String,courseName:String,term:Int,prerequisiteOne:String,prerequisiteTwo:String,status:Int,year:Int,mandatory:Int,courseDetails:String) {

        val db:SQLiteDatabase=this.writableDatabase
        val values= ContentValues()

        values.put(Col_COURSE_ID,courseId)
        values.put(Col_COURSE_NAME,courseName)
        values.put(COL_TERM, term)
        values.put(COL_COURSE_PREREQUISITE_ONE,prerequisiteOne)
        values.put(COL_COURSE_PREREQUISITE_TWO,prerequisiteTwo)
        values.put(COL_COURSE_STATUS,status)
        values.put(COL_YEAR, year)
        values.put(COL_MANDATORY, mandatory)
        values.put(COL_COURSE_DETAILS,courseDetails)
        db.insert(TABLE_NAME, null,values)

        db.close()
    }
}