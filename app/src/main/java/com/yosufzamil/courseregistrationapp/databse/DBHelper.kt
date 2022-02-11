package com.yosufzamil.courseregistrationapp.databse

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.yosufzamil.courseregistrationapp.model.Course

class DBHelper (context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VAR) {


    companion object {
        private val DATABASE_VAR=1
        private val DATABASE_NAME="myDbclass"

        private val TABLE_NAME="AvailableCourse"
        private val Col_ID="Id"
        private val Col_COURSE_ID="CourseId"
        private val Col_COURSE_NAME="CourseName"
        private val COL_COURSE_PREREQUISITE="Prerequisite"
        private val COL_TERM="Term"
        private val COL_COURSE_DETAILS="CourseDescription"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY:String=("CREATE TABLE $TABLE_NAME($Col_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$Col_COURSE_ID TEXT,$Col_COURSE_NAME TEXT,$COL_COURSE_PREREQUISITE TEXT,$COL_TERM TEXT,$COL_COURSE_DETAILS TEXT)")

        db!!.execSQL(CREATE_TABLE_QUERY)


    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
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
                    course.prerequisite = cursor.getString(cursor.getColumnIndex(
                        COL_COURSE_PREREQUISITE))
                    course.term = cursor.getString(cursor.getColumnIndex(COL_TERM))
                    course.courseDetails = cursor.getString(cursor.getColumnIndex(COL_COURSE_DETAILS))

                    allCourse.add(course)


                } while (cursor.moveToNext())


            }
            return allCourse

        }

    fun addCourse(course: Course) {

        val db:SQLiteDatabase=this.writableDatabase
        val values= ContentValues()

        values.put(Col_COURSE_ID,course.courseId)
        values.put(Col_COURSE_NAME,course.courseName)
        values.put(COL_COURSE_PREREQUISITE,course.prerequisite)
        values.put(COL_TERM, course.term)
        values.put(COL_COURSE_DETAILS,course.courseDetails)

        db.insert(TABLE_NAME, null,values)

        db.close()


    }


   /* fun updatePerson(course: Course): Int {

        val db:SQLiteDatabase=this.writableDatabase
        val values=ContentValues()

        values.put(Col_COURSE_ID,course.courseId)
        values.put(Col_COURSE_NAME,course.courseName)
        values.put(COL_COURSE_PREREQUISITE,course.prerequisite)
        values.put(COL_TERM, course.term)
        values.put(COL_COURSE_DETAILS,course.courseDetails)

        return  db.update(TABLE_NAME,values, "$Col_ID=?", arrayOf(course.id))

        //  db.close()


    }  */

 /*   fun deletePerson(course: Course)
    {

        val db:SQLiteDatabase=this.writableDatabase

        db.delete(TABLE_NAME, "$Col_ID=?", arrayOf(course.id))

        db.close()


    }  */

}