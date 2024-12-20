import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/inputs/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun Long.pow(n: Int): Long {
    var res = 1L
    var i = n
    var m = this
    while (i > 0L) {
        if (i and 1 == 1) {
            res *= m
        }
        m *= m
        i = i shr 1
    }
    return res
}

fun range(n: Int) = 0 until n