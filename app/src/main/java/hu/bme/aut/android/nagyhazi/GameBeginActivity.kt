package hu.bme.aut.android.nagyhazi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import hu.bme.aut.android.nagyhazi.databinding.ActivityGameBeginBinding

class GameBeginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBeginBinding
    private lateinit var textName : TextView
    private var snake_color = -14609282

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBeginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textName  = binding.root.findViewById(R.id.etPlayerName)
        val str = textName.text

        snake_color =  intent.getIntExtra("MenuColor",0)

        binding.btnStart.setOnClickListener{
            if(textName.text.isEmpty()){
            Toast.makeText(this, "Please enter your name!",
                Toast.LENGTH_LONG).show()
            }else{
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("GameBeginColor",snake_color)
            intent.putExtra("text_name",str.toString())
            startActivity(intent)
            }
        }

    }
}