package com.smb.smbapplication.ui.login

/**
 * Created by Shijil Kadambath on 03/08/2018
 * for NewAgeSMB
 * Email : shijil@newagesmb.com
 */

import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.smb.smbapplication.AppExecutors
import com.smb.smbapplication.R
import com.smb.smbapplication.data.model.User
import com.smb.smbapplication.databinding.FragmentLeaderboardBinding
import com.smb.smbapplication.ui.BaseFragment
import java.io.File
import java.text.FieldPosition
import javax.inject.Inject


private const val TAG: String = "ChapterPlayerFragment"

/**
 * A simple [Fragment] subclass.
 *
 */
class LeaderBoardFragment : BaseFragment<FragmentLeaderboardBinding>() {



    @Inject
    lateinit var appExecutors: AppExecutors

    lateinit var user:User

    companion object {

        private const val ARG_CHAPTER = "chapter"

        fun create(position:Int): LeaderBoardFragment {
            val fragment = LeaderBoardFragment()
            val args = Bundle()
            args.putInt(ARG_CHAPTER, position)
            fragment.arguments = args

            return fragment
        }

        val categories = listOf(
                User(1, "Submissions"),
                User(2, "Interviews"),
                User(3, "Series"),
                User(4, "Starts"),
                User(5, "Today's Stats")
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_leaderboard;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
       super.onActivityCreated(savedInstanceState)
        user =categories[arguments!!.getInt(ARG_CHAPTER,0)]



   }

    override fun onResume() {
        super.onResume()

        mBinding.name.animateText(user.name )
    }
}
