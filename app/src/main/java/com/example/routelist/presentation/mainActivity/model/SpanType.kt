package com.example.routelist.presentation.mainActivity.model

sealed interface SpanType {
    val spanSize: Int

    abstract class Two : SpanType {
        override val spanSize = 1
    }
}