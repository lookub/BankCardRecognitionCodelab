package com.hms.codelab.mlkit.bankcardrecognition

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.hms.codelab.mlkit.bankcardrecognition.util.Utils

class MainActivity : AppCompatActivity() {

    //region variablesAndObjects
    private val TAG: String = MainActivity::class.java.simpleName

    private var unbinder: Unbinder? = null

    // TODO : create -> private lateinit var bcrCaptureCallback: MLBcrCapture.Callback
    //private lateinit var bcrCaptureCallback: MLBcrCapture.Callback

    private val permissionCodeCameraAndStorage = 2
    private var permissionRequestCameraAndStorage = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    @Nullable
    @BindView(R.id.ivCardImage)
    lateinit var ivCardImage: ImageView

    @Nullable
    @BindView(R.id.btnTakePhoto)
    lateinit var btnTakePhoto: Button

    @Nullable
    @BindView(R.id.ivDelete)
    lateinit var ivDelete: ImageView

    @Nullable
    @BindView(R.id.clResults)
    lateinit var clResults: ConstraintLayout

    @Nullable
    @BindView(R.id.ivCardNumberImage)
    lateinit var ivCardNumberImage: ImageView

    @Nullable
    @BindView(R.id.tvCardType)
    lateinit var tvCardType: TextView

    @Nullable
    @BindView(R.id.tvCardNumberAll)
    lateinit var tvCardNumberAll: TextView

    @Nullable
    @BindView(R.id.tvExpireDate)
    lateinit var tvExpireDate: TextView

    @Nullable
    @BindView(R.id.tvCardNumber1)
    lateinit var tvCardNumber1: TextView

    @Nullable
    @BindView(R.id.tvCardNumber2)
    lateinit var tvCardNumber2: TextView

    @Nullable
    @BindView(R.id.tvCardNumber3)
    lateinit var tvCardNumber3: TextView

    @Nullable
    @BindView(R.id.tvCardNumber4)
    lateinit var tvCardNumber4: TextView


    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        unbinder = ButterKnife.bind(this)

