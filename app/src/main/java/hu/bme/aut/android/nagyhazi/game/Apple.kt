package hu.bme.aut.android.nagyhazi.game

import kotlin.random.Random

class Apple(var w:Int, var h:Int) {

    private var x = 0
        fun getX(): Int {
            return x
        }

    private var y = 0
        fun getY(): Int{
            return y
        }

    fun createCoordinates(){
        x = Random.nextInt(0, w-50)
        y = Random.nextInt(0, h-50)
    }

}