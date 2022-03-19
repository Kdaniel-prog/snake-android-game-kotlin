package hu.bme.aut.android.nagyhazi.game


class Snake(var x: Int, var y: Int, var w: Int ,var h: Int){
    var direction = Direction.right
    private val pointSize = 50

    fun RotateRight() {
        direction = when(direction){
            Direction.up -> Direction.right
            Direction.right -> Direction.down
            Direction.down -> Direction.left
            Direction.left -> Direction.up
        }
    }

    fun RotateLeft() {
        direction = when(direction){
            Direction.up -> Direction.left
            Direction.left -> Direction.down
            Direction.down -> Direction.right
            Direction.right -> Direction.up
        }
    }

    fun moving(){
        when(direction){
            Direction.down ->
                if(y < h){
                    y += pointSize
                }else{
                    y = 0
                }
            Direction.left ->
                if(x > 0){
                    x -= pointSize
                }else{
                    x = w
                }
            Direction.up ->
                if(y > 0){
                    y -= pointSize
                }else{
                    y = h
                }
            Direction.right ->
                if(x < w){
                    x += pointSize

                }else{
                    x = 0
                }
        }
    }

}