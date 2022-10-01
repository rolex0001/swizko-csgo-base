package rat.swizko.scripts.visuals

import org.jire.arrowhead.keyPressed
import rat.swizko.curSettings
import rat.swizko.ui.uiUpdate
import rat.swizko.utils.every
import rat.swizko.utils.generalUtil.strToBool

fun espToggle() = every(50) {
    if (keyPressed(curSettings["VISUALS_TOGGLE_KEY"].toInt())) {
        curSettings["ENABLE_ESP"] = !curSettings["ENABLE_ESP"].strToBool()
        if (!curSettings["ENABLE_ESP"].strToBool()) {
            disableAllEsp()
        }

        Thread.sleep(100)

        uiUpdate()
    }
}