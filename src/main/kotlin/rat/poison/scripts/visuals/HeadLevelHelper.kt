package rat.swizko.scripts.visuals

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.MathUtils.clamp
import com.badlogic.gdx.math.Matrix4
import org.lwjgl.opengl.GL11.glEnable
import rat.swizko.curSettings
import rat.swizko.game.angle
import rat.swizko.game.clientState
import rat.swizko.game.entity.onGround
import rat.swizko.game.entity.position
import rat.swizko.game.me
import rat.swizko.game.w2sViewMatrix
import rat.swizko.overlay.App
import rat.swizko.scripts.aim.meDead
import rat.swizko.utils.generalUtil.strToBool
import rat.swizko.utils.generalUtil.strToColorGDX
import rat.swizko.utils.generalUtil.toMatrix4
import rat.swizko.utils.inGame
import kotlin.math.abs

fun headLevelHelper() = App {
    if (!inGame || meDead) return@App

    val mePos = me.position()
    val meAng = clientState.angle()

    if (me.onGround() && curSettings["HEAD_LVL_ENABLE"].strToBool()) {
        val oldMatrix = Matrix4(shapeRenderer.projectionMatrix.values)

        val deadZone = curSettings["HEAD_LVL_DEADZONE"].toInt()

        shapeRenderer.apply {
            val gameMatrix = w2sViewMatrix.toMatrix4()
            gameMatrix.translate(0f, 0f, mePos.z)
            projectionMatrix = gameMatrix

            if (shapeRenderer.isDrawing) {
                end()
            }

            begin()

            glEnable(GL20.GL_BLEND) //sb end resets...

            val c = curSettings["HEAD_LVL_COLOR"].strToColorGDX()
            c.a = clamp((abs(meAng.x) - deadZone) / 5f, 0f, 1f)

            color = c

            circle(mePos.x, mePos.y, 50f)

            end()
        }
        shapeRenderer.projectionMatrix = oldMatrix
    }
}