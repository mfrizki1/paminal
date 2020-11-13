package id.calocallo.sicape.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.widget.TextViewCompat
import id.calocallo.sicape.R
import id.calocallo.sicape.utils.ext.OnTextChangedListener
import id.calocallo.sicape.utils.ext.onFocusChanged
import id.co.iconpln.smartcity.widget.WidgetEditTextAbs
import kotlinx.android.synthetic.main.view_input_edit_text.view.*


class InputEditText(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs),
    WidgetEditTextAbs {
    override fun getEditText() = edit_text

    private var listener: OnClickListener? = null

    fun getTextInputLayout() = text_input_layout

    var maxLength: Int = 0
        set(value) {
            if (value > 0) {
                getEditText().filters = arrayOf<InputFilter>(InputFilter.LengthFilter(value))
            }
        }

    var isErrorEnabled: Boolean
        get() = getTextInputLayout().isErrorEnabled
        set(value) {
            getTextInputLayout().isErrorEnabled = value
        }
    var isHintEnabled: Boolean
        get() = getTextInputLayout().isHintEnabled
        set(value) {
            getTextInputLayout().isErrorEnabled = value
        }
    var text: String?
        get() = getEditText().text?.toString()
        set(value) {
            getEditText().setText(value)
        }

    var inputType: Int
        get() = getEditText().inputType
        set(value) {
            getEditText().inputType = value
        }
    var hint: String?
        get() = getTextInputLayout().hint?.toString()
        set(value) {
            getTextInputLayout().hint = value
        }
    var error: String?
        get() = getTextInputLayout().error?.toString()
        set(value) {
            getTextInputLayout().error = value
        }
    var lines: Int = 0
        set(value) {
            getEditText().setLines(value)
        }
    var maxLines: Int
        get() = getEditText().maxLines
        set(value) {
            getEditText().maxLines = value
        }
    var textAppearance = 0
        set(value) {
            TextViewCompat.setTextAppearance(getEditText(), value)
        }
    var transformationMethod: TransformationMethod
        get() = getEditText().transformationMethod
        set(value) {
            getEditText().transformationMethod = value
        }
    var isPasswordVisibilityToggleEnabled: Boolean
        get() = getTextInputLayout().isPasswordVisibilityToggleEnabled
        set(value) {
            getTextInputLayout().isPasswordVisibilityToggleEnabled = value
        }
    var imeOptions = 0
        set(value) {
            getEditText().imeOptions = value
        }
    var drawableRight: Drawable? = null
        set(value) {
            getEditText().setCompoundDrawablesWithIntrinsicBounds(null, null, value, null)
        }
    var showBottomLine: Boolean = true
        set(value) {
            if (!value) {
                getEditText().setBackgroundColor(resources.getColor(R.color.white))
            }
            field = value
        }

    override fun setFocusable(focusable: Boolean) {
        getEditText().isFocusable = focusable
        getTextInputLayout().isFocusable = focusable
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        getEditText().addTextChangedListener(watcher)
    }

    fun removeTextChangedListener(watcher: TextWatcher) {
        getEditText().removeTextChangedListener(watcher)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        listener = l
    }

    init {
        View.inflate(context, R.layout.view_input_edit_text, this)

        val attrs = context.obtainStyledAttributes(attrs, R.styleable.InputEditText)
        isErrorEnabled = attrs.getBoolean(R.styleable.InputEditText_ietErrorEnabled, true)
        isHintEnabled = attrs.getBoolean(R.styleable.InputEditText_ietHintEnabled, true)
        text = attrs.getString(R.styleable.InputEditText_ietText)
        hint = attrs.getString(R.styleable.InputEditText_ietHint)
        error = attrs.getString(R.styleable.InputEditText_ietError)
        if (attrs.hasValue(R.styleable.InputEditText_android_inputType)) {
            inputType = attrs.getInt(R.styleable.InputEditText_android_inputType, EditorInfo.TYPE_NULL)
        }
        if (attrs.hasValue(R.styleable.InputEditText_android_textAppearance)) {
            textAppearance = attrs.getResourceId(R.styleable.InputEditText_android_textAppearance, 0)
        }
        lines = attrs.getInt(R.styleable.InputEditText_ietLines, 1)
        maxLines = attrs.getInt(R.styleable.InputEditText_ietMaxLines, 1)
        isPasswordVisibilityToggleEnabled = attrs.getBoolean(R.styleable.InputEditText_ietPasswordToggleEnabled, false)
        drawableRight = attrs.getDrawable(R.styleable.InputEditText_android_drawableRight)
        isFocusable = attrs.getBoolean(R.styleable.InputEditText_android_focusable, true)
        imeOptions = attrs.getInt(R.styleable.InputEditText_android_imeOptions, EditorInfo.IME_ACTION_DONE)
        maxLength = attrs.getInt(R.styleable.InputEditText_ietMaxLength, 0)


        layout_main.setOnClickListener { listener?.onClick(this) }
        edit_text.setOnClickListener { listener?.onClick(this) }
        showBottomLine = attrs.getBoolean(R.styleable.InputEditText_showBottomLine, true)

        attrs.recycle()
        getEditText().onFocusChanged { checkText() }
        getEditText().OnTextChangedListener {
            if (it.isNotBlank()) {
                error = null
                isHintEnabled = true
            }
        }

    }

    private fun checkText() {
        if (!error.isNullOrBlank()) isHintEnabled = !isHintEnabled
    }

}