package rat.swizko.scripts

import org.jire.arrowhead.keyPressed
import rat.swizko.curSettings
import rat.swizko.game.CSGO
import rat.swizko.game.entity.onGround
import rat.swizko.game.hooks.cursorEnable
import rat.swizko.game.hooks.updateCursorEnable
import rat.swizko.game.me
import rat.swizko.game.offsets.ClientOffsets.dwForceJump
import rat.swizko.scripts.aim.meDead
import rat.swizko.utils.every
import rat.swizko.utils.generalUtil.strToBool
import rat.swizko.utils.randInt

fun bunnyHop() = every(4, inGameCheck = true) {
    if (curSettings["ENABLE_BUNNY_HOP"].strToBool() && keyPressed(curSettings["ENABLE_BUNNY_HOP_KEY"].toInt()) && (me > 0 && !meDead && me.onGround()) && (randInt(0, 100) <= curSettings["BHOP_HITCHANCE"].toInt())) {
        updateCursorEnable()
        if (cursorEnable) return@every
        CSGO.clientDLL[dwForceJump] = 6
    }
}