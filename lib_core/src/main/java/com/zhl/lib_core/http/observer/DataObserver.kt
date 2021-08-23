package com.zhl.lib_core.http.observer

import com.zhl.lib_core.http.CommonCodeHandler
import com.zhl.lib_core.model.BaseResponse
import com.zhl.lib_core.utils.ToastUtil

/**
 *    author : zhuhl
 *    date   : 2021/8/23
 *    desc   :
 *    refer  :
 */
abstract class DataObserver<T> : BaseObserver<BaseResponse<T>>() {


    override fun doOnNext(data: BaseResponse<T>) {
        //consume为true表示公共业务，已拦截处理
        val consume = CommonCodeHandler.commonCodeDealBlock?.let {
            var invoke = it.invoke(data)
            invoke
        }
        if (consume != null && consume) {
            return
        }
        onSuccess(data.data)
    }

    abstract fun onSuccess(t: T)

    fun onError() {

    }

    override fun doOnComplete() {

    }

    override fun doOnError(e: Throwable?) {
        ToastUtil.show("error:" + e?.message)
        onError()
    }
}