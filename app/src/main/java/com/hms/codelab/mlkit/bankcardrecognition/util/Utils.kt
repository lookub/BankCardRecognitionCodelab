package com.hms.codelab.mlkit.bankcardrecognition.util

import android.app.Activity
import android.content.*
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Vibrator
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

open class Utils {

    companion object {

        private val TAG = Utils::class.java.simpleName

        // TODO : edit this getting value method on property file.
        var APIKEY =
            "CgB6e3x9wOq4k7eAVGVpgD/jMfIxOyRb/mlj5DCe3Cd6f/29IjWuvrVzOb9uHak2obK1NVZthD25IDGNs2MfZjiv"

        fun showToastMessage(
            appContext: Context?,
            message: String?
        ) {
            Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
        }

        /**
         * @param millisecond : long type vibrate time
         */
        fun createVibration(
            context: Context,
            millisecond: Long
        ) {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(millisecond)
        }


        fun haveNetworkConnection(appContext: Context): Boolean {
            var haveConnected = false
            val cm =
                appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfos = cm.allNetworkInfo
            for (ni in networkInfos) {
                if (ni != null && ni.isConnected) {
                    haveConnected = true
                }
            }
            return haveConnected
        }

        fun openWebPage(activity: Activity, url: String?) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            activity.startActivity(browserIntent)
        }

        /**
         * @param context
         * @param copyLabel
         * @param textForCopy
         */
        fun copyTextToClipboard(
            context: Context,
            copyLabel: String?,
            textForCopy: String?
        ) {
            val clipboard =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(copyLabel, textForCopy)
            clipboard.setPrimaryClip(clip)
            Log.d(TAG, "copyTextToClipboard : primaryClip : " + clipboard.primaryClip.toString())
        }


        /**
         *
         * @param context
         * @param dialogTitle
         * @param dialogMessage
         * @param iconId        : such as R.drawable.icon_settings
         * @param cancelMessage
         * @param positiveText
         * @param negativeText
         */
        fun showDialogPermissionWarning(
            context: Context, dialogTitle: String?,
            dialogMessage: String?, iconId: Int, cancelMessage: String?,
            positiveText: String?, negativeText: String?
        ) {
            showAlertDialog(context,
                dialogTitle,
                dialogMessage,
                iconId,
                positiveText,
                negativeText,
                DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", context.packageName, null)
                            intent.data = uri
                            context.startActivity(intent)
                        }
                        DialogInterface.BUTTON_NEUTRAL ->
                            // proceed with logic by disabling the related features or quit the app.
                            showToastMessage(context, cancelMessage)
                    }
                }
            )
        }


        private fun showAlertDialog(
            context: Context?,
            title: String?,
            message: String?,
            iconId: Int,
            positiveText: String?,
            negativeText: String?,
            okListener: DialogInterface.OnClickListener?
        ) {
            AlertDialog.Builder(context!!)
                .setTitle(title)
                .setMessage(message)
                .setIcon(iconId)
                .setPositiveButton(positiveText, okListener)
                .setNeutralButton(negativeText, okListener)
                .create()
                .show()
        }


    }


}