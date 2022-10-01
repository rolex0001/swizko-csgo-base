package rat.swizko.scripts

import rat.swizko.curSettings
import rat.swizko.game.entity.kills
import rat.swizko.game.me
import rat.swizko.robot
import rat.swizko.scripts.aim.meDead
import rat.swizko.utils.every
import rat.swizko.utils.generalUtil.strToBool

private var totalKills = me.kills()

fun killBind() = every(600, inGameCheck = true) {
    if (!curSettings["KILL_BIND"].strToBool() || me <= 0 || meDead) return@every

    val curKills = me.kills()
    if (curKills != totalKills) {
        totalKills = curKills
        robot.keyPress(curSettings["KILL_BIND_KEY"].toInt())
        robot.keyRelease(curSettings["KILL_BIND_KEY"].toInt())
    }
}