package rat.swizko.scripts.visuals

import rat.swizko.curSettings
import rat.swizko.game.CSGO.csgoEXE
import rat.swizko.game.offsets.ClientOffsets.bOverridePostProcesing
import rat.swizko.utils.every
import rat.swizko.utils.generalUtil.strToBool
import rat.swizko.utils.shouldPostProcess

fun disablePostProcessing() = every(10000, true, inGameCheck = true) {
    if (!shouldPostProcess) return@every

    if (curSettings["DISABLE_POST_PROCESSING"].strToBool()) {
        csgoEXE[bOverridePostProcesing] = true
    } else if (csgoEXE.boolean(bOverridePostProcesing)) {
        csgoEXE[bOverridePostProcesing] = false
    }
}