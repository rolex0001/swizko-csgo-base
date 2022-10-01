package rat.swizko.scripts

import org.jire.arrowhead.keyPressed
import rat.swizko.curSettings
import rat.swizko.game.CSGO.clientDLL
import rat.swizko.game.angle
import rat.swizko.game.clientState
import rat.swizko.game.entity.onGround
import rat.swizko.game.entity.velocity
import rat.swizko.game.hooks.cursorEnable
import rat.swizko.game.hooks.updateCursorEnable
import rat.swizko.game.me
import rat.swizko.game.offsets.ClientOffsets.dwForceBackward
import rat.swizko.game.offsets.ClientOffsets.dwForceForward
import rat.swizko.game.offsets.ClientOffsets.dwForceLeft
import rat.swizko.game.offsets.ClientOffsets.dwForceRight
import rat.swizko.scripts.aim.meDead
import rat.swizko.utils.every
import rat.swizko.utils.generalUtil.strToBool
import java.awt.event.KeyEvent.*
import kotlin.math.cos
import kotlin.math.sin

internal fun fastStop() = every(4, inGameCheck = true) {
    if (!curSettings["FAST_STOP"].strToBool() || meDead) return@every

    updateCursorEnable()
    if (cursorEnable) return@every

    val vel = me.velocity()
    val yaw = clientState.angle().y

    //Velocity relative to player direction
    val x = (vel.x * cos(yaw / 180 * Math.PI) + vel.y * sin(yaw / 180 * Math.PI))
    val y = (vel.y * cos(yaw / 180 * Math.PI) - vel.x * sin(yaw / 180 * Math.PI))

    if (!keyPressed(VK_SPACE) && me.onGround()) {
        if (x != 0.0 && y != 0.0) {
            if (!keyPressed(VK_W) && !keyPressed(VK_S)) {
                if (x > 30) {
                    clientDLL[dwForceBackward] = 6
                    //robot.keyRelease(VK_S)
                } else if (x < -30) {
                    clientDLL[dwForceForward] = 6
                    //robot.keyRelease(VK_W)
                }
            }

            if (!keyPressed(VK_A) && !keyPressed(VK_D)) {
                if (y > 30) {
                    clientDLL[dwForceRight] = 6
                    //robot.keyRelease(VK_D)
                } else if (y < -30) {
                    clientDLL[dwForceLeft] = 6
                    //robot.keyRelease(VK_A)
                }
            }
        }
    }
}