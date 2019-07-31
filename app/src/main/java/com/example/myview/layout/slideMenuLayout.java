package com.example.myview.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Donvy_y on 2019/7/29
 */
public class slideMenuLayout extends LinearLayout {
    private static final String TAG = "slideMenuLayout";

    private float startX;                //startX表示鼠标点击的点的位置
    private float startY;

    private Scroller mScroller;          //构建Scroller对象，用于获取当前的滚动值

    public slideMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();                    //鼠标点击（按下）事件获取的x坐标
                startY = event.getY();
//                Log.d(TAG, "onTouchEvent---down事件获取的mouse的初始坐标: （" + startX + ", " + startY + ")");
                break;
            case MotionEvent.ACTION_MOVE:

//                Log.d(TAG, "onTouchEvent---move1事件获取的mouse的mx、my坐标: （" + event.getX() + ",  " + event.getY() + ") ");
                final float mX = (int) (event.getX() - startX);              //鼠标在X轴的滑动距离
                final float mY = (int) (event.getY() - startY);

                int disX = (int) (getScrollX() - mX);
                int disY = (int) (getScrollY() - mY);
//                Log.d(TAG, "onTouchEvent---move2事件获取的mouse的滑动位移mX: " + mX + " ,mY: " + mY);
//                Log.d(TAG, "onTouchEvent---move3事件获取的子控件view未滑动时的坐标: （" + getScrollX() + ", " + getScrollY() + ")");
//                Log.d(TAG, "onTouchEvent---move4事件获取的子控件view需要水平滑动的位移disX: " + disX +", disY"+ disY);

                scrollTo(disX, disY);
//
//                Log.d(TAG, "onTouchEvent---move5事件获取的滑动后getScrollX: " + getScrollX() + ", getScrollY:" + getScrollY());
//                Log.d(TAG, "onTouchEvent---move6事件获取的子控件view已经滑动后实际坐标: （" + (-getScrollX()) + ", " + (-getScrollY()) + ")");

                //重新设置初始坐标，把一次较长的滑动事件当作若干个小的滑动事件相加，每一次小滑动结束后的位置便是下一次小滑动事件开始的位置
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_UP:

                break;

        }
        return true;
    }


    /**
     * 重写computeScroll()方法
     * 该方法实际上通过不断调用scrollTo()，再结合scroller对象，帮助获取到当前的滚动值，
     * 通过不断地瞬移一个小的距离来实现整体上平滑的移动效果
     * computeScrollOffset()用于判断是否完成了（每一个小的）滑动事件。
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
