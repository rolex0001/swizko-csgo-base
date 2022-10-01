package rat.swizko.scripts

import org.jire.arrowhead.keyPressed
import rat.swizko.curSettings
import rat.swizko.game.hooks.cursorEnable
import rat.swizko.game.hooks.updateCursorEnable
import rat.swizko.robot
import rat.swizko.scripts.aim.meDead
import rat.swizko.utils.ObservableBoolean
import rat.swizko.utils.every
import rat.swizko.utils.generalUtil.strToBool
import java.awt.event.MouseEvent

private var toggled = false
var weaponSpamToggleKey = ObservableBoolean({keyPressed(curSettings["W_SPAM_KEY"].toInt())})

fun weaponSpam() = every (20, inGameCheck = true) {
    if (!curSettings["W_SPAM"].strToBool()) return@every

    updateCursorEnable()
    if (cursorEnable || meDead) return@every

    weaponSpamToggleKey.update()
    if (weaponSpamToggleKey.justBecameTrue) {
        toggled = !toggled
    }

    if (toggled) {
        Thread(Runnable {
            robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK)
            Thread.sleep(2)
            robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK)
            Thread.sleep(2)
        }).start()
    }
}

