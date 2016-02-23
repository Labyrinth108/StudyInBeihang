package com.example.sony.StudyInBeihang;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
/**
 *	功能描述：自定义顶部工具栏
 */
public class TitleView extends FrameLayout implements View.OnClickListener {

   // private Button mLeftBtn;
    private Button mRightBtn;
    private TextView mTitle;

  //  private OnLeftButtonClickListener mOnLeftButtonClickListener;
    private OnRightButtonClickListener mOnRightButtonClickListener;

    public interface OnLeftButtonClickListener {
        public void onClick(View button);
    }

    public interface OnRightButtonClickListener {
        public void onClick(View button);
    }

//    public void setLeftButton(String text, OnLeftButtonClickListener listener) {
//        mLeftBtn.setText(text);
//        mLeftBtn.setVisibility(View.VISIBLE);
//        mOnLeftButtonClickListener = listener;
//    }

//    public void setLeftButton(int stringID, OnLeftButtonClickListener listener) {
//        mLeftBtn.setText(stringID);
//        mLeftBtn.setVisibility(View.VISIBLE);
//        mOnLeftButtonClickListener = listener;
//    }

//    public void removeLeftButton() {
//        mLeftBtn.setText("");
//        mLeftBtn.setVisibility(View.INVISIBLE);
//        mOnLeftButtonClickListener = null;
//    }

//    public void hiddenLeftButton() {
//        mLeftBtn.setVisibility(View.INVISIBLE);
//    }
//
//    public void showLeftButton() {
//        mLeftBtn.setVisibility(View.VISIBLE);
//    }

    public void setRightButton(String text, OnRightButtonClickListener listener) {
        mRightBtn.setText(text);
        mRightBtn.setVisibility(View.VISIBLE);
        mOnRightButtonClickListener = listener;
    }

    public void setRightButton(int stringID, OnRightButtonClickListener listener) {
        mRightBtn.setText(stringID);
        mRightBtn.setVisibility(View.VISIBLE);
        mOnRightButtonClickListener = listener;
    }

    public void removeRightButton() {
        mRightBtn.setText("");
        mRightBtn.setVisibility(View.INVISIBLE);
        mOnRightButtonClickListener = null;
    }

    public void hiddenRightButton() {
        mRightBtn.setVisibility(View.INVISIBLE);
    }

    public void showRightButton() {
        mRightBtn.setVisibility(View.VISIBLE);
    }

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.title_view, this, true);

        mRightBtn = (Button) findViewById(R.id.right_btn);
        mRightBtn.setVisibility(View.INVISIBLE);
        mRightBtn.setOnClickListener(this);

        mTitle = (TextView) findViewById(R.id.title_text);
        mTitle.setVisibility(View.INVISIBLE);
    }

    public void setTitle(String text) {
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(text);
    }

    public void setTitle(int stringID) {
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(stringID);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.left_btn:
//                if(mOnLeftButtonClickListener != null)
//                    mOnLeftButtonClickListener.onClick(v);
//                break;
            case R.id.right_btn:
                if(mOnRightButtonClickListener != null)
                    mOnRightButtonClickListener.onClick(v);
                break;
        }
    }

}

