package hu.bme.aut.android.nagyhazi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.android.nagyhazi.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private var snake_color = -14609282

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.getIntExtra("OptionColor",0) != 0){
            snake_color = intent.getIntExtra("OptionColor",0)
        }

        binding.btnStart.setOnClickListener{
            val intent = Intent(this, GameBeginActivity::class.java)
            intent.putExtra("MenuColor",snake_color)
            startActivity(intent)
        }

        binding.btnTopScores.setOnClickListener{
            val intent = Intent(this, TopScoreActivity::class.java)

            startActivity(intent)
        }

        binding.btnOption.setOnClickListener{
            val intent = Intent(this,OptionActivity::class.java)
            intent.putExtra("MenuColor",snake_color)
            startActivity(intent)
        }

    }
}