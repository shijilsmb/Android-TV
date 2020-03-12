package com.smb.smbapplication.ui.login

/**
 * Created by Shijil Kadambath on 03/08/2018
 * for NewAgeSMB
 * Email : shijil@newagesmb.com
 */

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.smb.smbapplication.AppExecutors
import com.smb.smbapplication.R
import com.smb.smbapplication.common.autoCleared
import com.smb.smbapplication.data.model.User
import com.smb.smbapplication.databinding.FragmentLoginBinding
import com.smb.smbapplication.ui.BaseFragment
import java.util.*
import javax.inject.Inject

private const val TAG: String = "LoginFragment"

/**
 * A simple [Fragment] subclass.
 *
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>() {


    @Inject
    lateinit var appExecutors: AppExecutors

    lateinit var mViewModel: LoginViewModel

    var mAdapter by autoCleared<ListAdapter>()
    var mCircularAdapter by autoCleared<CircularListAdapter>()
    var mBannerAdapter by autoCleared<BannerListAdapter>()
    var list: ArrayList<User>? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_login;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel = getViewModel(LoginViewModel::class.java)

        list = arrayListOf()
        mAdapter = ListAdapter(dataBindingComponent = dataBindingComponent, appExecutors = appExecutors) {

        }

        mCircularAdapter = CircularListAdapter(dataBindingComponent = dataBindingComponent, appExecutors = appExecutors) {


        }
        mBannerAdapter = BannerListAdapter(dataBindingComponent = dataBindingComponent, appExecutors = appExecutors) {

        }

        mBinding.viewPagerBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mBinding.viewPagerBanner.adapter = mBannerAdapter



        mBinding.viewPagerBannerTitle.apply {
            offscreenPageLimit = 1
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                /* val padding = resources.getDimensionPixelOffset(R.dimen.) +
                         resources.getDimensionPixelOffset(R.dimen.peekOffset)
                 // setting padding on inner RecyclerView puts overscroll effect in the right place*/
                // TODO: expose in later versions not to rely on getChildAt(0) which might break
                setPadding(0, 55, 0, 55)
                clipToPadding = false
            }
            orientation = ViewPager2.ORIENTATION_VERTICAL
            adapter = mCircularAdapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    mCircularAdapter.pageSelected(position)
                }
            })

        }

        /* mBinding.viewPagerBannerTitle.orientation= ViewPager2.ORIENTATION_VERTICAL
         mBinding.viewPagerBannerTitle.adapter = adapter*/


        mBinding.viewPagerListTitle.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mBinding.viewPagerListTitle.adapter = LeaderBoardAdapter(activity!!)


        val categories = listOf(
                User(1, "Submissions"),
                User(2, "Interviews"),
                User(3, "Series"),
                User(4, "Starts"),
                User(5, "Today's Stats")
        )

        mAdapter.submitList(categories)
        mBannerAdapter.submitList(categories)
        mCircularAdapter.submitList(categories)
        mBinding.viewPagerBannerTitle.setCurrentItem(mCircularAdapter.getCenterPage(0), false)





    }

    override fun onResume() {
        super.onResume()
        //startBannerAnimation()
    }

    fun navController() = findNavController()



    fun startBannerAnimation(){
        var bannerPosition = 0;
        var bannerTitlePosition = 0;

        mBinding.viewPagerBanner.post {

            Timer().scheduleAtFixedRate(object : TimerTask() {
                override fun run() {


                    activity!!.runOnUiThread(Runnable {
                        // Stuff that updates the UI
                        bannerPosition++

                        if (bannerPosition >= 5) {

                            bannerTitlePosition++
                            if (bannerTitlePosition >= 5){
                                mBinding.viewPagerBannerTitle.setCurrentItem(mCircularAdapter.getCenterPage(0), false)
                                bannerTitlePosition =0
                            }else{
                                mBinding.viewPagerBannerTitle.setCurrentItem(mCircularAdapter.getCenterPage(bannerTitlePosition), true)
                            }
                            mBinding.viewPagerBanner.setCurrentItem(0, false)
                            bannerPosition = 0

                        } else {
                            mBinding.viewPagerBanner.setCurrentItem(bannerPosition, true)
                        }

                    })


                }
            }, 5000, 5000)
        }
    }


    private inner class LeaderBoardAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 5

        override fun createFragment(position: Int): Fragment = LeaderBoardFragment.create(position)
    }
}
