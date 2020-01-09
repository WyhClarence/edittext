package com.yh.edittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yh.edittext.utils.ConvertDpAndPx;
import com.yh.edittext.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MyEditText extends RelativeLayout {

    private Context context;
    //輸入最大長度
    private int maxLeght;

    private String maxLengthTip;
    //是否顯示輸入最大字數限制
    private boolean isShowMaxLenght;
    private int maxLenghtTextColor;
    private int maxLenghtTextSize;
    private int maxTextMarginLeft;
    private int maxTextMarginRight;
    private int maxTextMarginTop;
    private int maxTextMarginBottom;
    //是否允許輸入表情
    private boolean isInputIcon;
    //輸入表情時提示的內容，不設置內容不提示
    private String inputIconTipText;

    private String hint;
    private int hintColor;
    private int edTextColor;
    private String edText;
    private int edTextSize;
    //清除按鈕的大小
    private int clearIconSiZe;
    //清除按鈕圖標
    private Drawable clearIcon;
    //清除按鈕右間距
    private int clearRightMargin;
    //清除按鈕點擊事件
    private OnClickListener clearOnClickListener;

    //查看密碼按鈕圖標兩種狀態
    private Drawable lookIconUncheck;

    private Drawable lookIconChecked;

    //"查看密碼"圖標的大小
    private int lookIconSiZe;
    //查看密碼按鈕右間距
    private int lookRightMargin;
    //查看密碼按鈕點擊事件
    private OnClickListener lookOnClickListener;

    private int btnMarginTop;
    private int btnMarginRight;
    private int btnMarginBottom;
    private int btnMarginLeft;

    private int editRightPadding;

    //設置輸入密碼替換符
    private String pwdChar;
    //是否換行
    private boolean singleLine;

    private int inputType;

    private int contentGravity;

    private int btnGravity;
    private EditText editText;
    //顯示最大字數的textview
    private TextView maxLenghtTextView;

    private ImageView ivClear;
    private CheckBox ivLook;


    private List<InputFilter> mInputFilter = new ArrayList<>();

    private AsteriskPasswordTransformationMethod passwordTransformationMethod;

    public MyEditText(Context context) {
        this(context, null);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        this(context, attrs, -1);//R.attr.editTextStyle
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyEditText);
        maxLeght = typedArray.getInt(R.styleable.MyEditText_ed_MaxLength, 0);
        maxLengthTip = typedArray.getString(R.styleable.MyEditText_ed_MaxLengthTip);
        isShowMaxLenght = typedArray.getBoolean(R.styleable.MyEditText_ed_showMaxLenght, false);
        maxLenghtTextColor = typedArray.getColor(R.styleable.MyEditText_ed_maxLengthTextColor, 0x10);
        maxLenghtTextSize = ConvertDpAndPx.Px2Dp(context, typedArray.getDimensionPixelSize(R.styleable.MyEditText_ed_maxLengthTextSize, 12));
        isInputIcon = typedArray.getBoolean(R.styleable.MyEditText_ed_isInputIcon, false);
        inputIconTipText = typedArray.getString(R.styleable.MyEditText_ed_putIconTip);
        clearIconSiZe = typedArray.getDimensionPixelSize(R.styleable.MyEditText_ed_clearIconSize, 0);
        clearIcon = typedArray.getDrawable(R.styleable.MyEditText_ed_clearRes);
        clearRightMargin = ConvertDpAndPx.Px2Dp(context, typedArray.getDimensionPixelSize(R.styleable.MyEditText_ed_clearMarginRight, 0));
        lookIconUncheck = typedArray.getDrawable(R.styleable.MyEditText_ed_lookIcon_uncheck);
        lookIconChecked = typedArray.getDrawable(R.styleable.MyEditText_ed_lookIcon_checked);
        lookIconSiZe = ConvertDpAndPx.Px2Dp(context, typedArray.getDimensionPixelSize(R.styleable.MyEditText_ed_lookIconSize, 0));
        lookRightMargin = ConvertDpAndPx.Px2Dp(context, typedArray.getDimensionPixelSize(R.styleable.MyEditText_ed_lookMarginRight, 0));
        pwdChar = typedArray.getString(R.styleable.MyEditText_ed_pwdChar);
        singleLine = typedArray.getBoolean(R.styleable.MyEditText_ed_singLeLine, false);
        inputType = typedArray.getInteger(R.styleable.MyEditText_inputType, 0);
        contentGravity = typedArray.getInteger(R.styleable.MyEditText_ed_gravity, 0);

        maxTextMarginLeft = ConvertDpAndPx.Px2Dp(context, typedArray.getDimensionPixelSize(R.styleable.MyEditText_ed_maxLengthMarginLeft, 0));
        maxTextMarginRight = ConvertDpAndPx.Px2Dp(context, typedArray.getDimensionPixelSize(R.styleable.MyEditText_ed_maxLengthMarginRight, 0));
        maxTextMarginTop = ConvertDpAndPx.Px2Dp(context, typedArray.getDimensionPixelSize(R.styleable.MyEditText_ed_maxLengthMarginTop, 0));
        maxTextMarginBottom = ConvertDpAndPx.Px2Dp(context, typedArray.getDimensionPixelSize(R.styleable.MyEditText_ed_maxLengthMarginBottom, 0));
        btnGravity = typedArray.getInteger(R.styleable.MyEditText_ed_clear_gravity, 2);

        btnMarginTop = ConvertDpAndPx.Px2Dp(context, typedArray.getDimensionPixelSize(R.styleable.MyEditText_ed_btn_marginTop, 0));
        btnMarginRight = ConvertDpAndPx.Px2Dp(context, typedArray.getDimensionPixelSize(R.styleable.MyEditText_ed_btn_marginRight, 0));
        btnMarginBottom = ConvertDpAndPx.Px2Dp(context, typedArray.getDimensionPixelSize(R.styleable.MyEditText_ed_btn_marginBottom, 0));
        btnMarginLeft = ConvertDpAndPx.Px2Dp(context, typedArray.getDimensionPixelSize(R.styleable.MyEditText_ed_btn_marginLeft, 0));

        hint = typedArray.getString(R.styleable.MyEditText_ed_hint);
        hintColor = typedArray.getColor(R.styleable.MyEditText_ed_hintColor, 0x10);
        edTextColor = typedArray.getColor(R.styleable.MyEditText_ed_textColor, 0x10);
        edText = typedArray.getString(R.styleable.MyEditText_ed_text);
        edTextSize = ConvertDpAndPx.Px2Dp(context, typedArray.getDimensionPixelSize(R.styleable.MyEditText_ed_textSize, 12));

        typedArray.recycle();
        initLeght();
        initEditText();
        initClearIcon();
        initLookIcon();
    }

    private void initEditText() {
        if (!isInputIcon) {
            mInputFilter.add(new EmojiInputFilter());
        }
        editText = new EditText(context);
        editText.setId(R.id.my_edit_id);
        editText.setText(edText);
        editText.setTextColor(edTextColor);
        editText.setHint(hint);
        editText.setHintTextColor(hintColor);
        editText.setTextSize(edTextSize);
        editText.setGravity(contentGravity);
        InputFilter[] inputFilters = new InputFilter[mInputFilter.size()];
        for (int i = 0; i < mInputFilter.size(); i++) {
            inputFilters[i] = mInputFilter.get(i);
        }
        editText.setFilters(inputFilters);
        if (!TextUtils.isEmpty(pwdChar)) {
            passwordTransformationMethod = new AsteriskPasswordTransformationMethod();
            editText.setTransformationMethod(passwordTransformationMethod);//必須在setsingleLine之後才有效
        }
        if (inputType != 0) {
            editText.setInputType(inputType);
        }

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        editRightPadding = clearIconSiZe + lookIconSiZe + clearRightMargin + lookRightMargin;
        editText.setPadding(0, 0, editRightPadding, 0);
        editText.setBackgroundColor(0);
        if (isShowMaxLenght) {
            params.addRule(RelativeLayout.ABOVE, R.id.my_edit_max_length_text_id);
        }
        editText.setLayoutParams(params);
//        editText.setSingleLine();這個方法會改變edittext的TransformationMethod，不適用
        if (singleLine) {
            editText.setMaxLines(1);
            editText.setHorizontallyScrolling(true);
        } else {
            editText.setMaxLines(Integer.MAX_VALUE);
            //水平滚动设置为False
            editText.setHorizontallyScrolling(false);
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setMaxLengthText(editable.toString().length());
                if (ivClear != null) {
                    if (editable.toString().length() > 0) {
                        ivClear.setVisibility(VISIBLE);
                    } else {
                        ivClear.setVisibility(GONE);
                    }
                }
            }
        });
        this.addView(editText);
    }

    /**
     * 初始化查看密碼按鈕
     */
    private void initLookIcon() {
        if (lookIconUncheck != null) {
            ivLook = new CheckBox(context);
            ivLook.setId(R.id.my_edit_look_icon_id);
            LayoutParams ivLookParams = getImgLayoutParams(lookIconSiZe, lookIconSiZe, lookRightMargin);
            ivLook.setButtonDrawable(null);
            if (ivClear != null) {
                ivLookParams.rightMargin = clearIconSiZe + lookRightMargin + clearRightMargin;
            } else {
                ivLookParams.rightMargin = lookRightMargin;
            }

            ivLook.setLayoutParams(ivLookParams);
            ivLook.setBackground(lookIconUncheck);

            this.addView(ivLook);
            ivLook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        ivLook.setBackground(lookIconChecked);
                        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        ivLook.setBackground(lookIconUncheck);
                        if (passwordTransformationMethod == null) {
                            editText.setTransformationMethod(new PasswordTransformationMethod());
                        } else {
                            editText.setTransformationMethod(passwordTransformationMethod);//讓密碼顯示為*
                        }
                    }
                    editText.setSelection(editText.getText().toString().length());
                    if (lookOnClickListener != null) {
                        lookOnClickListener.onClick(compoundButton);
                    }
                }
            });
        }
    }

    /**
     * 初始化清空按鈕
     */
    private void initClearIcon() {
        if (clearIcon != null) {
            ivClear = new ImageView(context);
            ivClear.setId(R.id.my_edit_clear_icon_id);
            ivClear.setImageDrawable(clearIcon);
            ivClear.setLayoutParams(getImgLayoutParams(clearIconSiZe, clearIconSiZe, clearRightMargin));
            this.addView(ivClear);
            //有了內容才顯示清除按鈕
            ivClear.setVisibility(GONE);
            ivClear.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("");
                    if (clearOnClickListener != null) {
                        clearOnClickListener.onClick(view);
                    }
                }
            });
        }
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public Editable getText() {
        return editText.getText();
    }

    private void setMaxLengthText(int currentLenght) {
        if (maxLenghtTextView != null)
            maxLenghtTextView.setText(currentLenght + "/" + maxLeght);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setHasScrollView() {
        //解决ScrollView
        editText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // 解决scrollView中嵌套EditText导致不能上下滑动的问题
                if (canVerticalScroll(editText))
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    public static boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    private LayoutParams getImgLayoutParams(int widht, int height, int rightMargin) {
        LayoutParams params;
        if (widht == 0) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        } else {
            params = new LayoutParams(widht, height);
        }
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        switch (btnGravity) {
            case 1:
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            case 2:
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                break;
            case 3:
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
        }
        params.setMargins(btnMarginLeft, btnMarginTop, btnMarginRight, btnMarginBottom);
        params.rightMargin = rightMargin;
        return params;
    }

    /**
     * 初始化長度監聽和長度顯示文字
     */
    private void initLeght() {
        if (maxLeght > 0) {
            mInputFilter.add(new MaxTextLengthFilter(maxLeght));
        }
        if (isShowMaxLenght) {
            maxLenghtTextView = new TextView(context);
            maxLenghtTextView.setId(R.id.my_edit_max_length_text_id);
            maxLenghtTextView.setTextColor(maxLenghtTextColor);
            maxLenghtTextView.setTextSize(maxLenghtTextSize);
            LayoutParams textParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            textParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            textParams.setMargins(maxTextMarginLeft, maxTextMarginTop, maxTextMarginRight, maxTextMarginBottom);
            maxLenghtTextView.setLayoutParams(textParams);
            setMaxLengthText(0);
            this.addView(maxLenghtTextView);
        }
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    private boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private boolean isEmojiCharacter(char codePoint) {
        return codePoint == 0x0 || codePoint == 0x9 || codePoint == 0xA || codePoint == 0xD || codePoint >= 0x20 && codePoint <= 0xD7FF || codePoint >= 0xE000 && codePoint <= 0xFFFD;
    }


    public void setClearOnClickListener(OnClickListener clearOnClickListener) {
        this.clearOnClickListener = clearOnClickListener;
    }


    public void setLookOnClickListener(OnClickListener lookOnClickListener) {
        this.lookOnClickListener = lookOnClickListener;
    }

    //最大字數限制器
    class MaxTextLengthFilter implements InputFilter {
        private int mMaxLength;

        public MaxTextLengthFilter(int max) {
            mMaxLength = max;
        }

        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            int keep = mMaxLength - (dest.length() - (dend - dstart));
            if (keep < (end - start)) {
                if (!TextUtils.isEmpty(maxLengthTip)) {
                    ToastUtils.show(context, String.format(maxLengthTip, mMaxLength));
                }
            }
            if (keep <= 0) {
                return "";
            } else if (keep >= end - start) {
                return null;
            } else {
                return source.subSequence(start, start + keep);
            }
        }
    }

    class EmojiInputFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (containsEmoji(source.toString())) {
                //如果輸入了表情
                if (!TextUtils.isEmpty(inputIconTipText)) {
                    //提示文字不為空，就彈出提示,並移除掉表情
                    ToastUtils.show(context, inputIconTipText);
                    return "";
                }
                return "";
            }
            return source;
        }
    }

    //替換密碼輸入框顯示的密碼樣式
    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source;
            }

            public char charAt(int index) {
                return pwdChar.charAt(0);
            }

            public int length() {
                return mSource.length();
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end);
            }
        }
    }


}
