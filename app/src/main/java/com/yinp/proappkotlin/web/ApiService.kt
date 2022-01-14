package com.yinp.proappkotlin.web

import com.yinp.proappkotlin.home.bean.HomeBannerData
import com.yinp.proappkotlin.study.wanAndroid.data.*
import com.yinp.proappkotlin.web.data.WanAndroidData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
    @GET("article/list/{page}/json")
    suspend fun getHomArticleList(@Path("page") size: Int): WanAndroidData<WanHomeListData>

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

    /**
     * 获取自己得积分
     *
     * @return
     */
    @GET("lg/coin/userinfo/json")
    suspend fun getIntegral(): WanAndroidData<IntegralBean>

    /**
     * 退出WanAndroid登录
     * https://www.wanandroid.com/user/logout/json
     *
     * @return
     */
    @GET("user/logout/json")
    suspend fun loginOut(): WanAndroidData<String>

    /**
     * 获取积分排行榜
     *
     * @return
     */
    @GET("coin/rank/{page}/json")
    suspend fun getRankList(@Path("page") page: Int): WanAndroidData<RankListBean>

    /**
     * 登录接口
     *
     * @param username
     * @param password
     * @return
     */
    @POST("user/login")
    suspend fun login(
        @Query("username") username: String?,
        @Query("password") password: String?
    ): WanAndroidData<WanLoginBean>


    /**
     * 收藏文章
     * @return
     */
    @POST("lg/collect/{id}/json")
    suspend fun addCollect(@Path("id") id: Int): WanAndroidData<String>

    /**
     * 取消收藏
     * @return
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollect(@Path("id") id: Int): WanAndroidData<String>


    /**
     * 获取收藏列表
     * https://www.wanandroid.com/lg/collect/list/0/json
     *
     * @return
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectList(@Path("page") page: Int): WanAndroidData<CollectionListBean>
}