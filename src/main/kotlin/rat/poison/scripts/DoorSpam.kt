package rat.swizko.scripts

import org.jire.arrowhead.keyPressed
import rat.swizko.curSettings
import rat.swizko.game.CSGO
import rat.swizko.game.hooks.cursorEnable
import rat.swizko.game.hooks.updateCursorEnable
import rat.swizko.game.offsets.ClientOffsets.dwUse
import rat.swizko.scripts.aim.meDead
import rat.swizko.utils.every
import rat.swizko.utils.generalUtil.strToBool

fun doorSpam() = every(20, inGameCheck = true) {
    if (!curSettings["D_SPAM"].strToBool() || meDead) return@every

    updateCursorEnable()
    if (cursorEnable) return@every

    if (keyPressed(curSettings["D_SPAM_KEY"].toInt())) {
        Thread(Runnable {
            CSGO.clientDLL[dwUse] = 5
            Thread.sleep(20)
            CSGO.clientDLL[dwUse] = 4
            Thread.sleep(20)
        }).start()
    }
}