package com.example.androidpracticumcustomview.ui.theme

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */

class CustomContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        for (i in 0 until childCount) {
            val child = getChildAt(i)
                measureChild(child, child.measuredWidth, child.measuredHeight)
        }

        val width = resolveSize(widthSize, widthMeasureSpec)
        val height = resolveSize(heightSize, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val parentWidth = right - left
        val parentHeight = bottom - top

        val firstChild = getChildAt(0)
        firstChild?.let {
            val firstChildWidth = it.measuredWidth
            val firstChildHeight = it.measuredHeight
            val firstChildLeft = (parentWidth - firstChildWidth) / 2
            val firstChildTop = 0
            val firstChildRight = firstChildLeft + firstChildWidth
            val firstChildBottom = firstChildTop + firstChildHeight
            it.layout(firstChildLeft, firstChildTop, firstChildRight, firstChildBottom)
        }

        val secondChild = getChildAt(1)
        secondChild?.let {
            val secondChildWidth = it.measuredWidth
            val secondChildHeight = it.measuredHeight
            val secondChildLeft = (parentWidth - secondChildWidth) / 2
            val secondChildTop = parentHeight - secondChildHeight
            val secondChildRight = secondChildLeft + secondChildWidth
            val secondChildBottom = secondChildTop + secondChildHeight
            it.layout(secondChildLeft, secondChildTop, secondChildRight, secondChildBottom)
        }
    }

    override fun addView(child: View) {
        if (childCount >= 2) {
            throw IllegalStateException("CustomContainer может содержать только два дочерних элемента.")
        }
        super.addView(child)

        child.alpha = 0f

        post {
            val startTranslationY = when (childCount) {
                1 -> height / 2f
                2 -> -height / 2f
                else -> 0f
            }

            child.translationY = startTranslationY

            val alphaAnimator = ObjectAnimator.ofFloat(child, View.ALPHA, 0f, 1f)
            val translationYAnimator = ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, startTranslationY, 0f)

            alphaAnimator.duration = 5000L
            translationYAnimator.duration = 5000L

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(alphaAnimator, translationYAnimator)
            animatorSet.start()
        }
    }
}