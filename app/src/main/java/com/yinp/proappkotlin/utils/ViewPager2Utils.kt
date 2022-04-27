package com.yinp.proappkotlin.utils

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import net.lucode.hackware.magicindicator.MagicIndicator

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.utils
 * describe  :
 */
object ViewPager2Utils {
    fun bind(magicIndicator: MagicIndicator, viewPager: ViewPager2) {
        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                magicIndicator.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                magicIndicator.onPageScrollStateChanged(state)
            }
        })
    }

    fun unBind(viewPager: ViewPager2) {
        viewPager.unregisterOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
    }

    fun getAdapter(
        fragmentActivity: FragmentActivity,
        fragments: List<Fragment>
    ): FragmentStateAdapter {
        return MFragmentStateAdapter(fragmentActivity, fragments, -1)
    }

    fun getAdapter(
        fragmentActivity: FragmentActivity,
        fragments: List<Fragment>,
        position: Int
    ): FragmentStateAdapter {
        return MFragmentStateAdapter(fragmentActivity, fragments, position)
    }

    fun getAdapter(fragment: Fragment, fragments: List<Fragment>): FragmentStateAdapter {
        return MFragmentStateAdapter(fragment, fragments)
    }

    fun getAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        fragments: List<Fragment>
    ): FragmentStateAdapter {
        return MFragmentStateAdapter(fragmentManager, lifecycle, fragments)
    }

    internal class MFragmentStateAdapter : FragmentStateAdapter {
        private var fragments: List<Fragment>

        constructor(
            fragmentActivity: FragmentActivity,
            fragments: List<Fragment>,
            position: Int
        ) : super(fragmentActivity) {
            this.fragments = fragments
        }

        constructor(fragment: Fragment, fragments: List<Fragment>) : super(fragment) {
            this.fragments = fragments
        }

        constructor(
            fragmentManager: FragmentManager,
            lifecycle: Lifecycle,
            fragments: List<Fragment>
        ) : super(fragmentManager, lifecycle) {
            this.fragments = fragments
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
            //            return ClientFiveRecordsListFragment.newInstance(position);
        }

        override fun getItemCount(): Int {
            return fragments.size
        }
    }

    fun getAdapter(
        fragmentActivity: FragmentActivity,
        fragments: SparseArray<Fragment>
    ): FragmentStateAdapter {
        return MFragmentStateAdapter2(fragmentActivity, fragments, -1)
    }

    fun getAdapter(
        fragmentActivity: FragmentActivity,
        fragments: SparseArray<Fragment>,
        position: Int
    ): FragmentStateAdapter {
        return MFragmentStateAdapter2(fragmentActivity, fragments, position)
    }

    fun getAdapter(fragment: Fragment, fragments: SparseArray<Fragment>): FragmentStateAdapter {
        return MFragmentStateAdapter2(fragment, fragments)
    }

    fun getAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        fragments: SparseArray<Fragment>
    ): FragmentStateAdapter {
        return MFragmentStateAdapter2(fragmentManager, lifecycle, fragments)
    }

    internal class MFragmentStateAdapter2 : FragmentStateAdapter {
        private var fragments: SparseArray<Fragment>

        constructor(
            fragmentActivity: FragmentActivity,
            fragments: SparseArray<Fragment>,
            position: Int
        ) : super(fragmentActivity) {
            this.fragments = fragments
        }

        constructor(fragment: Fragment, fragments: SparseArray<Fragment>) : super(fragment) {
            this.fragments = fragments
        }

        constructor(
            fragmentManager: FragmentManager,
            lifecycle: Lifecycle,
            fragments: SparseArray<Fragment>
        ) : super(fragmentManager, lifecycle) {
            this.fragments = fragments
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
            //            return ClientFiveRecordsListFragment.newInstance(position);
        }

        override fun getItemCount(): Int {
            return fragments.size()
        }
    }
}