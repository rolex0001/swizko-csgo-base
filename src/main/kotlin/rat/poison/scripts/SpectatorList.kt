package rat.swizko.scripts

import rat.swizko.curSettings
import rat.swizko.game.CSGO.csgoEXE
import rat.swizko.game.entity.*
import rat.swizko.game.forEntities
import rat.swizko.game.me
import rat.swizko.game.netvars.NetVarOffsets.m_hObserverTarget
import rat.swizko.game.offsets.ClientOffsets.dwIndex
import rat.swizko.overlay.App.haveTarget
import rat.swizko.overlay.opened
import rat.swizko.ui.uiPanels.specListText
import rat.swizko.utils.every
import rat.swizko.utils.extensions.readIndex
import rat.swizko.utils.generalUtil.strToBool

internal fun spectatorList() = every(100, inGameCheck = true) {
    if (!curSettings["SPECTATOR_LIST"].strToBool() || !curSettings["MENU"].strToBool()) {
        return@every
    }

    var spectators = ""
    var entCount = 1

    val playerSpecTarget = csgoEXE.readIndex(me + dwIndex)

    forEntities(EntityType.CCSPlayer) {
        val entity = it.entity

        if (entity.isSpectating() && !entity.hltv() && !entity.dormant()) {
            val entSpecTarget = csgoEXE.readIndex(entity + m_hObserverTarget)
            val entName = entity.name()

            if (entSpecTarget > -1 && entSpecTarget == playerSpecTarget) {
                if (!spectators.contains(entName)) {
                    spectators += "$entCount. $entName\n"
                    entCount++
                }
            }
        }
        return@forEntities
    }

    if (opened && haveTarget) {
        specListText.setText(spectators)
    }
}

// Move to normal spot whenever
fun Int.toIndex() = ((this and 0xFFF) - 1).run {
    if (this == 4094) -1 else this
}
