package com.example.hearthstoneexplorer.widgets

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Size
import com.facebook.litho.annotations.*
import com.facebook.litho.utils.MeasureUtils
import com.p69.elma.litho.DSL.Container
import com.p69.elma.litho.DSL.widget
import com.p69.elma.litho.ElmaLithoView
import com.squareup.picasso.*
import java.io.File


@MountSpec
class PicassoImageSpec {
    companion object {
        private val DEFAULT_INT_VALUE = 0
        private val DEFAULT_FLOAT_VALUE = -1f

        private val DEFAULT_PIVOT_VALUE = 0

        @PropDefault
        val resizeTargetWidth = DEFAULT_INT_VALUE

        @PropDefault
        val resizeTargetHeight = DEFAULT_INT_VALUE

        @PropDefault
        val resizeTargetWidthResId = DEFAULT_INT_VALUE

        @PropDefault
        val resizeTargetHeightResId = DEFAULT_INT_VALUE

        @PropDefault
        val pivotX = DEFAULT_PIVOT_VALUE

        @PropDefault
        val pivotY = DEFAULT_PIVOT_VALUE

        @JvmStatic
        @OnMeasure
        fun onMeasure(c: ComponentContext, layout: ComponentLayout, widthSpec: Int, heightSpec: Int, size: Size) {
            MeasureUtils.measureWithEqualDimens(widthSpec, heightSpec, size)
        }

        @JvmStatic
        @OnCreateMountContent
        fun onCreateMountContent(c: ComponentContext): ImageView = ImageView(c.baseContext)

        @JvmStatic
        @OnMount
        fun onMount(c: ComponentContext, imageView: ImageView,
                    @Prop(optional = true) imageUrl: String?, @Prop(optional = true) file: File?,
                    @Prop(optional = true) uri: Uri?, @Prop(optional = true) resourceId: Int?,
                    @Prop(optional = true) picasso: Picasso?,
                    @Prop(optional = true, resType = ResType.DRAWABLE) errorDrawable: Drawable?,
                    @Prop(optional = true, resType = ResType.DRAWABLE) placeholderImage: Drawable?,
                    @Prop(optional = true) noPlaceholder: Boolean,
                    @Prop(optional = true) networkPolicy: NetworkPolicy?,
                    @Prop(optional = true) memoryPolicy: MemoryPolicy?, @Prop(optional = true) config: Bitmap.Config?,
                    @Prop(optional = true) fit: Boolean, @Prop(optional = true) centerCrop: Boolean,
                    @Prop(optional = true) centerInside: Boolean, @Prop(optional = true) noFade: Boolean,
                    @Prop(optional = true) onlyScaleDown: Boolean,
                    @Prop(optional = true) priority: Picasso.Priority?,
                    @Prop(optional = true) resizeTargetWidth: Int, @Prop(optional = true) resizeTargetHeight: Int,
                    @Prop(optional = true, resType = ResType.INT) resizeTargetWidthResId: Int,
                    @Prop(optional = true, resType = ResType.INT) resizeTargetHeightResId: Int,
                    @Prop(optional = true) rotateDegrees: Float, @Prop(optional = true) pivotX: Int,
                    @Prop(optional = true) pivotY: Int, @Prop(optional = true) stableKey: String?,
                    @Prop(optional = true) tag: Any?, @Prop(optional = true) transformation: Transformation?,
                    @Prop(optional = true) transformations: List<Transformation>?,
                    @Prop(optional = true) target: com.squareup.picasso.Target?, @Prop(optional = true) callback: Callback?) {
            var picasso = picasso

            if (imageUrl == null && file == null && uri == null && resourceId == null) {
                throw IllegalArgumentException(
                        "You must provide at least one of String, File, Uri or ResourceId")
            }

            if (picasso == null) {
                picasso = Picasso.with(c.baseContext)
            }

            val request: RequestCreator?

            if (imageUrl != null) {
                request = picasso!!.load(imageUrl)
            } else if (file != null) {
                request = picasso!!.load(file)
            } else if (uri != null) {
                request = picasso!!.load(uri)
            } else {
                request = picasso!!.load(resourceId!!)
            }

            if (request == null) {
                throw IllegalStateException("Could not instantiate RequestCreator")
            }

            if (networkPolicy != null) {
                request.networkPolicy(networkPolicy)
            }

            if (memoryPolicy != null) {
                request.memoryPolicy(memoryPolicy)
            }

            if (centerCrop) {
                request.centerCrop()
            }

            if (centerInside) {
                request.centerInside()
            }

            if (config != null) {
                request.config(config)
            }

            if (errorDrawable != null) {
                request.error(errorDrawable)
            }

            if (noPlaceholder) {
                request.noPlaceholder()
            } else {
                if (placeholderImage != null) {
                    request.placeholder(placeholderImage)
                }
            }

            if (fit) {
                request.fit()
            }

            if (noFade) {
                request.noFade()
            }

            if (onlyScaleDown) {
                request.onlyScaleDown()
            }

            if (priority != null) {
                request.priority(priority)
            }

            if (resizeTargetWidth != DEFAULT_INT_VALUE && resizeTargetHeight != DEFAULT_INT_VALUE) {
                request.resize(resizeTargetWidth, resizeTargetHeight)
            }

            if (resizeTargetWidthResId != DEFAULT_INT_VALUE && resizeTargetHeightResId != DEFAULT_INT_VALUE) {
                request.resizeDimen(resizeTargetWidthResId, resizeTargetHeightResId)
            }

            if (rotateDegrees != DEFAULT_FLOAT_VALUE) {
                if (pivotX != DEFAULT_PIVOT_VALUE && pivotY != DEFAULT_PIVOT_VALUE) {
                    request.rotate(rotateDegrees, pivotX.toFloat(), pivotY.toFloat())
                } else {
                    request.rotate(rotateDegrees)
                }
            }

            if (stableKey != null) {
                request.stableKey(stableKey)
            }

            if (tag != null) {
                request.tag(tag)
            }

            if (transformation != null) {
                request.transform(transformation)
            }

            if (transformations != null) {
                request.transform(transformations)
            }

            if (target != null) {
                request.into(target)
            } else {
                if (callback != null) {
                    request.into(imageView, callback)
                } else {
                    request.into(imageView)
                }
            }
        }


        @JvmStatic
        @OnUnmount
        fun onUnmount(c: ComponentContext, imageView: ImageView) {
            Picasso.with(imageView.context).cancelRequest(imageView)
        }
    }
}

// DSL
fun Container.picassoImage(defStyleAttr: Int = 0, defStyleRes: Int = 0, init: PicassoImage.Builder.() -> Unit)
        = children.add(widget({ PicassoImage.create(ctx, defStyleAttr, defStyleRes) }, init))