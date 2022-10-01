////Courtesy of Mr Noad

package rat.swizko.interfaces

interface IOverlay {
    val haveTargetWindow: Boolean
    var clickThrough: Boolean
    var protectAgainstScreenshots: Boolean
}