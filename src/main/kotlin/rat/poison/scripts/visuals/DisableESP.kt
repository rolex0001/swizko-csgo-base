package rat.swizko.scripts.visuals

import rat.swizko.game.CSGO
import rat.swizko.game.Color
import rat.swizko.game.entity.EntityType
import rat.swizko.game.forEntities
import rat.swizko.game.me
import rat.swizko.game.netvars.NetVarOffsets
import rat.swizko.game.offsets.ClientOffsets
import rat.swizko.game.offsets.EngineOffsets
import rat.swizko.utils.extensions.uint
import rat.swizko.utils.inGame
import java.lang.Float.floatToIntBits

//Change to construct entities at call to prevent crashing?

internal fun disableAllEsp() {
    if (!inGame) return

    val cWhite = Color(255, 255, 255, 1.0)

    val clientVModEnt = CSGO.csgoEXE.uint(CSGO.clientDLL.address + ClientOffsets.dwEntityList + (((CSGO.csgoEXE.uint(CSGO.csgoEXE.uint(CSGO.clientDLL.address + ClientOffsets.dwLocalPlayer) + NetVarOffsets.m_hViewModel)) and 0xFFF) - 1) * 16)
    CSGO.csgoEXE[clientVModEnt + 0x70] = 255.toByte()
    CSGO.csgoEXE[clientVModEnt + 0x71] = 255.toByte()
    CSGO.csgoEXE[clientVModEnt + 0x72] = 255.toByte()
    CSGO.engineDLL[EngineOffsets.dwModelAmbientMin] = floatToIntBits(0F) xor (CSGO.engineDLL.address + EngineOffsets.dwModelAmbientMin - 0x2C).toInt()

    forEntities {
        val entity = it.entity
        val type = it.type
        val glowAddress = it.glowAddress
        if (entity <= 0 || me == entity || glowAddress <= 0) return@forEntities

        if (type != EntityType.NULL) {
            when (type) {
                EntityType.CCSPlayer -> {
                    glowAddress.glow(cWhite, -1); entity.chams(cWhite); entity.hideOnRadar()
                }
                EntityType.CPlantedC4 -> glowAddress.glow(cWhite, -1)
                EntityType.CC4 -> glowAddress.glow(cWhite, -1)
                else -> {
                    if (type.weapon || type.grenade) {
                        glowAddress.glow(cWhite, -1)
                    }
                }
            }
        }

        return@forEntities
    }
}