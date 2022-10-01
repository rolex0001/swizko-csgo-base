package rat.swizko.scripts

import rat.swizko.curSettings
import rat.swizko.game.CSGO.csgoEXE
import rat.swizko.game.hooks.onFlash
import rat.swizko.game.me
import rat.swizko.game.netvars.NetVarOffsets.flFlashMaxAlpha
import rat.swizko.utils.generalUtil.strToBool

fun reducedFlash() = onFlash {
	if (!curSettings["ENABLE_REDUCED_FLASH"].strToBool()) return@onFlash

	csgoEXE[me + flFlashMaxAlpha] = curSettings["FLASH_MAX_ALPHA"].toFloat()
}