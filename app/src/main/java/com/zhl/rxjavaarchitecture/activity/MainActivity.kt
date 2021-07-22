package com.zhl.rxjavaarchitecture.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.models.album.entity.Photo
import com.kd.murmur.lib_core.utils.DisplayUtils
import com.permissionx.guolindev.PermissionX
import com.yalantis.ucrop.UCrop
import com.zhl.baselibrary.activity.BaseActivity
import com.zhl.baselibrary.constant.ARouterConstant
import com.zhl.baselibrary.doubleClickCheck
import com.zhl.baselibrary.dp2px
import com.zhl.baselibrary.fragment.PermissionXDialogFragment
import com.zhl.baselibrary.px2dp
import com.zhl.baselibrary.utils.ToastUtil
import com.zhl.rxjavaarchitecture.databinding.ActivityMainBinding
import com.zhl.webviewlibrary.ui.WebActivity
import java.io.File
import java.util.*

@Route(path = ARouterConstant.MAIN.INDEX)
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding.tvTest.text = "hello binding"
    }

    override fun initData() {

    }

    override fun initListener() {
        binding.btnCommonList.doubleClickCheck {
            startActivity(Intent(this, CommonListActivity::class.java))
        }
        binding.btnDialog.doubleClickCheck {
            startActivity(Intent(this, DialogTestActivity::class.java))
        }
        binding.btnViewPager2.doubleClickCheck {
            startActivity(Intent(this, SampleViewPager2Activity::class.java))
        }
        binding.btnUserIndex.doubleClickCheck {
            val f = 10f
            val result1 = f.dp2px
            val f1 = 100f
            val result2 = f1.px2dp
            Log.d("结果值", "${result1} + ; ${result2}")

            val h1 = DisplayUtils.dp2px(10f)
            val h2 = DisplayUtils.px2dp(100f)

            Log.d("结果值", "${h1} + ; ${h2}")

        }
        binding.jumpWebView.doubleClickCheck {
            startActivity(Intent(this, WebActivity::class.java))
        }
        binding.jumpCapture.doubleClickCheck {
            EasyPhotos.createCamera(this@MainActivity, false)
                .setFileProviderAuthority("$packageName.fileProvider")
                .start(9001)
        }
        binding.jumpMagicIndicator.doubleClickCheck {
            startActivity(Intent(this, MagicIndicatorSampleActivity::class.java))
        }
        binding.requestPermission.doubleClickCheck {
            PermissionX.init(this).permissions(Manifest.permission.CALL_PHONE)
                .explainReasonBeforeRequest()
                .onExplainRequestReason { scope, deniedList, beforeRequest ->
                    scope.showRequestReasonDialog(PermissionXDialogFragment(this, "必须给权限啊，大哥", deniedList))
                }
                .onForwardToSettings { scope, deniedList ->
                    scope.showForwardToSettingsDialog(
                        deniedList,
                        "You need to allow necessary permissions in Settings manually",
                        "确定",
                        "取消"
                    )
                }
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        ToastUtil.show("全部授权了")
                    } else {
                        ToastUtil.show("拒绝了")
                    }
                }
        }
    }

    override fun loadData() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) {
            return
        }
        if (requestCode == 9001) {
            val resultPhotos: ArrayList<Photo>? =
                data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS)
            if (resultPhotos == null || resultPhotos.size == 0) {
                return
            }
            val fileName = "${System.currentTimeMillis()}.jpg"
            var destinationUri =
                Uri.fromFile(File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName))
            if (destinationUri != null) {
                var options = UCrop.Options()
                options.setCircleDimmedLayer(true)
                options.setFreeStyleCropEnabled(false)
                options.setShowCropGrid(false)
                options.setHideBottomControls(true)
                options.setShowCropFrame(false)
                UCrop.of(resultPhotos[0].uri, destinationUri)
                    .withOptions(options)
                    .start(this)
            }
        }

        if (requestCode == UCrop.REQUEST_CROP) {
            var output = UCrop.getOutput(data)
            return
        }
    }

    override fun getLayoutViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}