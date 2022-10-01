package rat.swizko.ui.uiHelpers

import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.Tooltip.removeTooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.swizko.curLocale
import rat.swizko.curSettings
import rat.swizko.dbg
import rat.swizko.scripts.visuals.disableAllEsp
import rat.swizko.ui.changed
import rat.swizko.ui.tabs.*
import rat.swizko.utils.generalUtil.boolToStr
import rat.swizko.utils.generalUtil.strToBool

class VisCheckBoxCustom(mainText: String, varName: String, visibleText: Boolean = true) : VisCheckBox(mainText) {
    private val variableName = varName
    private val showText = visibleText
    private var hasTooltip = false

    init {
        update()

        updateTooltip()

        changed { _, _ ->
            curSettings[variableName] = isChecked.boolToStr()
            //CheckBoxes are the only things to disable/enable other settings, call all updates on change
                //Move to update() ?
            updateDisableRCrosshair()
            updateDisableRcsSmoothing()
            updateDisableEsp()
            updateDisableAim()
            updateDisableTrig()
            updateDisableBacktrack()
            disableAllEsp() //Nothing bad could come from this....??
            true
        }
    }

    fun update() {
        if (curSettings["CURRENT_LOCALE"] != "" && showText) { //Only update locale if we have one
            if (dbg && curLocale[variableName].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $variableName is missing!")
            }
            setText(curLocale[variableName])
        }

        isChecked = curSettings[variableName].strToBool()

        updateTooltip()
    }

    private fun updateTooltip() {
        if (curSettings["MENU_TOOLTIPS"].strToBool()) {
            if (curLocale["${variableName}_TOOLTIP"] != "") {
                if (!hasTooltip) {
                    Tooltip.Builder(curLocale["${variableName}_TOOLTIP"]).target(this).build()
                    hasTooltip = true
                    if (dbg) {
                        println("[DEBUG] Added tooltip to $variableName")
                    }
                }
            }
        } else {
            if (hasTooltip) {
                removeTooltip(this)
                hasTooltip = false
            }
        }
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}