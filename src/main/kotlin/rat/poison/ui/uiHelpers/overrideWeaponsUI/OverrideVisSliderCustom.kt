package rat.swizko.ui.uiHelpers.overrideWeaponsUI

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisTable
import rat.swizko.curLocale
import rat.swizko.curSettings
import rat.swizko.dbg
import rat.swizko.oWeapon
import rat.swizko.ui.changed
import rat.swizko.ui.uiPanelTables.weaponOverrideSelected
import kotlin.math.pow
import kotlin.math.round

class OverrideVisSliderCustom(mainText: String, varName: String, varMin: Float, varMax: Float, stepSize: Float, intVal: Boolean, dec: Int = 2, width1: Float = 225F, width2: Float = 225F) : VisTable() {
    private val labelText = mainText
    private val variableName = varName
    private val varIdx = getOverrideVarIndex(oWeapon().toString(), variableName)
    private val isInt = intVal
    private val rnd = 10.0.pow(dec)
    private val w1 = width1
    private val w2 = width2

    private val sliderLabel = VisLabel("$labelText: " + getOverrideVar(weaponOverrideSelected, varIdx))
    private val sliderBar = VisSlider(varMin, varMax, stepSize, false)

    init {
        update()

        sliderBar.changed { _, _ ->
            val sliderVal : Any = if (isInt) {
                sliderBar.value.toInt()
            } else {
                round(sliderBar.value * rnd)/rnd
            }

            setOverrideVar(weaponOverrideSelected, varIdx, sliderVal)

            if (curSettings["CURRENT_LOCALE"] != "") { //Only update locale if we have one
                if (dbg && curLocale[variableName].isBlank()) {
                    println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $variableName is missing!")
                }
                sliderLabel.setText("${curLocale[variableName]}: $sliderVal")
            } else { //User our default input
                sliderLabel.setText("$labelText: $sliderVal")
            }
        }

        add(sliderLabel).width(w1)
        add(sliderBar).width(w2)
    }

    fun update() {
        sliderBar.value = getOverrideVar(weaponOverrideSelected, varIdx).toString().toFloat()//curSettings[variableName].toFloat()

        val sliderVal : Any = if (isInt) {
            sliderBar.value.toInt()
        } else {
            round(sliderBar.value * rnd)/rnd
        }

        if (curSettings["CURRENT_LOCALE"] != "") { //Only update locale if we have one
            if (dbg && curLocale[variableName].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $variableName is missing!")
            }
            sliderLabel.setText("${curLocale[variableName]}: $sliderVal")
        } else { //User our default input
            sliderLabel.setText("$labelText: $sliderVal")
        }
    }

    fun disable(bool: Boolean, col: Color) {
        sliderBar.isDisabled = bool
        sliderLabel.color = col
    }
}