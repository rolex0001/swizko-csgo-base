package rat.swizko.utils.extensions

import org.jire.arrowhead.Source
import org.jire.arrowhead.unsign
import rat.swizko.scripts.toIndex

fun Source.uint(address: Long, offset: Long = 0) = int(address, offset).unsign()
fun Source.readIndex(address: Long) = int(address).toIndex()