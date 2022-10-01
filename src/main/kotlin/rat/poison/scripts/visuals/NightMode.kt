package rat.swizko.scripts.visuals

import rat.swizko.curSettings
import rat.swizko.game.CSGO.csgoEXE
import rat.swizko.game.hooks.toneMapController
import rat.swizko.game.netvars.NetVarOffsets.m_bUseCustomAutoExposureMax
import rat.swizko.game.netvars.NetVarOffsets.m_bUseCustomAutoExposureMin
import rat.swizko.game.netvars.NetVarOffsets.m_flCustomAutoExposureMax
import rat.swizko.game.netvars.NetVarOffsets.m_flCustomAutoExposureMin
import rat.swizko.utils.every
import rat.swizko.utils.generalUtil.strToBool

fun nightMode() = every(1000, inGameCheck = true) {
    if (!curSettings["ENABLE_ESP"].strToBool()) return@every

    if (curSettings["ENABLE_NIGHTMODE"].strToBool()) {
        if (toneMapController != 0L) {
            csgoEXE[toneMapController + m_bUseCustomAutoExposureMin] = 1
            csgoEXE[toneMapController + m_bUseCustomAutoExposureMax] = 1

            csgoEXE[toneMapController + m_flCustomAutoExposureMin] = curSettings["NIGHTMODE_VALUE"].toFloat()
            csgoEXE[toneMapController + m_flCustomAutoExposureMax] = curSettings["NIGHTMODE_VALUE"].toFloat()
        }
    }
}