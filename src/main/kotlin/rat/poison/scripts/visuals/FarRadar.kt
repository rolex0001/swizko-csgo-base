package rat.swizko.scripts.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import org.lwjgl.opengl.GL11.glEnable
import rat.swizko.curSettings
import rat.swizko.game.CSGO.clientDLL
import rat.swizko.game.CSGO.csgoEXE
import rat.swizko.game.entity.*
import rat.swizko.game.forEntities
import rat.swizko.game.me
import rat.swizko.game.offsets.ClientOffsets
import rat.swizko.game.offsets.ClientOffsets.dwRadarBase
import rat.swizko.game.worldToScreen
import rat.swizko.overlay.App
import rat.swizko.scripts.aim.meDead
import rat.swizko.settings.DANGER_ZONE
import rat.swizko.utils.Vector
import rat.swizko.utils.generalUtil.strToBool
import rat.swizko.utils.inGame
import kotlin.math.abs

data class FarPlayer(val pos: Vector = Vector(), var alpha: Float = 0F)
private var farPlayerRecords = Array(64) { FarPlayer() }

fun farRadar() = App {
    if (!inGame || !curSettings["BOX_FAR_RADAR"].strToBool() || meDead) return@App

    var dwRadar = clientDLL.int(dwRadarBase)
    dwRadar = csgoEXE.int(dwRadar + 0x74)

    forEntities(EntityType.CCSPlayer) { //This will probably require more prechecks
        val ent = it.entity

        //Prechecks
        if (ent == me || me.team() == ent.team() || DANGER_ZONE || !ent.dormant()) return@forEntities
        if (ent.dead()) return@forEntities

        val entID = csgoEXE.int(ent + ClientOffsets.dwIndex)

        val mem = csgoEXE.read(dwRadar + (0x174 * (entID + 1)) - 0x3C, 237) ?: return@forEntities

        val pos = Vector(mem.getFloat(0), mem.getFloat(4), mem.getFloat(8))
        val health = mem.getInt(0x50)

        if (pos.x != 0.0F && health > 0 && health <= 100 && entID <= 64) {
            if (farPlayerRecords[entID].alpha <= 0F && farPlayerRecords[entID].pos != pos) {
                farPlayerRecords[entID] = FarPlayer(pos, 1F)
            }
        }
    }

    farPlayerRecords.forEach {
        if (it.alpha > 0F) {
            val w2s1 = Vector()
            val w2s2 = Vector()

            if (worldToScreen(it.pos, w2s1) && worldToScreen(Vector(it.pos.x, it.pos.y, it.pos.z - 75F), w2s2)) {
                if (shapeRenderer.isDrawing) shapeRenderer.end()
                shapeRenderer.begin()

                glEnable(GL20.GL_BLEND)

                val h = w2s1.y - w2s2.y
                val w = abs(h / 5F * 2F)

                shapeRenderer.color = Color(1F, 1F, 1F, it.alpha)
                shapeRenderer.rect(w2s1.x - w/2F, w2s1.y, w, h)

                shapeRenderer.end()
            }

            it.alpha -= .01F
        }
    }
}