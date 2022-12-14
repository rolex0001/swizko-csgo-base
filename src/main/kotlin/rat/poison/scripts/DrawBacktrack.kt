package rat.swizko.scripts

import com.badlogic.gdx.graphics.Color
import org.jire.arrowhead.keyPressed
import rat.swizko.curSettings
import rat.swizko.game.worldToScreen
import rat.swizko.overlay.App
import rat.swizko.scripts.aim.meCurWep
import rat.swizko.scripts.aim.meDead
import rat.swizko.settings.MENUTOG
import rat.swizko.utils.Vector
import rat.swizko.utils.generalUtil.strToBool
import rat.swizko.utils.inGame

fun drawBacktrack() = App {
    if (MENUTOG) return@App
    if (meDead) return@App
    if (!inGame || !curSettings["BACKTRACK_VISUALIZE"].strToBool() || !curSettings["ENABLE_ESP"].strToBool() || !curSettings["ENABLE_BACKTRACK"].strToBool()) return@App

    val backtrackOnKey = curSettings["ENABLE_BACKTRACK_ON_KEY"].strToBool()
    val backtrackKeyPressed = keyPressed(curSettings["BACKTRACK_KEY"].toInt())

    if (backtrackOnKey && !backtrackKeyPressed) return@App

    if (!meCurWep.gun) return@App

    for (i in 0 until 63) {
        if (btRecords[i][0].simtime == 0F) continue

        val minMaxIDX = getRangeRecords(i)

        if (minMaxIDX[0] == Int.MAX_VALUE || minMaxIDX[1] == -1) continue

        val minRecord = btRecords[i][minMaxIDX[0]]
        val maxRecord = btRecords[i][minMaxIDX[1]]

        val minHeadPos = Vector()
        val maxHeadPos = Vector()
        val minAbsPos = Vector()
        val maxAbsPos = Vector()

        if (worldToScreen(minRecord.headPos, minHeadPos) && worldToScreen(minRecord.absPos, minAbsPos) && worldToScreen(maxRecord.headPos, maxHeadPos) && worldToScreen(maxRecord.absPos, maxAbsPos)) {
            val w = (minAbsPos.y - minHeadPos.y) / 4F
            val minMidX = (minAbsPos.x + minHeadPos.x) / 2F
            val maxMidX = (maxAbsPos.x + maxAbsPos.x) / 2F

            var sign = -1

            if (minMidX > maxMidX) {
                sign = 1
            }

            val topLeft = Vector(minHeadPos.x - (w / 3F) * sign, minHeadPos.y, minHeadPos.z)
            val topRight = Vector(maxHeadPos.x + (w / 3F) * sign, maxHeadPos.y, maxHeadPos.z)

            val bottomLeft = Vector(minMidX - (w / 2F) * sign, minAbsPos.y+8F, minAbsPos.z)
            val bottomRight = Vector(maxMidX + (w / 2F) * sign, maxAbsPos.y+8F, maxAbsPos.z)

            shapeRenderer.apply {
                if (shapeRenderer.isDrawing) {
                    end()
                }

                begin()

                color = Color(1F, 1F, 1F, 1F)

                line(topLeft.x, topLeft.y, topRight.x, topRight.y)
                line(topRight.x, topRight.y, bottomRight.x, bottomRight.y)
                line(bottomRight.x, bottomRight.y, bottomLeft.x, bottomLeft.y)
                line(bottomLeft.x, bottomLeft.y, topLeft.x, topLeft.y)

                color = Color.WHITE

                end()
            }
        }

        for (j in btRecords[i]) {
            j.alpha -= .5F
        }
    }
}