package rat.swizko.scripts

import rat.swizko.curSettings
import rat.swizko.game.CSGO.csgoEXE
import rat.swizko.game.entity.isScoped
import rat.swizko.game.me
import rat.swizko.game.netvars.NetVarOffsets.m_iDefaultFov
import rat.swizko.game.netvars.NetVarOffsets.m_zoomLevel
import rat.swizko.overlay.App
import rat.swizko.scripts.aim.meCurWep
import rat.swizko.scripts.aim.meCurWepEnt
import rat.swizko.utils.generalUtil.strToBool
import rat.swizko.utils.inGame

internal fun fovChanger() = App {
    if (!inGame) return@App

    val curFov = csgoEXE.int(me + m_iDefaultFov)

    if (!curSettings["ENABLE_FOV_CHANGER"].strToBool()) {
        if (curFov != 90) {
            csgoEXE[me + m_iDefaultFov] = 90
        }

        return@App
    }
    val zLevel = csgoEXE.int(meCurWepEnt + m_zoomLevel)

    val targetFov: Int

    var instantSwap = false

    if (meCurWep.sniper) {
        if (me.isScoped()) {
            targetFov = when (zLevel) {
                1 -> {
                    instantSwap = true
                    curSettings["FOV_ZOOM_1"].toInt()
                }

                2 -> {
                    instantSwap = true
                    curSettings["FOV_ZOOM_2"].toInt()
                }

                else -> {
                    instantSwap = true
                    curSettings["FOV_SNIPER_DEFAULT"].toInt()
                }
            }
        } else {
            targetFov = curSettings["FOV_SNIPER_DEFAULT"].toInt()

            if (targetFov > curFov) {
                instantSwap = true
            }
        }
    } else {
        targetFov = curSettings["FOV_DEFAULT"].toInt()
    }

    if (targetFov != -1) {
        if (instantSwap) {
            csgoEXE[me + m_iDefaultFov] = targetFov
            return@App
        }

        if (curSettings["FOV_SMOOTH"].strToBool()) {

            if (curFov < targetFov) {
                csgoEXE[me + m_iDefaultFov] = curFov + 1
            } else if (curFov > targetFov) {
                csgoEXE[me + m_iDefaultFov] = curFov - 1
            }
        } else {
            if (curFov != targetFov) {
                csgoEXE[me + m_iDefaultFov] = targetFov
            }
        }
    }
}