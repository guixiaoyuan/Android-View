package com.example.guixy.formwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static java.lang.Float.parseFloat;

/**
 * @author guixy
 * @version 1.0, 2017/05/16
 */

public class FormWidget extends LinearLayout {
    private static final String TAG = FormWidget.class.getSimpleName();

    private OnClickListener listener = null;

    private TextView tvChooseFormTitle;
    private EditText etChooseFormContent;

    private ContentChangeListener mContentChangeListener;
    private boolean mSelectable = false;

    public FormWidget(Context context) {
        super(context);
    }

    public FormWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        View view = inflate(context, R.layout.widget_choose_form, null);
        tvChooseFormTitle = (TextView) view.findViewById(R.id.tv_choose_form_title);
        etChooseFormContent = (EditText) view.findViewById(R.id.et_choose_form_content);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FormWidget);

        initView(typedArray);
        addView(view, new LinearLayoutCompat.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        typedArray.recycle();
    }

    private void initView(TypedArray typedArray) {
        if (typedArray == null) return;
        int size = typedArray.length();
        for (int i = 0; i < size; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.FormWidget_title:
                    setTitle(typedArray.getString(attr));
                    break;
                case R.styleable.FormWidget_hint:
                    setHint(typedArray.getString(attr));
                    break;
                case R.styleable.FormWidget_text:
                    setContent(typedArray.getString(attr));
                    break;
                case R.styleable.FormWidget_selectable:
                    setFormSelectable(typedArray.getBoolean(attr, false));
                    break;
                case R.styleable.FormWidget_editable:
                    setFormForEditable(typedArray.getBoolean(attr, false));
                    break;
                case R.styleable.FormWidget_android_inputType:
                    setInputType(typedArray.getInt(attr, EditorInfo.TYPE_NULL));
                    break;
                case R.styleable.FormWidget_maxLength:
                    setMaxLength(typedArray.getInt(attr, EditorInfo.TYPE_NULL));
                default:
                    break;
            }
        }
    }

    /**
     * 设置最大输入长度
     *
     * @param anInt
     */
    private void setMaxLength(int anInt) {
        etChooseFormContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(anInt)});
    }

    /**
     * 设置输入类型
     *
     * @param inputType
     */
    private void setInputType(int inputType) {
        etChooseFormContent.setInputType(inputType);
    }

    /**
     * 获取内容
     */
    public String getContent() {
        return etChooseFormContent.getText().toString();
    }

    public int getNum() {
        try {
            return Integer.parseInt(getContent());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getDate() {
        return DateUtil.dateToDateTime(getContent());
    }

    /**
     * 获取金额（包含错误处理）
     */
    public float getAmount() {
        try {
            return parseFloat(getContent());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0f;
    }


    /**
     * 设置内容可点击（设置为点击事件）
     */
    public void setFormSelectable(boolean clickable) {
        mSelectable = clickable;
        etChooseFormContent.setClickable(clickable);
        etChooseFormContent.setFocusableInTouchMode(!clickable);
        etChooseFormContent.setFocusable(false);
        etChooseFormContent.setOnClickListener(listener);
        this.setOnClickListener(listener);
    }

    /**
     * 设置内容可编辑
     */
    public void setFormForEditable(boolean editable) {
        mSelectable = !editable;
        if (editable) {
            etChooseFormContent.setOnClickListener(null);
            etChooseFormContent.setFocusable(true);
            etChooseFormContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            etChooseFormContent.setFocusableInTouchMode(true);
        } else {
            etChooseFormContent.setInputType(EditorInfo.TYPE_NULL);
        }
    }

    /**
     * 获取展示文本控件
     *
     * @return
     */
    public EditText getEditText() {
        return etChooseFormContent;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
        setFormSelectable(true);
    }

    public void setContent(String content) {
        etChooseFormContent.setText(content);
    }

    public void setTitle(String titleString) {
        if (tvChooseFormTitle != null && !StringUtil.isEmpty(titleString)) {
            tvChooseFormTitle.setText(titleString);
        }
    }

    public void setHint(String hintString) {
        if (etChooseFormContent != null && !StringUtil.isEmpty(hintString)) {
            etChooseFormContent.setHint(hintString);
        }
    }

    /**
     * 监听EditText里面数据的变化
     */

    public void observerContentChangeListener(ContentChangeListener contentChangeListener) {
        mContentChangeListener = contentChangeListener;
        etChooseFormContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mContentChangeListener.afterTextChanged();
            }
        });
    }

    public interface ContentChangeListener {
        void afterTextChanged();
    }

    /**
     * 设置是否让子控件接收触摸事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mSelectable || super.onInterceptTouchEvent(ev);
    }


}
