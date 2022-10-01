package rat.swizko.scripts

import org.jire.arrowhead.keyPressed
import rat.swizko.curSettings
import rat.swizko.game.CSGO.clientDLL
import rat.swizko.game.CSGO.csgoEXE
import rat.swizko.game.angle
import rat.swizko.game.clientState
import rat.swizko.game.entity.*
import rat.swizko.game.me
import rat.swizko.game.netvars.NetVarOffsets.iCrossHairID
import rat.swizko.game.offsets.ClientOffsets
import rat.swizko.game.offsets.ClientOffsets.dwForceAttack
import rat.swizko.safeToInt
import rat.swizko.scripts.aim.*
import rat.swizko.settings.AIM_KEY
import rat.swizko.settings.DANGER_ZONE
import rat.swizko.settings.MENUTOG
import rat.swizko.utils.every
import rat.swizko.utils.extensions.uint
import rat.swizko.utils.generalUtil.strToBool
import rat.swizko.utils.inGame

var inTrigger = false
private var triggerShots = 0

fun triggerBot() = every(5, inGameCheck = true) {
    //Don't run if not needed
    if (DANGER_ZONE || meDead || !inGame || MENUTOG || !meCurWep.gun || !curSettings["ENABLE_TRIGGER"].strToBool() || !haveAimSettings) { //Precheck
        inTrigger = false
        triggerShots = 0
        return@every
    }

    inTrigger = false //go and do the 2 step

    val initDelay = if (curWepOverride) curWepSettings.tBTrigInitDelay else curSettings[curWepCategory + "_TRIGGER_INIT_SHOT_DELAY"].safeToInt("Trig init delay $curWepCategory")
    val shotDelay = if (curWepOverride) curWepSettings.tBTrigPerShotDelay else curSettings[curWepCategory + "_TRIGGER_PER_SHOT_DELAY"].safeToInt("Trig per delay $curWepCategory")
    val bFOV = curSettings["TRIGGER_FOV"].toFloat()
    val bINFOV = curSettings["TRIGGER_USE_FOV"].strToBool()
    val bINCROSS = curSettings["TRIGGER_USE_INCROSS"].strToBool()
    val bAIMBOT = curSettings["TRIGGER_USE_AIMBOT"].strToBool()
    val bBACKTRACK = curSettings["TRIGGER_USE_BACKTRACK"].strToBool()

    if (curSettings[curWepCategory + "_TRIGGER"].strToBool() || (curWepOverride && curWepSettings.tBoneTrig)) { //If trigger is enabled for current weapon
        //Scope check
        if (curWepCategory == "SNIPER") { //If we are holding a sniper
            if ((curSettings["ENABLE_SCOPED_ONLY"].strToBool() && !curWepOverride) || (curWepOverride && curWepSettings.tScopedOnly)) { //Scoped only check
                if (!me.isScoped()) {
                    //Reset
                    inTrigger = false
                    triggerShots = 0
                    return@every
                }
            }
        }

        val useDelay = if (triggerShots > 0) { shotDelay } else { initDelay }

        //Trigger precheck
        if (meCurWepEnt.bullets() <= 0 || keyPressed(AIM_KEY)) { //Can shoot check???
            inTrigger = false
            triggerShots = 0
            return@every
        }

        //Trigger key check
        if (curSettings["TRIGGER_ENABLE_KEY"].strToBool() && !keyPressed(curSettings["TRIGGER_KEY"].safeToInt("Trig key"))) {
            inTrigger = false
            triggerShots = 0
            return@every
        }

        if (bINCROSS) { //If we should check in cross
            val iC = getIncross(me)
            if (iC > 0) {
                //Shoot
                trigQueueShot(useDelay, bAIMBOT, backtrack = false, backtrackFallback = false)
                return@every
            }
        }

        var canFOV = false
        if (bINFOV) { //If we should check in fov
            val currentAngle = clientState.angle()
            val position = me.position()
            val target = findTarget(position, currentAngle, false, bFOV, -2)
            if (target > 0) {
                if (!target.dead() && !target.isProtected()) {
                    canFOV = true
                }
            }
        }

        if (bBACKTRACK) { //If we should check backtrack
            if (bestBacktrackTarget > 0) {
                if (bestBacktrackTarget.canShoot()) {
                    //Shoot
                    trigQueueShot(useDelay, bAIMBOT, bBACKTRACK, canFOV)
                    return@every
                }
            }
        }

        if (canFOV) { //Cant backtrack
            trigQueueShot(useDelay, bAIMBOT, backtrack = false, backtrackFallback = false)
            return@every
        }

        //If not in cross, not in backtrack, and not in fov
        //Reset
        inTrigger = false
        triggerShots = 0
    }
}

private fun trigQueueShot(delay: Int, aimbot: Boolean = false, backtrack: Boolean = false, backtrackFallback: Boolean = false) {
    inTrigger = true

    if (delay > 0) {
        Thread.sleep(delay.toLong())
    }

    //Trigger key check
    if (curSettings["TRIGGER_ENABLE_KEY"].strToBool() && !keyPressed(curSettings["TRIGGER_KEY"].toInt())) {
        inTrigger = false
        triggerShots = 0
        return
    }

    triggerShots++
    triggerShoot(aimbot, backtrack, backtrackFallback)
}

private fun triggerShoot(aimbot: Boolean = false, backtrack: Boolean = false, backtrackFallback: Boolean = false) {
    var didBacktrack = false

    if (backtrack) {
        didBacktrack = attemptBacktrack()
        boneTrig = didBacktrack
    }

    if (!backtrack || (backtrack && !didBacktrack && backtrackFallback)) {
        boneTrig = aimbot
        clientDLL[dwForceAttack] = 6 //HandleFireKey.kt
    }

    Thread.sleep(10)
    //boneTrig = false
}

private fun getIncross(ent: Entity): Entity {
    val inCross = csgoEXE.uint(ent + iCrossHairID)

    if (inCross > 0) {
        val entity = clientDLL.uint(ClientOffsets.dwEntityList + (inCross * 0x10) - 0x10)
        if (!entity.inMyTeam() && !entity.isProtected() && !entity.dead()) {
            return entity
        }
    }

    return 0L
}
