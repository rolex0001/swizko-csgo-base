////Courtesy of Mr Noad

package rat.swizko.jna.structures

import com.sun.jna.Structure
import org.jire.arrowhead.Struct
import rat.swizko.jna.enums.AccentStates

class AccentPolicy : Struct(), Structure.ByReference {
    @JvmField
    internal var AccentState: Int = 0

    var accentState: AccentStates
        get() {
            return AccentStates[AccentState]
        }
        set(value) {
            AccentState = value.ordinal
        }

    @JvmField
    var AccentFlags: Int = 0
    @JvmField
    var GradientColor: Int = 0
    @JvmField
    var AnimationId: Int = 0
}