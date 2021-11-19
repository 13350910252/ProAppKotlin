package com.yinp.proappkotlin.web

import com.yinp.proappkotlin.home.bean.HomeBannerData
import com.yinp.proappkotlin.study.wanAndroid.data.*
import com.yinp.proappkotlin.web.data.WanAndroidData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author   :yinpeng
 * date      :2021/10/22
 * package   :com.yinp.proappkotlin.web
 * describe  :
 */
interface ApiService {
    /**
     *  获取首页的banner
     */
    @GET("banner/json")
    suspend fun getBannerList(): WanAndroidData<List<HomeBannerData>>

    /**
     * https://www.wanandroid.com/article/list/0/json
     *
     * @param size
     * @return
     */
    @GET("article/list/{size}/json")
    suspend fun getHomArticleList(@Path("size") size: Int): WanAndroidData<WanHomeListData>

    /**
     * 获取置顶文章
     *
     * @return
     */
    @GET("article/top/json")
    suspend fun getStickList(): WanAndroidData<List<WanHomeListData.Data>>

    /**
     * 获取广场列表数据
     * https://wanandroid.com/user_article/list/页码/json
     *
     * @return
     */
    @GET("user_article/list/{page}/json")
    suspend fun getSquareList(@Path("page") page: Int): WanAndroidData<WanSquareListData>

    /**
     * 获取导航数据
     * https://www.wanandroid.com/navi/json
     *
     * @return
     */
    @GET("navi/json")
    suspend fun getNavigationList(): WanAndroidData<ArrayList<NavigationListData>>

    /**
     * 获取体系列表
     *
     * @return
     */
    @GET("tree/json")
    suspend fun getSystemList(): WanAndroidData<ArrayList<WanSysListData>>

    /**
     * 获取项目列表数据
     * https://wanandroid.com/article/listproject/0/json
     *
     * @return
     */
    @GET("article/listproject/{page}/json")
    suspend fun getProjectList(@Path("page") page: Int): WanAndroidData<WanProjectListData>
}