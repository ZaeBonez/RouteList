package com.example.routelist.presentation.mainRouteList.model

sealed interface SpanType {
    val spanSize: Int

    abstract class Two : SpanType {
        override val spanSize = 1
    }
}