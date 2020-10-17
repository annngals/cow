import java.io.File

const val zero = (0).toChar()

fun readCow(path: String): String {
    val file = File(path)
    val lines = file.readLines()
    return lines.joinToString(" ")
}

fun eval(source: String) {
    val arr = ArrayList<String>(source.split(' '))
    val buffer = Array(1000) { _ -> zero }
    var ptr = 0
    var i = 0
    val blocks = getLoopBlocks(arr)
    while (i < arr.size) {

        when (arr[i]) {
            "moO" -> ptr += 1
            "mOo" -> ptr -= 1
            "MoO" -> buffer[ptr] = buffer[ptr] + 1
            "MOo" -> buffer[ptr] = buffer[ptr] - 1
            "OOM" -> print(buffer[ptr].toInt())
            "Moo" -> {
                if(buffer[ptr] == zero){
                    print(">>> ")
                    buffer[ptr] = readLine()?.toCharArray()?.get(0)!!
                } else{
                    print(buffer[ptr])
                }
            }
            "MOO" -> {
                if (buffer[ptr] == zero){
                    i = blocks[i]!!
                }
            }
            "moo" -> {
                if (buffer[ptr] != zero){
                    i = blocks[i]!!
                } else {
                    if (i + 1 < arr.size && arr[i + 1] == ":"){
                        print(arr[i]+" ")
                    }
                }
            }
        }
        i += 1
    }
}

fun getLoopBlocks(source: ArrayList<String>): HashMap<Int, Int> {
    val blocks = HashMap<Int, Int>()
    val stack = mutableListOf<Int>()
    for ((i, char) in source.withIndex()) {
        if (char == "MOO") {
            stack.add(i)
        }
        if (char == "moo") {
            blocks[i] = stack[stack.lastIndex]
            blocks[stack.removeAt(stack.lastIndex)] = i
        }
    }
    return blocks
}

fun main(args: Array<String>) {
    var source = readCow("D://bf_examples//hello.cow")
    println(source)
    eval(source)
    println("\nDone")
}