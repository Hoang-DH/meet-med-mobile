package com.example.doctorapp.modulePatient.presentation.loadMore

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.IntDef
import com.example.doctorapp.R

class LoadMoreView : LinearLayout, LoadMoreFooter {

    private lateinit var ivLoading: ImageView
    private var rotation: Animation? = null
    private var mState = LOADING_STATE_INIT

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        inflate(context, R.layout.layout_loadmore, this)
        ivLoading = findViewById(R.id.ivLoadingArrow)
        setPadding(0, 0, 0, 0)
        setState(LOADING_STATE_NORMAL)
    }

    fun startLoadingAnimation() {
        rotation = AnimationUtils.loadAnimation(context, R.anim.loadmore_rotate_anim)
        ivLoading.startAnimation(rotation)
        visibility = VISIBLE
    }

    fun stopLoadingAnimation() {
        rotation?.cancel()
        rotation?.reset()
        visibility = GONE
    }

    private fun setState(@LoadingState state: Int) {
        if (mState == state) return
        mState = state
        when (mState) {
            LOADING_STATE_NORMAL -> visibility = INVISIBLE
            LOADING_STATE_PREPARE -> visibility = VISIBLE
            LOADING_STATE_LOAD -> visibility = VISIBLE
            LOADING_STATE_COMPLETE -> setState(LOADING_STATE_NORMAL)
        }
    }

    override fun onLoadMore() {
        setState(LOADING_STATE_LOAD)
    }

    override fun onPrepareLoad() {
        setState(LOADING_STATE_PREPARE)
    }

    override fun onLoadCancel() {
        setState(LOADING_STATE_NORMAL)
    }

    override fun onLoadCompleted() {
        setState(LOADING_STATE_COMPLETE)
    }

    @IntDef(LOADING_STATE_INIT, LOADING_STATE_NORMAL, LOADING_STATE_LOAD, LOADING_STATE_COMPLETE, LOADING_STATE_PREPARE)
    @Retention(AnnotationRetention.SOURCE)
    annotation class LoadingState
    companion object {
        private const val LOADING_STATE_INIT = -1
        const val LOADING_STATE_NORMAL = 0
        const val LOADING_STATE_PREPARE = 1
        const val LOADING_STATE_LOAD = 2
        const val LOADING_STATE_COMPLETE = 3
    }
}

interface LoadMoreFooter {
    fun onLoadMore()
    fun onPrepareLoad()
    fun onLoadCancel()
    fun onLoadCompleted()
}

