package com.rw.tweaks.prefs.secure.specific

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.preference.DialogPreference
import com.rw.tweaks.R
import com.rw.tweaks.util.ISecurePreference
import com.rw.tweaks.util.ISpecificPreference
import com.rw.tweaks.util.SecurePreference
import com.rw.tweaks.util.SettingsType

class LockscreenShortcutsPref(context: Context, attrs: AttributeSet) : DialogPreference(context, attrs), ISecurePreference by SecurePreference(context), ISpecificPreference {
    override var type: SettingsType = SettingsType.SECURE
    override val keys: Array<String> = arrayOf(
        "sysui_keyguard_left",
        "sysui_keyguard_right"
    )

    init {
        key = "lockscreen_shortcuts"

        setTitle(R.string.feature_lockscreen_shortcuts)
        setSummary(R.string.feature_lockscreen_shortcuts_desc)

        dialogTitle = title
        dialogMessage = summary
        setIcon(R.drawable.lock_open)

        lowApi = Build.VERSION_CODES.O
        highApi = Build.VERSION_CODES.O_MR1
        iconColor = ContextCompat.getColor(context, R.color.pref_color_3)

        init(this)
    }
}