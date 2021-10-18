package com.yinp.proappkotlin.utils

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.SELECT_PHOTO
import com.yinp.proappkotlin.TAKE_PHOTO
import com.yinp.proappkotlin.databinding.DialogSelectTakePhotoBinding
import com.yinp.proappkotlin.view.PermissionDialog
import com.yinp.tools.fragment_dialog.BaseDialogFragment
import com.yinp.tools.fragment_dialog.CommonDialogFragment
import com.yinp.tools.fragment_dialog.DialogFragmentHolder
import com.yinp.tools.fragment_dialog.ViewConvertListener
import java.io.File
import java.io.IOException

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.proappkotlin.utils
 * describe  :
 */
class SelectTakePhoto {
    var imageUri: Uri? = null
    var type = 0

    fun selectType(activity: FragmentActivity, requestCode: Int) {
        CommonDialogFragment.newInstance(activity).setLayout(
            DialogSelectTakePhotoBinding.inflate(
                LayoutInflater.from(activity),
                null,
                false
            )
        )
            .setViewConvertListener(object : ViewConvertListener() {
                override fun convertView(
                    holder: DialogFragmentHolder,
                    dialogFragment: BaseDialogFragment,
                    viewBinding: ViewBinding
                ) {
                    val binding: DialogSelectTakePhotoBinding =
                        viewBinding as DialogSelectTakePhotoBinding
                    binding.tvTakePhoto.setOnClickListener { v ->
                        getPermission(activity, TAKE_PHOTO.also {
                            type = it
                        }, requestCode, dialogFragment)
                    }
                    binding.tvPicture.setOnClickListener { v ->
                        getPermission(activity, SELECT_PHOTO.also {
                            type = it
                        }, requestCode, dialogFragment)
                    }
                    binding.tvCancel.setOnClickListener { v -> dialogFragment.dismiss() }
                }
            }).setAnimStyle(R.style.BottomDialogAnimation).setGravity(BaseDialogFragment.BOTTOM)
            .setPercent(true, false)
            .show(activity.supportFragmentManager)
    }

    fun selectType(fragment: Fragment, requestCode: Int) {
        CommonDialogFragment.newInstance(fragment.context).setLayout(
            DialogSelectTakePhotoBinding.inflate(
                LayoutInflater.from(fragment.context),
                null,
                false
            )
        )
            .setViewConvertListener(object : ViewConvertListener() {
                override fun convertView(
                    holder: DialogFragmentHolder,
                    dialogFragment: BaseDialogFragment,
                    viewBinding: ViewBinding
                ) {
                    val binding: DialogSelectTakePhotoBinding =
                        viewBinding as DialogSelectTakePhotoBinding
                    binding.tvTakePhoto.setOnClickListener { v ->
                        getPermission(fragment, TAKE_PHOTO.also {
                            type = it
                        }, requestCode, dialogFragment)
                    }
                    binding.tvPicture.setOnClickListener { v ->
                        getPermission(fragment, SELECT_PHOTO.also {
                            type = it
                        }, requestCode, dialogFragment)
                    }
                    binding.tvCancel.setOnClickListener { v -> dialogFragment.dismiss() }
                }
            }).setAnimStyle(R.style.BottomDialogAnimation).setGravity(BaseDialogFragment.BOTTOM)
            .setPercentSize(0f, 0f).show(fragment.childFragmentManager)
    }

