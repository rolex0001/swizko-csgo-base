package rat.swizko.scripts.visuals

import rat.swizko.curSettings
import rat.swizko.game.entity.*
import rat.swizko.game.forEntities
import rat.swizko.game.me
import rat.swizko.settings.DANGER_ZONE
import rat.swizko.utils.Vector
import rat.swizko.utils.distanceTo
import rat.swizko.utils.every
import rat.swizko.utils.generalUtil.strToBool

internal fun radarEsp() = every(100, inGameCheck = true) {
    if (!curSettings["RADAR_ESP"].strToBool() || DANGER_ZONE) return@every

    if (curSettings["LEGIT_RADAR"].strToBool()) {
        val entsChecked = mutableListOf<Long>()
        for (i in footSteps.indices) {
            val ent = footSteps[i].ent

            if (ent > 0L) {
                entsChecked.add(ent)
                if (!footSteps[i].open && footSteps[i].ttl > 0 && Vector(footSteps[i].x, footSteps[i].y, footSteps[i].z).distanceTo(me.position()) <= curSettings["LEGIT_RADAR_FOOTSTEPS_DISTANCE"].toInt()) {
                    ent.showOnRadar()
                } else {
                    ent.hideOnRadar()
                }
            }
        }

        forEntities(EntityType.CCSPlayer) {
            if (!entsChecked.contains(it.entity)) {
                it.entity.hideOnRadar()
            }
        }
    } else {
        forEntities(EntityType.CCSPlayer) {
            val entity = it.entity

            if (entity.dead() || entity == me || entity.dormant()) return@forEntities
            entity.showOnRadar()
        }
    }
}