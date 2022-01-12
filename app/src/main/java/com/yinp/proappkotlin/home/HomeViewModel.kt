package com.yinp.proappkotlin.home

import androidx.lifecycle.viewModelScope
import com.yinp.proappkotlin.base.BaseViewModel
import com.yinp.proappkotlin.home.bean.HomeBannerData
import com.yinp.proappkotlin.study.wanAndroid.data.WanHomeListData
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.proappkotlin.web.wanDisposeNetOuter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author   :yinpeng
 * date      :2021/10/22
 * package   :com.yinp.proappkotlin.home
 * describe  :
 */
class HomeViewModel : BaseViewModel() {

    private val _homeBannerData =
        MutableStateFlow<WanResultDispose<List<HomeBannerData>>>(WanResultDispose.LS())
    val homeBannerData: StateFlow<WanResultDispose<List<HomeBannerData>>> =
        _homeBannerData

    private val _wanHomeListData2 =
        MutableStateFlow<WanResultDispose<WanHomeListData>>(WanResultDispose.LS())
    val wanHomeListData2: StateFlow<WanResultDispose<WanHomeListData>> =
        _wanHomeListData2

    private val _wanHomeListData =
        MutableStateFlow<WanResultDispose<List<WanHomeListData.Data>>>(WanResultDispose.LS())
    val wanHomeListData: StateFlow<WanResultDispose<List<WanHomeListData.Data>>> =
        _wanHomeListData

    /**
     * 获取banner的显示列表数据
     */
    fun getBannerList() {
        viewModelScope.launch {
//            _homeBannerData.value = WanResultDispose.Start()
            wanDisposeNetOuter(_homeBannerData) {
                RetrofitUtil.wandroidApiService.getBannerList()
            }
        }
    }

    /**
     * 获取首页数据
     */
    fun getListInfo(size: Int) {
        viewModelScope.launch {
            wanDisposeNetOuter(_wanHomeListData2) {
                RetrofitUtil.wandroidApiService.getHomArticleList(size)
            }
        }
    }

    /**
     * 获取首页置顶文章
     */
    fun getStickList() {
        viewModelScope.launch {
            wanDisposeNetOuter(_wanHomeListData) {
                RetrofitUtil.wandroidApiService.getStickList()
            }
        }
    }
}