package rat.swizko.scripts

import com.badlogic.gdx.math.MathUtils.clamp
import rat.swizko.WEAPON_STATS_FILE
import rat.swizko.curSettings
import rat.swizko.game.CSGO
import rat.swizko.game.CSGO.csgoEXE
import rat.swizko.game.entity.*
import rat.swizko.game.me
import rat.swizko.game.netvars.NetVarOffsets
import rat.swizko.game.netvars.NetVarOffsets.m_weaponMode
import rat.swizko.overlay.App
import rat.swizko.scripts.aim.meCurWep
import rat.swizko.scripts.aim.meCurWepEnt
import rat.swizko.scripts.aim.meDead
import rat.swizko.settings.MENUTOG
import rat.swizko.utils.every
import rat.swizko.utils.generalUtil.strToBool
import rat.swizko.utils.generalUtil.strToColorGDX
import rat.swizko.utils.inGame
import java.lang.Math.toDegrees
import java.lang.Math.toRadians
import kotlin.math.atan
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.tan

private data class WeaponData(var maxPlayerSpeed: Int = 0, var spread: Float = 0f, var inaccuracyFire: Float = 0f,
                              var inaccuracyMove: Float = 0f, var inaccuracyFireAlt: Float = 0f, var inaccuracyMoveAlt: Float = 0f,
                              var maxPlayerSpeedAlt: Int = 0, var spreadAlt: Float = 0f)

private var wepData = WeaponData()

private fun refreshWepData() = every(1000) {
    wepData = getWeaponData(meCurWep.name)
}

fun spreadCircle() {
    refreshWepData()

    App {
        if (meDead || MENUTOG || !curSettings["ENABLE_ESP"].strToBool() || !curSettings["SPREAD_CIRCLE"].strToBool() || !inGame) return@App

        val vAbsVelocity = me.velocity()
        val flVelocity = sqrt(vAbsVelocity.x.pow(2F) + vAbsVelocity.y.pow(2F) + vAbsVelocity.z.pow(2F))

        val realInaccuracyFire: Float
        val realSpread: Float
        val realInaccuracyMove: Float

        if (csgoEXE.int(meCurWepEnt + m_weaponMode) > 0) { //Silencer
            realInaccuracyFire = wepData.inaccuracyFireAlt
            realSpread = wepData.spreadAlt
            realInaccuracyMove = wepData.inaccuracyMoveAlt
        } else {
            realInaccuracyFire = wepData.inaccuracyFire
            realSpread = wepData.spread
            realInaccuracyMove = wepData.inaccuracyMove
        }

        var radius = realInaccuracyMove * (flVelocity / wepData.maxPlayerSpeed)
        radius += clamp(me.shotsFired() * realInaccuracyFire, 0f, realSpread * 100)

        val defaultFov = csgoEXE.int(me + NetVarOffsets.m_iDefaultFov)
        val iFov = csgoEXE.int(me + NetVarOffsets.m_iFOV)
        val viewFov: Int

        viewFov = if (iFov == 0) {
            defaultFov
        } else {
            iFov
        }

        val actualRadius = toDegrees(atan(radius / 2560.0 * tan(toRadians((2.0 * toDegrees(atan((16.0 / 9.0) * 0.75 * tan(toRadians(90 / 2.0))))) / 2.0)))) * 2.0
        val realFov = calcFovRadius(viewFov, actualRadius.toFloat())

        val rccXo = curSettings["RCROSSHAIR_XOFFSET"].toFloat()
        val rccYo = curSettings["RCROSSHAIR_YOFFSET"].toFloat()
        val x = CSGO.gameWidth / 2 + rccXo
        val y = CSGO.gameHeight / 2 + rccYo

        shapeRenderer.apply {
            if (isDrawing) {
                end()
            }

            begin()

            color = curSettings["SPREAD_CIRCLE_COLOR"].strToColorGDX()
            circle(x, y, realFov)

            end()
        }
    }
}

private fun getWeaponData(wep: String): WeaponData {
    val wepData = WeaponData()
    var strList: List<String>

    WEAPON_STATS_FILE.forEachLine { line->
        if (line.startsWith(wep)) {
            strList = line.split(" : ")

            try {
                wepData.maxPlayerSpeed = strList[1].toInt()
                wepData.spread = strList[2].toFloat()
                wepData.inaccuracyFire = strList[3].toFloat()
                wepData.inaccuracyMove = strList[4].toFloat()
                wepData.inaccuracyFireAlt = strList[5].toFloat()
                wepData.inaccuracyMoveAlt = strList[6].toFloat()
                wepData.maxPlayerSpeedAlt = strList[7].toInt()
                wepData.spreadAlt = strList[8].toFloat()
            } catch (e: Exception) {
                println("$strList is FUCKING WRONG BROOOOOOOOO FUCK")
            }
        }
    }

    return wepData
}