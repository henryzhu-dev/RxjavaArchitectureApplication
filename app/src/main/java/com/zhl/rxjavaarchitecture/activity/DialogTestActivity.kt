package com.zhl.rxjavaarchitecture.activity

import com.zhl.baselibrary.activity.BaseActivity
import com.zhl.baselibrary.doubleClickCheck
import com.zhl.baselibrary.utils.ToastUtil
import com.zhl.dialoglibrary.dialog.*
import com.zhl.dialoglibrary.listener.DialogClickListener
import com.zhl.dialoglibrary.listener.DialogMultiClickListener
import com.zhl.dialoglibrary.listener.DialogSingleChoiceListener
import com.zhl.rxjavaarchitecture.R
import com.zhl.rxjavaarchitecture.databinding.ActivityDialogTestBinding

/**
 *    author : zhuhl
 *    date   : 2021/7/1
 *    desc   :
 *    refer  :
 */
class DialogTestActivity : BaseActivity<ActivityDialogTestBinding>() {

    override fun initData() {
    }

    override fun loadData() {
    }

    override fun initListener() {
        binding.btnDialog.doubleClickCheck {
            val dialog =
                DefaultAlertDialogFragment(
                    this,
                    "这个是自定义内容",
                    object : DialogClickListener {
                        override fun onDialogCancelClick() {
                            ToastUtil.show("cancel")
                        }

                        override fun onDialogConfirmClick() {
                            ToastUtil.show("confirm")
                        }
                    })
            dialog.show(supportFragmentManager, "")
        }
        binding.btnSingleDialog.doubleClickCheck {
            val dialog = TraditionalSingleChoiceDialogFragment("选择颜色",
                arrayOf("红", "黄", "蓝"),
                object : DialogSingleChoiceListener<CharSequence> {
                    override fun onClick(which: Int, selItem: CharSequence) {
                        ToastUtil.show(selItem.toString())
                    }
                })
            dialog.show(supportFragmentManager, "")
        }
        binding.yjSingleStringDialog.doubleClickCheck {
            val dialog = StringSingleChoiceDialogFragment(
                "选择颜色",
                arrayOf("红", "黄", "蓝"), 0,
                object : DialogSingleChoiceListener<String> {
                    override fun onClick(which: Int, selItem: String) {
                        ToastUtil.show(selItem.toString())
                    }
                })
            dialog.show(supportFragmentManager, "")
        }
        binding.yjMultiModeDialog.doubleClickCheck {
            val dialog = MultiChoiceDialogFragment(
                this,
                arrayOf("红色", "黄色", "绿色"),
                object : DialogMultiClickListener {
                    override fun onDialogMultiConfirmClick(list: MutableList<Int>) {

                    }

                    override fun onDialogMultiCancelClick() {

                    }
                }
            )
            dialog.show(supportFragmentManager, "")
        }
        binding.customViewDialog.doubleClickCheck {
            val dialog = CustomViewDialogFragment(
                this,
                R.layout.dialog_common_layout
            )
            dialog.show(supportFragmentManager, "")
        }
        binding.appUseViewDialog.doubleClickCheck {
            val dialog = CommonDialogFragment(
                this,
                "传进去的内容",
                object : DialogClickListener {
                    override fun onDialogConfirmClick() {
                        ToastUtil.show("点击了确定")
                    }

                    override fun onDialogCancelClick() {
                        ToastUtil.show("点击了取消")
                    }
                })
            dialog.show(supportFragmentManager, "")
        }
    }

    override fun getLayoutViewBinding(): ActivityDialogTestBinding {
        return ActivityDialogTestBinding.inflate(layoutInflater)
    }

}