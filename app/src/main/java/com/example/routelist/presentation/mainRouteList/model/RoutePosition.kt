package com.example.routelist.presentation.mainRouteList.model

import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel

sealed interface RoutePosition {

    val shapeAppearanceModel: ShapeAppearanceModel

    data object First : RoutePosition {

        override val shapeAppearanceModel: ShapeAppearanceModel =
            ShapeAppearanceModel().toBuilder().apply {
                setTopLeftCorner(CornerFamily.ROUNDED, 45F)
                setTopRightCorner(CornerFamily.ROUNDED, 45F)
            }.build()
    }

    data object Last : RoutePosition {

        override val shapeAppearanceModel: ShapeAppearanceModel =
            ShapeAppearanceModel().toBuilder().apply {
                setBottomLeftCorner(CornerFamily.ROUNDED, 45F)
                setBottomRightCorner(CornerFamily.ROUNDED, 45F)
            }.build()
    }

    data object Middle : RoutePosition {

        override val shapeAppearanceModel: ShapeAppearanceModel =
            ShapeAppearanceModel().toBuilder().build()
    }
}