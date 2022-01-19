package club.newb.open.camera

import android.Manifest.permission.CAMERA
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import com.vmadalin.easypermissions.models.PermissionRequest
import java.util.jar.Manifest

private const val TAG = "MainActivity"
private const val REQUEST_CODE_CAMERA_PERMISSION = 123
private const val REQUEST_CODE_STORAGE_PERMISSION = 124
private const val REQUEST_CODE_LOCATION_AND_CONTACTS_PERMISSION = 125
private const val REQUEST_VIDEO_CAPTURE = 1

class MainActivity : AppCompatActivity() , EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.openCamera).setOnClickListener {
            onClickRequestPermissionCameraButton()
        }
    }

    @AfterPermissionGranted(REQUEST_CODE_CAMERA_PERMISSION)
    private fun onClickRequestPermissionCameraButton() {
        if (hasCameraPermission()) {
            // Have permission, do things!
            dispatchTakeVideoIntent()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.permission_camera_rationale_message),
                REQUEST_CODE_CAMERA_PERMISSION,
                CAMERA
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Log.d(TAG, getString(R.string.log_permissions_denied, requestCode, perms.size))
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.d(TAG, getString(R.string.log_permissions_granted, requestCode, perms.size))

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onRationaleAccepted(requestCode: Int) {
        Log.d(TAG, getString(R.string.log_permission_rationale_accepted, requestCode))
    }

    override fun onRationaleDenied(requestCode: Int) {
        Log.d(TAG, getString(R.string.log_permission_rationale_denied, requestCode))
    }

    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, CAMERA)
    }

    private fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(packageManager)?.also {
                Log.e(TAG, "dispatchTakeVideoIntent: start activity" )
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
            }
        }
    }
}