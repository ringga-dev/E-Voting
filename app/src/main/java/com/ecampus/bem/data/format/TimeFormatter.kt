package com.ecampus.bem.data.format

import com.github.mikephil.charting.formatter.ValueFormatter

class TimeFormatter(private val mValues: Array<String>) :  ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return if (value >= 0) {
            if (mValues.size > value.toInt()) {
                mValues[value.toInt()]
            } else ""
        } else {
            ""
        }
    }
}