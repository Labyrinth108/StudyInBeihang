
package com.example.sony.StudyInBeihang;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentIndicator extends LinearLayout implements OnClickListener {

    private int mDefaultIndicator = 0;

    private static int mCurIndicator;

    private static View[] mIndicators;

    private OnIndicateListener mOnIndicateListener;

    private static final String TAG_ICON_0 = "icon_tag_0";
    private static final String TAG_ICON_1 = "icon_tag_1";
    private static final String TAG_ICON_2 = "icon_tag_2";

    private static final String TAG_TEXT_0 = "text_tag_0";
    private static final String TAG_TEXT_1 = "text_tag_1";
    private static final String TAG_TEXT_2 = "text_tag_2";

    private static final int COLOR_UNSELECT = Color.argb(100, 0xff, 0xff, 0xff);
    private static final int COLOR_SELECT = Color.WHITE;

    private FragmentIndicator(Context context) {
        super(context);
    }

    public FragmentIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        mCurIndicator = mDefaultIndicator;
        setOrientation(LinearLayout.HORIZONTAL);
        init();
    }

    private View createIndicator(int iconResID, int stringResID, int stringColor,
                                 String iconTag, String textTag) {
        LinearLayout view = new LinearLayout(getContext());
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
        view.setGravity(Gravity.CENTER_HORIZONTAL);

        ImageView iconView = new ImageView(getContext());
        iconView.setTag(iconTag);
        iconView.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
        iconView.setImageResource(iconResID);

        TextView textView = new TextView(getContext());
        textView.setTag(textTag);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
        textView.setTextColor(stringColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setText(stringResID);

        view.addView(iconView);
        view.addView(textView);

        return view;

    }

    private void init() {
        mIndicators = new View[3];
        mIndicators[0] = createIndicator(R.drawable.search_select,
                R.string.tab_searchroom, COLOR_SELECT, TAG_ICON_0, TAG_TEXT_0);
        mIndicators[0].setBackgroundResource(R.drawable.button_select);//选中的颜色
        mIndicators[0].setTag(Integer.valueOf(0));
        mIndicators[0].setOnClickListener(this);
        addView(mIndicators[0]);
        mIndicators[1] = createIndicator(R.drawable.dining_unselect,
                R.string.tab_diningroom, COLOR_UNSELECT, TAG_ICON_1, TAG_TEXT_1);
        mIndicators[1].setBackgroundColor(Color.alpha(0));
        mIndicators[1].setTag(Integer.valueOf(1));
        mIndicators[1].setOnClickListener(this);
        addView(mIndicators[1]);
        mIndicators[2] = createIndicator(R.drawable.book_unselect,
                R.string.tab_recommend, COLOR_UNSELECT, TAG_ICON_2, TAG_TEXT_2);
        mIndicators[2].setBackgroundColor(Color.alpha(0));
        mIndicators[2].setTag(Integer.valueOf(2));
        mIndicators[2].setOnClickListener(this);
        addView(mIndicators[2]);
    }

    public static void setIndicator(int which) {
        // clear previous status.
        mIndicators[mCurIndicator].setBackgroundColor(Color.alpha(0));
        ImageView prevIcon;
        TextView prevText;
        switch(mCurIndicator) {
            case 0:
                prevIcon =(ImageView) mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_0);
                prevIcon.setImageResource(R.drawable.search_unselect);
                prevText = (TextView) mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_0);
                prevText.setTextColor(COLOR_UNSELECT);
                break;
            case 1:
                prevIcon =(ImageView) mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_1);
                prevIcon.setImageResource(R.drawable.dining_unselect);
                prevText = (TextView) mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_1);
                prevText.setTextColor(COLOR_UNSELECT);
                break;
            case 2:
                prevIcon =(ImageView) mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_2);
                prevIcon.setImageResource(R.drawable.book_unselect);
                prevText = (TextView) mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_2);
                prevText.setTextColor(COLOR_UNSELECT);
                break;
        }

        // update current status.
        mIndicators[which].setBackgroundResource(R.drawable.button_select);
        ImageView currIcon;
        TextView currText;
        switch(which) {
            case 0:
                currIcon =(ImageView) mIndicators[which].findViewWithTag(TAG_ICON_0);
                currIcon.setImageResource(R.drawable.search_select);
                currText = (TextView) mIndicators[which].findViewWithTag(TAG_TEXT_0);
                currText.setTextColor(COLOR_SELECT);
                break;
            case 1:
                currIcon =(ImageView) mIndicators[which].findViewWithTag(TAG_ICON_1);
                currIcon.setImageResource(R.drawable.dining_select);
                currText = (TextView) mIndicators[which].findViewWithTag(TAG_TEXT_1);
                currText.setTextColor(COLOR_SELECT);
                break;
            case 2:
                currIcon =(ImageView) mIndicators[which].findViewWithTag(TAG_ICON_2);
                currIcon.setImageResource(R.drawable.book_select);
                currText = (TextView) mIndicators[which].findViewWithTag(TAG_TEXT_2);
                currText.setTextColor(COLOR_SELECT);
                break;
        }

        mCurIndicator = which;
    }

    public interface OnIndicateListener {
        public void onIndicate(View v, int which);
    }

    public void setOnIndicateListener(OnIndicateListener listener) {
        mOnIndicateListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnIndicateListener != null) {
            int tag = (Integer) v.getTag();
            switch (tag) {
                case 0:
                    if (mCurIndicator != 0) {
                        mOnIndicateListener.onIndicate(v, 0);
                        setIndicator(0);
                    }
                    break;
                case 1:
                    if (mCurIndicator != 1) {
                        mOnIndicateListener.onIndicate(v, 1);
                        setIndicator(1);
                    }
                    break;
                case 2:
                    if (mCurIndicator != 2) {
                        mOnIndicateListener.onIndicate(v, 2);
                        setIndicator(2);
                    }
                    break;
                case 3:
                    if (mCurIndicator != 3) {
                        mOnIndicateListener.onIndicate(v, 3);
                        setIndicator(3);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}

