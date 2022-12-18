package com.ruben.bartender.presentation.ui.common

import android.text.TextUtils
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import com.ruben.bartender.R

/**
 * Created by Ruben Quadros on 18/12/22
 **/
@Composable
fun HtmlText(
    modifier: Modifier,
    lines: Int? = null,
    @ColorRes textColor: Int,
    fontSize: Float,
    htmlText: String
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).apply {
                setTextColor(ContextCompat.getColor(context, textColor))
                setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                lines?.let {
                    maxLines = it
                    ellipsize = TextUtils.TruncateAt.END
                }
                this.typeface = try {
                    ResourcesCompat.getFont(context, R.font.libre_baskerville)
                } catch (e: Exception) {
                    null
                }
            }
        },
        update = { textView ->
            textView.setTextColor(ContextCompat.getColor(textView.context, textColor))
            textView.text = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
    )
}