package com.yinp.proappkotlin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.databinding.ActivityMajorBinding
import com.yinp.proappkotlin.utils.StatusBarUtil

/**
 * @Author: yinp
 * @Date: 2021/10/9
 * @Description:首页
 */
class MajorActivity : BaseActivity<ActivityMajorBinding>() {
    private var homeFragment: HomeFragment? = null

    private lateinit var curFragment: Fragment
    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(this))
        bd.header.headerBackImg.visibility = View.GONE
        bd.header.headerCenterTitle.text = "首页"
        bd.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
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
        bd.bottomNavigationView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED

        fragmentManager = supportFragmentManager
        chooseFragment(0)
    }

    /**
     * 切换fragment
     */
    private fun chooseFragment(position: Int) {
        fragmentTransaction = fragmentManager!!.beginTransaction()
        when (position) {
            0 -> {
                if (homeFragment == null) {
                    homeFragment = HomeFragment()
                    fragmentTransaction.add(R.id.fl_content, homeFragment)
                }
                if (curFragment != null) {
                    fragmentTransaction.hide(curFragment)
                }
                bd.header.ivImg.visibility = View.VISIBLE
                curFragment = homeFragment
                fragmentTransaction.show(curFragment)
                fragmentTransaction.commitNow()
            }
            1 -> {
                if (studyFragment == null) {
                    studyFragment = StudyFragment()
                    fragmentTransaction.add(R.id.fl_content, studyFragment)
                }
                if (curFragment != null) {
                    fragmentTransaction.hide(curFragment)
                }
                bd.header.ivImg.visibility = View.GONE
                curFragment = studyFragment
                fragmentTransaction.show(curFragment)
                fragmentTransaction.commitNow()
            }
            2 -> {
                if (toolsFragment == null) {
                    toolsFragment = ToolsFragment()
                    fragmentTransaction.add(R.id.fl_content, toolsFragment)
                }
                if (curFragment != null) {
                    fragmentTransaction.hide(curFragment)
                }
                bd.header.ivImg.visibility = View.GONE
                curFragment = toolsFragment
                fragmentTransaction.show(curFragment)
                fragmentTransaction.commitNow()
            }
            3 -> {
                if (recreationFragment == null) {
                    recreationFragment = RecreationFragment()
                    fragmentTransaction.add(R.id.fl_content, recreationFragment)
                }
                if (curFragment != null) {
                    fragmentTransaction.hide(curFragment)
                }
                bd.header.ivImg.visibility = View.GONE
                curFragment = recreationFragment
                fragmentTransaction.show(curFragment)
                fragmentTransaction.commitNow()
            }
            4 -> {
                if (meFragment == null) {
                    meFragment = MeFragment()
                    fragmentTransaction.add(R.id.fl_content, meFragment)
                }
                if (curFragment != null) {
                    fragmentTransaction.hide(curFragment)
                }
                bd.header.ivImg.visibility = View.GONE
                curFragment = meFragment
                fragmentTransaction.show(curFragment)
                fragmentTransaction.commitNow()
            }
        }
    }

    override fun getBinding(): ViewBinding {
        return ActivityMajorBinding.inflate(layoutInflater)
    }
}