    private fun getPermission(
        fragment: Fragment,
        type: Int,
        requestCode: Int,
        dialogFragment: BaseDialogFragment
    ) {
        val permission: Array<String> = when (type) {
            TAKE_PHOTO -> {
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            SELECT_PHOTO -> {
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            else -> {
                return
            }
        }
        XXPermissions.with(fragment.context)
            .permission(permission)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                    if (all) {
                        when (type) {
                            TAKE_PHOTO -> takePhoto(fragment, requestCode, dialogFragment)
                            SELECT_PHOTO -> SelectPhoto(
                                fragment,
                                requestCode,
                                dialogFragment
                            )
                        }
                    }
                }

                override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                    if (never) {
                        val permissionDialog = PermissionDialog(
                            fragment.requireActivity(),
                            fragment.getString(
                                R.string.per_tips,
                                fragment.getString(R.string.storage)
                            ),
                            permissions
                        )
                        permissionDialog.show()
                    }
                }
            })
    }

    private fun getPermission(
        activity: Activity,
        type: Int,
        requestCode: Int,
        dialogFragment: BaseDialogFragment
    ) {
        val permission: Array<String> = when (type) {
            TAKE_PHOTO -> {
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            SELECT_PHOTO -> {
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            else -> {
                return
            }
        }
        XXPermissions.with(activity)
            .permission(permission)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                    if (all) {
                        when (type) {
                            TAKE_PHOTO -> takePhoto(activity, requestCode, dialogFragment)
                            SELECT_PHOTO -> SelectPhoto(
                                activity,
                                requestCode,
                                dialogFragment
                            )
                        }
                    }
                }

                override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                    if (never) {
                        val permissionDialog = PermissionDialog(
                            activity,
                            activity.getString(
                                R.string.per_tips,
                                activity.getString(R.string.storage)
                            ),
                            permissions
                        )
                        permissionDialog.show()
                    }
                }
            })
    }

    private fun takePhoto(
        fragment: Fragment,
        requestCode: Int,
        dialogFragment: BaseDialogFragment
    ) {
        imageUri = null
        val file = File(fragment.requireContext().externalCacheDir, "output_image.jpg")
        try {
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        imageUri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(fragment.requireContext(), "com.yinp.proapp", file)
        } else {
            Uri.fromFile(file)
        }
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        fragment.startActivityForResult(intent, requestCode)
        dialogFragment.dismiss()
    }

    private fun takePhoto(
        activity: Activity,
        requestCode: Int,
        dialogFragment: BaseDialogFragment
    ) {
        imageUri = null
        val file = File(activity.externalCacheDir, "output_image.jpg")
        try {
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        imageUri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(activity, "com.yinp.proapp", file)
        } else {
            Uri.fromFile(file)
        }
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        activity.startActivityForResult(intent, requestCode)
        dialogFragment.dismiss()
    }

    private fun SelectPhoto(
        fragment: Fragment,
        requestCode: Int,
        dialogFragment: BaseDialogFragment
    ) {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        fragment.startActivityForResult(intent, requestCode)
        dialogFragment.dismiss()
    }

    private fun SelectPhoto(
        activity: Activity,
        requestCode: Int,
        dialogFragment: BaseDialogFragment
    ) {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        activity.startActivityForResult(intent, requestCode)
        dialogFragment.dismiss()
    }

    fun handleImageOnKitKat(context: Context, data: Intent): String {
        val pictureUri = data.data
        if (DocumentsContract.isDocumentUri(context, pictureUri)) {
            val docId = DocumentsContract.getDocumentId(pictureUri)
            if ("com.android.providers.media.documents" == pictureUri!!.authority) {
                val id = docId.split(":").toTypedArray()[1]
                val selection = MediaStore.Images.Media._ID + "=" + id
                return getImagePath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    selection,
                    context
                ) ?: ""
            } else if ("com.android.providers.downloads.documents" == pictureUri.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(docId)
                )
                return getImagePath(contentUri, null, context) ?: ""
            } else {
                return ""
            }
        } else if ("content".equals(pictureUri!!.scheme, ignoreCase = true)) {
            //如果是content类型的uri，则使用普通类型
            return getImagePath(pictureUri, null, context) ?: ""
        } else if ("file".equals(pictureUri.scheme, ignoreCase = true)) {
            //如果是file类型的uri，则直接获取图片的路径即可
            return pictureUri.path ?: ""
        } else {
            return ""
        }
    }

    private fun getImagePath(uri: Uri?, selection: String?, context: Context): String? {
        var path: String? = null
        //通过uri和selection来获取图片的真实路径
        val cursor = context.contentResolver.query(
            uri!!, arrayOf(
                MediaStore.Images.Media.DATA
            ), selection, null, null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }
}