package com.example.midle_exam

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.midle_exam.database.ConnectDB
import com.example.midle_exam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var db:ConnectDB? = null
    lateinit var binding: ActivityMainBinding
    var checkMail  = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // connect database
        db = ConnectDB(this)
        db?.openDatabase()
        // data helper
        var helper = ConnectDB(applicationContext)
        var data = helper.readableDatabase

        // btn register
        binding.btnRegister.setOnClickListener {
            evenrAdd()
        }
    }

    private fun evenrAdd() {

        // data helper
        var helper = ConnectDB(applicationContext)
        var data = helper.readableDatabase

        // get data
        val mail = findViewById<EditText>(R.id.edtEmail)
        val user = findViewById<EditText>(R.id.edtUser)
        val pass = findViewById<EditText>(R.id.edtPass)
        val passAgain = findViewById<EditText>(R.id.edtAgainPass)


        // insert data
           if (!mail.text.toString().matches(checkMail)){
                 mail.error = "Nhập đúng định dạng email!"
           }
        // exists data
        var rs = data.rawQuery("Select * from users where mail = '${mail.text.toString()}'",null)
        var checkErr:Int = 0
        // data view
        if (rs.count>0){
            mail.error = "Mail đã tồn tại!"
            checkErr++
        }
        if (pass.text.toString()!=passAgain.text.toString()){
            passAgain.error = "Mật khẩu không trùng khớp!"
            checkErr++
        }
        var rsUser = data.rawQuery("Select * from users where name = '${user.text.toString()}'",null)
        if (rsUser.count>0){
            user.error = "Tên đăng nhập đã tồn tại!"
            checkErr++
        }
        // data empty
        if (mail.text.toString().isEmpty()){
            mail.error = "Vui lòng điền vào trường này!"
            checkErr++
        }
        if (user.text.toString().isEmpty()){
            user.error = "Vui lòng điền vào trường này!"
            checkErr++
        }
        if (pass.text.toString().isEmpty()){
            mail.error = "Vui lòng điền vào trường này!"
            checkErr++
        }
        if (passAgain.text.toString().isEmpty()){
            mail.error = "Vui lòng điền vào trường này!"
            checkErr++
        }

        if ( checkErr==0){
            var cv = ContentValues()
            cv.put("mail",mail.text.toString())
            cv.put("name",user.text.toString())
            cv.put("pass",pass.text.toString())

            data.insert("users",null,cv)
            Toast.makeText(this,"Thêm thành công!",Toast.LENGTH_SHORT).show()
        }













    }
}