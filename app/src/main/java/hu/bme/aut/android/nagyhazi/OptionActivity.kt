package hu.bme.aut.android.nagyhazi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import hu.bme.aut.android.nagyhazi.databinding.ActivityOptionBinding
import yuku.ambilwarna.AmbilWarnaDialog

class OptionActivity : AppCompatActivity(){
    private var defualtColor: Int = -14609282
    private lateinit var binding: ActivityOptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.getIntExtra("MenuColor", 0) != 0){
            defualtColor = intent.getIntExtra("MenuColor", 0)
        }

        binding.btnColor.setOnClickListener{
            openColorPicker()
        }

        binding.btnSave.setOnClickListener{
            val intent = Intent(this,MenuActivity::class.java)
            Log.d("Option","$defualtColor")
            intent.putExtra("OptionColor",defualtColor)
            startActivity(intent)
        }
    }

    private fun  openColorPicker (){
        AmbilWarnaDialog(this, defualtColor, object : AmbilWarnaDialog.OnAmbilWarnaListener{

            override fun  onCancel(dialog: AmbilWarnaDialog){
                Toast.makeText(this@OptionActivity, "Exit of Color Picker" ,
                    Toast.LENGTH_LONG).show()
            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                defualtColor = color
            }
        }).show()
    }

}