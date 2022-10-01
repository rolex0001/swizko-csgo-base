package rat.swizko.scripts

import org.jire.arrowhead.keyPressed
import rat.swizko.curSettings
import rat.swizko.game.angle
import rat.swizko.game.clientState
import rat.swizko.game.entity.onGround
import rat.swizko.game.hooks.cursorEnable
import rat.swizko.game.hooks.updateCursorEnable
import rat.swizko.game.me
import rat.swizko.robot
import rat.swizko.scripts.aim.meDead
import rat.swizko.settings.MENUTOG
import rat.swizko.utils.every
import rat.swizko.utils.generalUtil.strToBool
import rat.swizko.utils.inBackground
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.VK_SPACE

private var lastAngY = 0.0F

fun strafeHelper() = every(2, inGameCheck = true) {
    if (MENUTOG || inBackground || meDead) return@every

    if (!curSettings["AUTO_STRAFE"].strToBool()) return@every

    updateCursorEnable()
    if (cursorEnable) return@every
    val curAngY = clientState.angle().y
    val grounded = me.onGround()

    if ((curSettings["STRAFE_BHOP_ONLY"].strToBool() && keyPressed(VK_SPACE)) || (!curSettings["STRAFE_BHOP_ONLY"].strToBool())) {
        if (!grounded) {
            if (!keyPressed(KeyEvent.VK_A) && !keyPressed(KeyEvent.VK_D)) {
                if (curAngY > lastAngY) {
                    robot.keyPress(KeyEvent.VK_A)
                    robot.keyRelease(KeyEvent.VK_A)
                } else if (curAngY < lastAngY) {
                    robot.keyPress(KeyEvent.VK_D)
                    robot.keyRelease(KeyEvent.VK_D)
                }
            }
        }
    }

    lastAngY = curAngY
}