        createAndInitializeBcrCaptureCallback()
    }


    private fun createAndInitializeBcrCaptureCallback() {
        // TODO : create and initialize of MLBcrCapture.Callback
        // TODO : call displaySuccessAnalyseResults method with MLBcrCaptureResult param in Callback onSuccess , to show recognised details
    }

    private fun startMLBcrCaptureCameraStream() {
        // TODO : create and initialize of MLBcrCaptureConfig.Factory
        // TODO : custom initialize of MLBcrCaptureConfig.Factory
        // TODO : create and initialize of MBcrCapture
    }

    @OnClick(
        R.id.ivInfo,
        R.id.ivCardImage,
        R.id.btnTakePhoto,
        R.id.tvCardNumberAll,
        R.id.ivCardNumberImage,
        R.id.tvCardNumber1,
        R.id.tvCardNumber2,
        R.id.tvCardNumber3,
        R.id.tvCardNumber4,
        R.id.ivDelete
    )
    fun onItemClick(v: View) {
        when (v.id) {
            R.id.ivInfo -> Utils.openWebPage(this, resources.getString(R.string.link_trs_bcr))
            R.id.ivCardImage, R.id.btnTakePhoto -> checkAndRequestPermissions()
            R.id.tvCardNumberAll, R.id.ivCardNumberImage -> copyTextToClipboard(tvCardNumberAll)
            R.id.tvCardNumber1 -> copyTextToClipboard(tvCardNumber1)
            R.id.tvCardNumber2 -> copyTextToClipboard(tvCardNumber2)
            R.id.tvCardNumber3 -> copyTextToClipboard(tvCardNumber3)
            R.id.tvCardNumber4 -> copyTextToClipboard(tvCardNumber4)
            R.id.ivDelete -> clearAndHideViews()
        }
    }

    private fun checkAndRequestPermissions() {
        // TODO : call ActivityCompat.requestPermissions with checkAndRequestPermissions method
        ActivityCompat.requestPermissions(
            this,
            permissionRequestCameraAndStorage,
            permissionCodeCameraAndStorage
        )
    }

    // TODO : call startMLBcrCaptureCameraStream function if permissions were has got granted
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "PermissionsResult : requestCode : $requestCode")
        if (requestCode == permissionCodeCameraAndStorage) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                // TODO : call startMLBcrCaptureCameraStream function if permissions were has got granted
                startMLBcrCaptureCameraStream()
            } else {
                Log.d(TAG, "onRequestPermissionsResult : CameraPermission  was NOT GRANTED")
                Utils.showDialogPermissionWarning(
                    this,
                    getString(R.string.need_camera_and_storage_permission),
                    getString(R.string.go_to_permission_setting),
                    R.drawable.icon_camera,
                    getString(R.string.you_can_not_use_ml_bcr_wihtout_permission),
                    getString(R.string.yes_go), getString(R.string.cancel)
                )
            }
        }
    }

    // ------------------------------------------------------------------------------------------ //

    private fun copyTextToClipboard(clickedView: TextView) {
        // TODO : use Context.CLIPBOARD_SERVICE and set clipboard.setPrimaryClip with created ClipData.newPlainText object
        val clickedText: String = clickedView.text.toString()
        val clipboard = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("copiedCardNumber", clickedText)
        clipboard.setPrimaryClip(clip)
    }


    // TODO : Set and edit view with MLBcrCaptureResult object detail information in displaySuccessAnalyseResults function to show recognised card details
    //private fun displaySuccessAnalyseResults(result: MLBcrCaptureResult) {
    //    val analyseResults: String = editFormatCardAnalyseResult(result).toString()
    //    Log.i(TAG, "Success AnalyseResults : $analyseResults")
    //    Utils.createVibration(applicationContext, 200)
    //    clResults.visibility = View.VISIBLE
    //    ivDelete.visibility = View.VISIBLE
    //    ivCardImage.setImageBitmap(result.originalBitmap)
    //    ivCardNumberImage.setImageBitmap(result.numberBitmap)
    //    tvCardType.text = result.organization
    //    tvExpireDate.text = result.expire
    //    tvCardNumberAll.text = result.number
    //    tvCardNumber1.text = result.number.substring(0, 4)
    //    tvCardNumber2.text = result.number.substring(4, 8)
    //    tvCardNumber3.text = result.number.substring(8, 12)
    //    tvCardNumber4.text = result.number.substring(12, 16)
    //}

    //private fun editFormatCardAnalyseResult(result: MLBcrCaptureResult): String? {
    //    val resultBuilder = StringBuilder()
    //    resultBuilder.append("Number：")
    //    resultBuilder.append(result.number)
    //    resultBuilder.append(System.lineSeparator())
//
    //    resultBuilder.append("Issuer：")
    //    resultBuilder.append(result.issuer)
    //    resultBuilder.append(System.lineSeparator())
//
    //    resultBuilder.append("Expire: ")
    //    resultBuilder.append(result.expire)
    //    resultBuilder.append(System.lineSeparator())
//
    //    resultBuilder.append("Type: ")
    //    resultBuilder.append(result.type)
    //    resultBuilder.append(System.lineSeparator())
//
    //    resultBuilder.append("Organization: ")
    //    resultBuilder.append(result.organization)
    //    resultBuilder.append(System.lineSeparator())
//
    //    return resultBuilder.toString()
    //}

    private fun displayFailureAnalyseResults(msg: String) {
        Log.i(TAG, "Failure AnalyseResults : $msg")
        Utils.createVibration(applicationContext, 400)
        Utils.showToastMessage(applicationContext, "Failed AnalyseResults : $msg")
        clearAndHideViews()
    }

    private fun clearAndHideViews() {
        ivDelete.visibility = View.GONE
        clResults.visibility = View.GONE
        ivCardImage.setImageBitmap(
            BitmapFactory.decodeResource(
                applicationContext.resources,
                R.drawable.icon_card1
            )
        )
        ivCardNumberImage.setImageBitmap(null)
        tvCardNumberAll.text = null
        tvCardType.text = null
        tvExpireDate.text = null
        tvCardNumber1.text = null
        tvCardNumber2.text = null
        tvCardNumber3.text = null
        tvCardNumber4.text = null
    }


    // ------------------------------------------------------------------------------------------ //

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }

}