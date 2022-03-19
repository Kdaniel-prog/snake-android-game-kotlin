package hu.bme.aut.android.nagyhazi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import hu.bme.aut.android.nagyhazi.data.TopScore
import hu.bme.aut.android.nagyhazi.data.TopScoreListDatabase
import hu.bme.aut.android.nagyhazi.databinding.ActivityGameEndBinding
import kotlinx.coroutines.*


class GameEndActivity :
    AppCompatActivity(),
    CoroutineScope by MainScope() {

    private lateinit var binding: ActivityGameEndBinding
    private lateinit var  endScore : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameEndBinding.inflate(layoutInflater)
        setContentView(binding.root)
        endScore  = binding.root.findViewById(R.id.endScore)
        val name = intent.getStringExtra("name")
        val score = intent.getStringExtra("score")

        endScore.text = "$name your score: $score"
        binding.btnEnd.setOnClickListener{
            loadPlayersInBackground()
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }

    fun loadPlayersInBackground() = launch {
        val name = intent.getStringExtra("name")
        val score = intent.getStringExtra("score")
        val database = TopScoreListDatabase.getDatabase(applicationContext)
        database.TopScoreDao().insert(TopScore(0, name!!,score.toString()))
    }
}