package rat.swizko.game.hooks

import rat.swizko.game.CSGO.csgoEXE
import rat.swizko.game.me
import rat.swizko.game.netvars.NetVarOffsets.flFlashMaxAlpha
import rat.swizko.scripts.aim.meDead
import rat.swizko.utils.hook
import rat.swizko.utils.inGame

val onFlash = hook(250) {
	if (me > 0 && !meDead && inGame) csgoEXE.float(me + flFlashMaxAlpha) > 0F
	else false
}