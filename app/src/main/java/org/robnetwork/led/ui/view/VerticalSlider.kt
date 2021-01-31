package org.robnetwork.led.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import org.robnetwork.led.R
import kotlin.math.max


/**
 * Created by bo on 7/2/18.
 */
class VerticalSlider : View {
    private val thumbFgPaint: Paint
    private val trackBgPaint: Paint
    private val trackFgPaint: Paint
    private val trackRect: RectF

    private var thumbRadius = 0
    private var trackBgThickness = 0
    private var trackFgThickness = 0
    private var listener: OnProgressChangeListener? = null
    private var progress = 0.0f

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        // to support non-touchable environment
        isFocusable = true
        val colorDefaultBg = resolveAttrColor("colorControlNormal", COLOR_BG)
        val colorDefaultFg = resolveAttrColor("colorControlActivated", COLOR_FG)
        thumbFgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        thumbFgPaint.style = Paint.Style.FILL
        thumbFgPaint.color = colorDefaultFg
        trackBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        trackBgPaint.style = Paint.Style.FILL
        trackBgPaint.color = colorDefaultBg
        trackFgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        trackFgPaint.style = Paint.Style.FILL
        trackFgPaint.color = colorDefaultFg
        trackRect = RectF()
        val dm = resources.displayMetrics
        thumbRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            THUMB_RADIUS_FG.toFloat(),
            dm
        ).toInt()
        trackBgThickness = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            TRACK_HEIGHT_BG.toFloat(),
            dm
        ).toInt()
        trackFgThickness = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            TRACK_HEIGHT_FG.toFloat(),
            dm
        ).toInt()
        attrs?.let {
            context?.obtainStyledAttributes(
                it,
                R.styleable.VerticalSlider,
                defStyleAttr,
                0
            )
        }?.let { arr ->
            val thumbColor =
                arr.getColor(R.styleable.VerticalSlider_vs_thumb_color, thumbFgPaint.color)
            thumbFgPaint.color = thumbColor
            val trackColor =
                arr.getColor(R.styleable.VerticalSlider_vs_track_fg_color, trackFgPaint.color)
            trackFgPaint.color = trackColor
            val trackBgColor =
                arr.getColor(R.styleable.VerticalSlider_vs_track_bg_color, trackBgPaint.color)
            trackBgPaint.color = trackBgColor
            thumbRadius = arr.getDimensionPixelSize(
                R.styleable.VerticalSlider_vs_thumb_radius,
                thumbRadius
            )
            trackFgThickness = arr.getDimensionPixelSize(
                R.styleable.VerticalSlider_vs_track_fg_thickness,
                trackFgThickness
            )
            trackBgThickness = arr.getDimensionPixelSize(
                R.styleable.VerticalSlider_vs_track_bg_thickness,
                trackBgThickness
            )
            arr.recycle()
        }
    }

    fun setThumbColor(color: Int) {
        thumbFgPaint.color = color
        invalidate()
    }

    fun setTrackFgColor(color: Int) {
        trackFgPaint.color = color
        invalidate()
    }

    fun setTrackBgColor(color: Int) {
        trackBgPaint.color = color
        invalidate()
    }

    fun setThumbRadiusPx(radiusPx: Int) {
        thumbRadius = radiusPx
        invalidate()
    }

    fun setTrackFgThicknessPx(heightPx: Int) {
        trackFgThickness = heightPx
        invalidate()
    }

    fun setTrackBgThicknessPx(heightPx: Int) {
        trackBgThickness = heightPx
        invalidate()
    }

    fun setProgress(progress: Float) {
        setProgress(progress, false)
    }

    fun setProgress(progress: Float, notifyListener: Boolean) {
        onProgressChanged(progress, notifyListener)
    }

    fun setOnSliderProgressChangeListener(listener: OnProgressChangeListener?) {
        this.listener = listener
    }

    private fun resolveAttrColor(attrName: String, defaultColor: Int): Int {
        val packageName = context.packageName
        val attrRes = resources.getIdentifier(attrName, "attr", packageName)
        if (attrRes <= 0) {
            return defaultColor
        }
        val value = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(attrRes, value, true)
        return ContextCompat.getColor(context, value.resourceId)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height =
            getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val contentWidth = paddingLeft + thumbRadius * 2 + paddingRight
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        if (widthMode != MeasureSpec.EXACTLY) {
            width = max(contentWidth, suggestedMinimumWidth)
        }
        setMeasuredDimension(width, height)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) {
            return false
        }
        val y = event.y
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val height =
                    height - paddingTop - paddingBottom - 2 * thumbRadius
                onProgressChanged(1 - y / height, true)
            }
        }
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            if (progress < 1f) {
                onProgressChanged(progress + 0.02f, true)
                return true
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            if (progress > 0f) {
                onProgressChanged(progress - 0.02f, true)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun onProgressChanged(
        progress: Float,
        notifyChange: Boolean
    ) {
        this.progress = progress
        if (this.progress < 0) this.progress = 0f
        else if (this.progress > 1f) this.progress = 1f
        invalidate()
        listener?.takeIf { notifyChange }?.onProgress(this.progress)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawTrack(canvas, thumbRadius, trackBgThickness, 0, trackBgPaint, 1f)
        val trackPadding =
            if (trackBgThickness > trackFgThickness) trackBgThickness - trackFgThickness shr 1 else 0
        drawTrack(canvas, thumbRadius, trackFgThickness, trackPadding, trackFgPaint, progress)

        // draw bg thumb
        val width = width - paddingLeft - paddingRight
        val height = height - paddingTop - paddingBottom - 2 * thumbRadius - 2 * trackPadding
        val leftOffset = width - thumbRadius * 2 shr 1
        canvas.drawCircle(
            paddingLeft + leftOffset + thumbRadius.toFloat(),
            paddingTop + thumbRadius + (1 - progress) * height + trackPadding,
            thumbRadius.toFloat(),
            thumbFgPaint
        )
    }

    private fun drawTrack(
        canvas: Canvas,
        thumbRadius: Int,
        trackThickness: Int,
        trackPadding: Int,
        trackPaint: Paint,
        progress: Float
    ) {
        val width = width - paddingLeft - paddingRight
        val height = height - paddingTop - paddingBottom - 2 * thumbRadius
        val trackLeft = paddingLeft + (width - trackThickness shr 1)
        val trackTop =
            (paddingTop + thumbRadius + (1 - progress) * height).toInt() + trackPadding
        val trackRight = trackLeft + trackThickness
        val trackBottom = getHeight() - paddingBottom - thumbRadius - trackPadding
        val trackRadius = trackThickness * 0.5f
        trackRect[trackLeft.toFloat(), trackTop.toFloat(), trackRight.toFloat()] =
            trackBottom.toFloat()
        canvas.drawRoundRect(trackRect, trackRadius, trackRadius, trackPaint)
    }

    interface OnProgressChangeListener {
        fun onProgress(progress: Float)
    }

    companion object {
        private const val THUMB_RADIUS_FG = 6
        private const val TRACK_HEIGHT_BG = 4
        private const val TRACK_HEIGHT_FG = 2
        private val COLOR_BG = Color.parseColor("#dddfeb")
        private val COLOR_FG = Color.parseColor("#7da1ae")
    }
}