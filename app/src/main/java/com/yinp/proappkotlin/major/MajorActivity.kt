package com.yinp.proappkotlin.major

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationBarView
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.databinding.ActivityMajorBinding
import com.yinp.proappkotlin.home.activity.AddUndeterminedActivity
import com.yinp.proappkotlin.major.fragment.*
import com.yinp.proappkotlin.me.AddLabelActivity
import com.yinp.tools.MNONE

/**
 * @Author: yinp
 * @Date: 2021/10/9
 * @Description:首页
 */
class MajorActivity : BaseActivity<ActivityMajorBinding>() {
    private var homeFragment: HomeFragment? = null
    private var studyFragment: StudyFragment? = null
    private var toolsFragment: ToolsFragment? = null
    private var recreationFragment: RecreationFragment? = null
    private var meFragment: MeFragment? = null

    private var curFragment: Fragment? = null
    private val fragmentManager by lazy(MNONE) {
        supportFragmentManager
    }
    private lateinit var fragmentTransaction: FragmentTransaction
    override fun initViews() {
        bd.header.headerBackImg.visibility = View.GONE
        bd.header.headerCenterTitle.text = "首页"
        bd.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.one -> {
                    bd.header.headerCenterTitle.text = "首页"
                    chooseFragment(0)
                }
                R.id.two -> {
                    bd.header.headerCenterTitle.text = "学习"
                    chooseFragment(1)
                }
                R.id.three -> {
                    bd.header.headerCenterTitle.text = "工具"
                    chooseFragment(2)
                }
                R.id.four -> {
                    bd.header.headerCenterTitle.text = "娱乐"
                    chooseFragment(3)
                }
                R.id.five -> {
                    bd.header.headerCenterTitle.text = "我的"
                    chooseFragment(4)
                }
            }
            true
        }
        bd.bottomNavigationView.itemIconTintList = null
        bd.bottomNavigationView.itemTextColor = getColorStateList(R.color.selector_8a8a8a_ff4d4d)
        bd.bottomNavigationView.itemIconSize = resources.getDimension(R.dimen._28dp).toInt()
        bd.bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED

        chooseFragment(0)
        initClick(bd.header.ivImg)
    }

    override fun onClick(v: View) {
        when (v) {
            bd.header.ivImg -> {
                homeFragment?.bd?.materialViewPager?.let {
                    when (it.currentItem) {
                        0 -> {
                            gotoActivityForResult<AddUndeterminedActivity> { code, data ->
                                homeFragment?.todayUndeterminedFragment?.toDoCurFragment?.initDataList()
                            }
                        }
                        1 -> {
                            gotoActivity<AddLabelActivity>()
                        }
                        else -> {
                            showToast("viewPager2的页数错误")
                        }
                    }
                }
            }
        }
    }

    /**
     * 切换fragment
     */
    private fun chooseFragment(position: Int) {
        fragmentTransaction = fragmentManager.beginTransaction()
        when (position) {
            0 -> {
                homeFragment ?: let {
                    homeFragment = HomeFragment()
                    fragmentTransaction.add(R.id.fl_content, homeFragment!!)
                }
                curFragment?.let {
                    fragmentTransaction.hide(it)
                }
                bd.header.ivImg.visibility = View.VISIBLE
                curFragment = homeFragment
                fragmentTransaction.show(curFragment!!)
                fragmentTransaction.commitNow()
            }
            1 -> {
                studyFragment = studyFragment ?: StudyFragment().apply {
                    fragmentTransaction.add(R.id.fl_content, this)
                }
                curFragment?.let {
                    fragmentTransaction.hide(it)
                }
                bd.header.ivImg.visibility = View.GONE
                curFragment = studyFragment
                fragmentTransaction.show(curFragment!!)
                fragmentTransaction.commitNow()
            }
            2 -> {
                toolsFragment ?: let {
                    toolsFragment = ToolsFragment()
                    fragmentTransaction.add(R.id.fl_content, toolsFragment!!)
                }
                curFragment?.let {
                    fragmentTransaction.hide(it)
                }
                bd.header.ivImg.visibility = View.GONE
                curFragment = toolsFragment
                fragmentTransaction.show(curFragment!!)
                fragmentTransaction.commitNow()
            }
            3 -> {
                recreationFragment ?: let {
                    recreationFragment = RecreationFragment()
                    fragmentTransaction.add(R.id.fl_content, recreationFragment!!)
                }
                curFragment?.let {
                    fragmentTransaction.hide(it)
                }
                bd.header.ivImg.visibility = View.GONE
                curFragment = recreationFragment
                fragmentTransaction.show(curFragment!!)
                fragmentTransaction.commitNow()
            }
            4 -> {
                meFragment ?: let {
                    meFragment = MeFragment()
                    fragmentTransaction.add(R.id.fl_content, meFragment!!)
                }
                curFragment?.let { it1 ->
                    fragmentTransaction.hide(it1)
                }
                bd.header.ivImg.visibility = View.GONE
                curFragment = meFragment
                fragmentTransaction.show(curFragment!!)
                fragmentTransaction.commitNow()
            }
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(false)
    }

    override fun getBinding() = ActivityMajorBinding.inflate(layoutInflater)
}