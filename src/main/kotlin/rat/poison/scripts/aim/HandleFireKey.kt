package rat.swizko.scripts.aim

import org.jire.arrowhead.keyPressed
import rat.swizko.curSettings
import rat.swizko.game.CSGO.clientDLL
import rat.swizko.game.entity.*
import rat.swizko.game.hooks.cursorEnable
import rat.swizko.game.hooks.updateCursorEnable
import rat.swizko.game.me
import rat.swizko.game.offsets.ClientOffsets.dwForceAttack
import rat.swizko.scripts.*
import rat.swizko.settings.MENUTOG
import rat.swizko.utils.every
import rat.swizko.utils.generalUtil.strToBool
import rat.swizko.utils.inBackground
import rat.swizko.utils.inGame

private var shouldShoot = false
var didShoot = false
var meDead = true

fun handleFireKey() = every(1, continuous = true) {
    if (inGame) {
        meDead = me.dead()
    } else {
        return@every
    }

    if (MENUTOG || (me > 0L && meDead) || inBackground) {
        if (clientDLL.int(dwForceAttack) == 5) {
            clientDLL[dwForceAttack] = 4
            Thread.sleep(1)
        }
        return@every
    }

    if (keyPressed(1)) {
        boneTrig = false
        if (!shouldShoot) {
            punchCheck = 0
            shouldShoot = true
        }

        Thread.sleep(10)
        fireWeapon()
    } else if (inTrigger) {
        if (shouldShoot) { //Finish shooting...
            if (clientDLL.int(dwForceAttack) == 5) {
                clientDLL[dwForceAttack] = 4
            }
            shouldShoot = false
            didShoot = false
        }
        punchCheck = 0
        //Let trigger handle the rest
    } else {
        clientDLL[dwForceAttack] = 4

        shouldShoot = false
        didShoot = false
        punchCheck = 0
        boneTrig = false
    }
}

fun fireWeapon() {
    val shotsFired = me.shotsFired()
    if (shotsFired > 0) {
        didShoot = true
    }

    updateCursorEnable()
    if (cursorEnable) return

    var shouldAuto = false

    if (curSettings["AUTOMATIC_WEAPONS"].strToBool() && !meCurWep.automatic && meCurWep.gun && curSettings["ENABLE_AIM"].strToBool()) {
        shouldAuto = automaticWeapons()

        if (!didShoot) { //Skip first delay
            shouldAuto = true
        }

        if (clientDLL.int(dwForceAttack) == 5) {
            clientDLL[dwForceAttack] = 4
        }

        if (!shouldAuto) {
            return
        }
    }

    val backtrackOnKey = curSettings["ENABLE_BACKTRACK_ON_KEY"].strToBool()
    val backtrackKeyPressed = keyPressed(curSettings["BACKTRACK_KEY"].toInt())

    if (((curSettings["ENABLE_BACKTRACK"].strToBool() && !curWepOverride) || (curWepOverride && curWepSettings.tBacktrack)) && ((!backtrackOnKey || (backtrackOnKey && backtrackKeyPressed)))) {
        if (shouldAuto || (!shouldAuto && !didShoot) || meCurWep.automatic) {
            if (attemptBacktrack()) {
                if (!shouldAuto) {
                    didShoot = true
                }
                return
            }
        }
    }

    if (shouldAuto) {
        clientDLL[dwForceAttack] = 6
    } else if (!didShoot || meCurWep.automatic) {
        if (!meCurWep.automatic && me.shotsFired() > 0) { //Dont use shotsFired var, this can be different
            return
        }

        didShoot = true
        clientDLL[dwForceAttack] = 5
    }
}