package com.zacharee1.systemuituner.views

import android.content.Context
import android.os.BatteryManager
import android.provider.Settings
import android.util.AttributeSet
import android.widget.CompoundButton
import android.widget.ScrollView
import com.zacharee1.systemuituner.util.prefManager
import com.zacharee1.systemuituner.util.writeGlobal
import kotlinx.android.synthetic.main.keep_device_plugged_dialog.view.*

class KeepOnPlugged(context: Context, attrs: AttributeSet) : ScrollView(context, attrs) {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val ac = on_ac
        val usb = on_usb
        val wireless = on_wireless
        val current = Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.STAY_ON_WHILE_PLUGGED_IN,
            0
        )

        ac.isChecked = current and BatteryManager.BATTERY_PLUGGED_AC != 0
        usb.isChecked = current and BatteryManager.BATTERY_PLUGGED_USB != 0
        wireless.isChecked = current and BatteryManager.BATTERY_PLUGGED_WIRELESS != 0

        val listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val result = when (buttonView) {
                ac -> (if (isChecked) BatteryManager.BATTERY_PLUGGED_AC else 0) or
                        (if (usb.isChecked) BatteryManager.BATTERY_PLUGGED_USB else 0) or
                        (if (wireless.isChecked) BatteryManager.BATTERY_PLUGGED_WIRELESS else 0)
                usb -> (if (ac.isChecked) BatteryManager.BATTERY_PLUGGED_AC else 0) or
                        (if (isChecked) BatteryManager.BATTERY_PLUGGED_USB else 0) or
                        (if (wireless.isChecked) BatteryManager.BATTERY_PLUGGED_WIRELESS else 0)
                wireless -> (if (ac.isChecked) BatteryManager.BATTERY_PLUGGED_AC else 0) or
                        (if (usb.isChecked) BatteryManager.BATTERY_PLUGGED_USB else 0) or
                        (if (isChecked) BatteryManager.BATTERY_PLUGGED_WIRELESS else 0)
                else -> current
            }

            context.prefManager.putInt(Settings.Global.STAY_ON_WHILE_PLUGGED_IN, result)
            context.writeGlobal(Settings.Global.STAY_ON_WHILE_PLUGGED_IN, result)
        }

        ac.setOnCheckedChangeListener(listener)
        usb.setOnCheckedChangeListener(listener)
        wireless.setOnCheckedChangeListener(listener)
    }
}