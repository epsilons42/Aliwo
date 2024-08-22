package com.example.aliwo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aliwo.databinding.ImageDetailViewpagerItemBinding
import com.example.aliwo.listener.IZoomControlListener
import com.example.aliwo.util.Glide
import com.otaliastudios.zoom.ZoomLayout

class ImageDetailViewPager2Adapter(
    val context: Context,
    private val imageList: List<String>,
    private val zoomControlListener: IZoomControlListener
) :
    RecyclerView.Adapter<ImageDetailViewPager2Adapter.ViewHolder>() {
    class ViewHolder(val viewBinding: ImageDetailViewpagerItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ImageDetailViewpagerItemBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val zoomLayout = holder.viewBinding.imageDetailViewPagerTemZL
        val imageView = holder.viewBinding.imageDetailViewPagerTemImv
        imageView.Glide(imageList[position])

        imageView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    zoomControl(zoomLayout)
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    zoomControl(zoomLayout)
                    true
                }
                MotionEvent.ACTION_UP -> {
                    zoomControl(zoomLayout)
                    true
                }
                else -> {
                    zoomControl(zoomLayout)
                    false
                }
            }
        }
    }

    private fun zoomControl(zoomLayout: ZoomLayout) {
        if (zoomLayout.zoom > 1.0F) {
            zoomControlListener.zoomControl(true)
        } else {
            zoomControlListener.zoomControl(false)
        }
    }
}
