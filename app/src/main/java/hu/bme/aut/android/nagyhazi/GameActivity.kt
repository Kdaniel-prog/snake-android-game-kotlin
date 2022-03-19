package hu.bme.aut.android.nagyhazi

import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import hu.bme.aut.android.nagyhazi.databinding.ActivityGameBinding
import hu.bme.aut.android.nagyhazi.game.Apple
import hu.bme.aut.android.nagyhazi.game.Direction
import hu.bme.aut.android.nagyhazi.game.Snake
import java.util.*

class GameActivity : AppCompatActivity(), SurfaceHolder.Callback{
    private lateinit var binding: ActivityGameBinding
    private var surfaceHolder: SurfaceHolder? = null
    private lateinit var snake: Snake
    private var snakeList: ArrayList<Snake> = ArrayList()
    private var score: Int = 0
    private lateinit var apple: Apple
    private lateinit var  textScore : TextView
    private lateinit var name: TextView
    private lateinit var surfaceView: SurfaceView
    private var timer = Timer()
    private lateinit var c : Canvas
    private val paint = Paint()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        surfaceView = binding.root.findViewById(R.id.GameSurfaceView)
        textScore = binding.root.findViewById(R.id.GameScoreTv)
        surfaceView.holder.addCallback(this)

        binding.LeftRotate.setOnClickListener{
            snakeList[0].RotateLeft()
        }

        binding.RightRotate.setOnClickListener{
            snakeList[0].RotateRight()
        }
    }


    override fun surfaceCreated(p0: SurfaceHolder) {
        surfaceHolder = p0
        c  = surfaceHolder!!.lockCanvas()
        init()
        surfaceHolder!!.unlockCanvasAndPost(c)
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        surfaceHolder = p0
        running(surfaceHolder!!)
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        surfaceHolder = p0
        surfaceHolder!!.removeCallback(this)
    }

    private fun init(){
        val snake_color =  intent.getIntExtra("GameBeginColor",0)
        paint.color  =  Color.rgb(snake_color.red, snake_color.green, snake_color.blue)
        name = binding.root.findViewById(R.id.nameText)
        val str = intent.getStringExtra("text_name")!!
        name.text = str

        snakeList.clear()
        score = 0
        apple = Apple(c.width,c.height)
        apple.createCoordinates()
        snake = Snake(100,100, c.width, c.height)
        snake.direction = Direction.right
        snakeList.add(snake)
        snakeList.add(Snake(0,0,c.width,c.height))
    }

    private  fun  running(p0: SurfaceHolder) {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                surfaceHolder = p0
                c = surfaceHolder!!.lockCanvas()
                c.drawColor(Color.BLACK)
                getScore()
                snakeMoving()
                loseCheck()
                drawing(c)
                surfaceHolder!!.unlockCanvasAndPost(c)
            }
        }, 0, 200)
    }

    override fun onBackPressed() {
        timer.cancel()
        startActivity(Intent(this, MenuActivity::class.java))
    }

    fun getScore(){
        val distance = Math.sqrt(Math.pow(((middlePoint(snakeList[0].x, snakeList[0].x + 50)
                - middlePoint(apple.getX(), apple.getX() + 60)).toDouble()), 2.0)
                + Math.pow((middlePoint(snakeList[0].y, snakeList[0].y + 50)
                    - middlePoint(apple.getY(),apple.getY()+60)).toDouble(), 2.0))
        if(distance < 60){
            score += 1
            snakeList.add(Snake(0,0,c.width, c.height))
            apple.createCoordinates()
            Thread {
                this@GameActivity.runOnUiThread {
                    textScore.text = score.toString()
                }
            }.start()
        }
    }

    fun drawing(c: Canvas){
        val apple_img : Drawable = resources.getDrawable(R.drawable.apple, null)
        paint.style = Paint.Style.FILL
        apple_img.setBounds(apple.getX(),apple.getY(),apple.getX()+60,apple.getY()+60)
        apple_img.draw(c)
        drawSnake(c,paint)
    }

    fun drawSnake(c: Canvas, paint: Paint){
        for(i in 0 until snakeList.size){
            val snake_rect = Rect(snakeList[i].x, snakeList[i].y, snakeList[i].x + 50, snakeList[i].y + 50)
            c.drawRect(snake_rect, paint)
        }
    }

    fun snakeMoving(){
        var x = snakeList[0].x
        var y = snakeList[0].y
        snakeList[0].moving()

        for(i in 1 until snakeList.size){
            val x1 = snakeList[i].x
            val y1 = snakeList[i].y
            snakeList[i].x = x
            snakeList[i].y = y
            x = x1
            y = y1
        }
    }

    private fun middlePoint(x1:Int, x2:Int): Int{
        return (x1 + x2) / 2
    }

    fun loseCheck(){
        for(i in 0 until snakeList.size){
            for(j in 0 until snakeList.size){
                if(snakeList[i].y == snakeList[j].y && snakeList[j].x == snakeList[i].x && i!=j){
                    timer.cancel()
                    val intent = Intent(this, GameEndActivity::class.java)
                    intent.putExtra("name",name.text)
                    intent.putExtra("score",textScore.text)
                    startActivity(intent)
                }
            }
        }
    }

}