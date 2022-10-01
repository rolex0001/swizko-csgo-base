package rat.swizko.ui.uiHelpers.overrideWeaponsUI

import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.swizko.oWeapon
import rat.swizko.toLocale
import rat.swizko.ui.changed
import rat.swizko.ui.uiPanelTables.weaponOverrideSelected
import rat.swizko.ui.uiPanels.overridenWeapons
import rat.swizko.utils.generalUtil.strToBool

private val flatAimIdx = getOverrideVarIndex(oWeapon().toString(), "tFlatAim")
private val pathAimIdx = getOverrideVarIndex(oWeapon().toString(), "tPathAim")

class OverrideVisCheckBoxCustom(mainText: String, varName: String) : VisCheckBox(mainText) {
    private val variableName = varName
    private val defaultText = mainText
    private val varIdx = getOverrideVarIndex(oWeapon().toString(), variableName)

    init {
        update()
        changed { _, _ ->
            if (varName == "tFlatAim" && isChecked) {
                setOverrideVar(weaponOverrideSelected, pathAimIdx, false)
                overridenWeapons.enablePathAim.isChecked = false
            } else if (varName == "tPathAim" && isChecked) {
                setOverrideVar(weaponOverrideSelected, flatAimIdx, false)
                overridenWeapons.enableFlatAim.isChecked = false
            }

            setOverrideVar(weaponOverrideSelected, varIdx, isChecked)

            true
        }
    }

    fun update() {
        val tmpText = variableName.toLocale()
        setText(if (tmpText.isBlank()) defaultText else tmpText )
        isChecked = getOverrideVar(weaponOverrideSelected, varIdx).strToBool()
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}