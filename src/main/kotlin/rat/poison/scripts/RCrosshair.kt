package rat.swizko.scripts

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.swizko.curSettings
import rat.swizko.game.CSGO.csgoEXE
import rat.swizko.game.CSGO.gameHeight
import rat.swizko.game.CSGO.gameWidth
import rat.swizko.game.entity.isScoped
import rat.swizko.game.entity.punch
import rat.swizko.game.me
import rat.swizko.game.netvars.NetVarOffsets
import rat.swizko.overlay.App
import rat.swizko.scripts.aim.meCurWep
import rat.swizko.settings.MENUTOG
import rat.swizko.ui.uiPanels.mainTabbedPane
import rat.swizko.ui.uiPanels.rcsTab
import rat.swizko.utils.generalUtil.strToBool
import rat.swizko.utils.generalUtil.strToColor
import rat.swizko.utils.inGame
import java.lang.Math.toRadians
import kotlin.math.*

internal fun rcrosshair() = App {
    if (!curSettings["ENABLE_ESP"].strToBool() || !inGame) return@App

    val eRC = curSettings["ENABLE_RECOIL_CROSSHAIR"].strToBool()
    val eSC = !curSettings["ENABLE_SNIPER_CROSSHAIR"].strToBool()

    if (!eRC) return@App

    val x: Float
    val y: Float

    //Crosshair Length/Width
    val cL = curSettings["RCROSSHAIR_LENGTH"].toFloat()
    val cW = curSettings["RCROSSHAIR_WIDTH"].toFloat()

    //Crosshair X/Y offset
    val rccXo = curSettings["RCROSSHAIR_XOFFSET"].toFloat()
    val rccYo = curSettings["RCROSSHAIR_YOFFSET"].toFloat()

    //Crosshair FOV modifier
    val curFov = csgoEXE.int(me + NetVarOffsets.m_iDefaultFov)
    val rccFov1 = atan((gameWidth.toFloat()/gameHeight.toFloat()) * 0.75 * tan(toRadians(curFov/2.0)))
    val rccFov2 = (gameWidth/2) / tan(rccFov1).toFloat()

    //Center based on Length/Width
    val wO = floor(cW / 2.0).toFloat()
    val lO = floor(cL / 2.0).toFloat()

    if (eRC && !(eSC && meCurWep.sniper)) {
        val punch = me.punch()

        //Center
        x = (gameWidth / 2) - tan(toRadians(punch.y.toDouble())).toFloat() * rccFov2 + rccXo
        y = (gameHeight / 2) - tan(toRadians(punch.x.toDouble())).toFloat() * rccFov2 + rccYo
    } else {
        //Center
        x = gameWidth / 2 + rccXo
        y = gameHeight / 2 + rccYo
    }

    if (!MENUTOG || ((eRC || eSC) && mainTabbedPane.activeTab == rcsTab)) {
        shapeRenderer.apply {
            if (shapeRenderer.isDrawing) {
                end()
            }

            begin()
            val col = curSettings["RCROSSHAIR_COLOR"].strToColor()
            color = Color(col.red / 255F, col.green / 255F, col.blue / 255F, curSettings["RCROSSHAIR_ALPHA"].toFloat())

            val hasSniper = meCurWep.scope

            if ((eSC && hasSniper && !me.isScoped()) || !eSC || (eRC && !hasSniper)) {
                if (curSettings["RCROSSHAIR_TYPE"].uppercase() == "CROSSHAIR") {
                    set(ShapeRenderer.ShapeType.Filled)
                    //Horizontal
                    rect(x - lO, y - wO, cL, cW)
                    //Vertical
                    rect(x - wO, y - lO, cW, cL)
                } else {
                    circle(x, y, curSettings["RCROSSHAIR_RADIUS"].toFloat())
                }
            }

            set(ShapeRenderer.ShapeType.Line)
            end()
        }
    }
}