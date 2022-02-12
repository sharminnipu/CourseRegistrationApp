package com.yosufzamil.courseregistrationapp.databse

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.yosufzamil.courseregistrationapp.model.Course

class DBHelper (context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VAR) {


    companion object {
        private val DATABASE_VAR=1
        private val DATABASE_NAME="myDbclass"

        private val TABLE_NAME="AvailableCourse"
        private val TABLE_REGIDTER_COURSE_NAME="RegisterCourse"

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

        val CREATE_TABLE_REGISTER:String=("CREATE TABLE $TABLE_REGIDTER_COURSE_NAME($Col_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$Col_COURSE_ID TEXT,$Col_COURSE_NAME TEXT,$COL_COURSE_PREREQUISITE TEXT,$COL_TERM TEXT,$COL_COURSE_DETAILS TEXT)")

        db!!.execSQL(CREATE_TABLE_QUERY)
        db!!.execSQL(CREATE_TABLE_REGISTER)


    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

       // val DROP_TABLE_DEPT = "DROP TABLE IF EXISTS $TABLE_NAME"
       /// val DROP_TABLE_ITEM = "DROP TABLE IF EXISTS $TABLE_REGIDTER_COURSE_NAME"
      //  db!!.execSQL(DROP_TABLE_DEPT + DROP_TABLE_ITEM)

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_REGIDTER_COURSE_NAME")
        onCreate(db!!)
    }






    @SuppressLint("Range")
    fun getRegisterCourse(term: String):List<Course>{

            val  registerCourse=ArrayList<Course>()
            val selectQuery="SELECT * FROM $TABLE_REGIDTER_COURSE_NAME WHERE $COL_TERM=$term"
            val db: SQLiteDatabase =this.writableDatabase
            val cursor: Cursor =db.rawQuery(selectQuery, null)

            if(cursor.moveToFirst()) {

                do {
                    val course = Course()
                    course.id = cursor.getInt(cursor.getColumnIndex(Col_ID))
                    course.courseId = cursor.getString(cursor.getColumnIndex(Col_COURSE_ID))
                    course.courseName = cursor.getString(cursor.getColumnIndex(Col_COURSE_NAME))
                    course.prerequisite = cursor.getString(cursor.getColumnIndex(COL_COURSE_PREREQUISITE))
                    course.term = cursor.getString(cursor.getColumnIndex(COL_TERM))
                    course.courseDetails =
                        cursor.getString(cursor.getColumnIndex(COL_COURSE_DETAILS))

                    registerCourse.add(course)



                } while (cursor.moveToNext())


            }

             return registerCourse
        }

    fun exitedRegisterCourse(courseId: String?): Boolean {
        var flag = false
        val selectQuery="SELECT * FROM $TABLE_REGIDTER_COURSE_NAME WHERE $Col_COURSE_ID=$courseId"
        val db: SQLiteDatabase =this.writableDatabase
        val cursor: Cursor =db.rawQuery(selectQuery, null)

        Log.e("selct",cursor.toString())


       // val cursor: Cursor =db.rawQuery(selectQuery, null)

    /*    if (cursor.moveToFirst() && cursor!=null && cursor.getCount()>0) {
               flag =true
            } else {
               flag =false
            }  */

        return flag
    }
    fun addRegisterCourse(course: Course) {

        val db:SQLiteDatabase=this.writableDatabase
        val values= ContentValues()
        values.put(Col_ID,course.id)
        values.put(Col_COURSE_ID,course.courseId)
        values.put(Col_COURSE_NAME,course.courseName)
        values.put(COL_COURSE_PREREQUISITE,course.prerequisite)
        values.put(COL_TERM, course.term)
        values.put(COL_COURSE_DETAILS,course.courseDetails)
        db.insert(TABLE_REGIDTER_COURSE_NAME, null,values)
        db.close()
    }
    fun deleteRegisterCourse(courseId:String)
    {

        val db:SQLiteDatabase=this.writableDatabase
       var result= db.delete(TABLE_NAME, "$Col_COURSE_ID=?", arrayOf(courseId))
        Log.e("result",result.toString())

        //if(result>0){

      //  }

        db.close()
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
    fun addCourse(courseId:String,courseName:String,prerequisite:String,term:String,courseDetails:String) {

        val db:SQLiteDatabase=this.writableDatabase
        val values= ContentValues()

        values.put(Col_COURSE_ID,courseId)
        values.put(Col_COURSE_NAME,courseName)
        values.put(COL_COURSE_PREREQUISITE,prerequisite)
        values.put(COL_TERM, term)
        values.put(COL_COURSE_DETAILS,courseDetails)

        db.insert(TABLE_NAME, null,values)

        db.close()


    }

    /* fun addCourse(course: Course) {

         val db:SQLiteDatabase=this.writableDatabase
         val values= ContentValues()

         values.put(Col_COURSE_ID,course.courseId)
         values.put(Col_COURSE_NAME,course.courseName)
         values.put(COL_COURSE_PREREQUISITE,course.prerequisite)
         values.put(COL_TERM, course.term)
         values.put(COL_COURSE_DETAILS,course.courseDetails)

         db.insert(TABLE_NAME, null,values)

         db.close()


     }  */


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