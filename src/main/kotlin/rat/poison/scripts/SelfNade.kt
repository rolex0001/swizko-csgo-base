package rat.swizko.scripts

import kotlinx.coroutines.Runnable
import rat.swizko.curSettings
import rat.swizko.game.angle
import rat.swizko.game.clientState
import rat.swizko.robot
import rat.swizko.settings.MENUTOG
import rat.swizko.utils.Angle
import rat.swizko.utils.normalize
import rat.swizko.utils.pathAim
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent

fun selfNade() {
    Thread(Runnable {
        if (MENUTOG) {
            robot.keyPress(curSettings["MENU_KEY"].toInt())
        }
        Thread.sleep(50)

        val curAng = clientState.angle()
        val destAng = Angle() //= curAng doesnt work??
        destAng.set(-89F, curAng.y, curAng.z)
        destAng.normalize()

        pathAim(curAng, destAng, 10, false, checkOnScreen = false)
        Thread.sleep(50)
        robot.keyPress(KeyEvent.VK_CONTROL)
        Thread.sleep(50)
        robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK)
        Thread.sleep(50)
        robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK)
        Thread.sleep(2000)
        robot.keyRelease(KeyEvent.VK_CONTROL)
    }).start()
}
