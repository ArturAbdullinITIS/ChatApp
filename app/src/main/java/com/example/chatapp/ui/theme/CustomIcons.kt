package com.example.chatapp.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val PasswordIsVisible: ImageVector
    get() {
        if (_HeroiconsEye != null) return _HeroiconsEye!!

        _HeroiconsEye = ImageVector.Builder(
            name = "eye",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Transparent),
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1.5f,
                strokeLineJoin = StrokeJoin.Miter
            ) {
                moveTo(2.036f, 12.322f)
                arcToRelative(1.012f, 1.012f, 0f, false, true, 0f, -0.639f)
                curveTo(3.423f, 7.51f, 7.36f, 4.5f, 12f, 4.5f)
                curveToRelative(4.638f, 0f, 8.573f, 3.007f, 9.963f, 7.178f)
                curveToRelative(0.07f, 0.207f, 0.07f, 0.431f, 0f, 0.639f)
                curveTo(20.577f, 16.49f, 16.64f, 19.5f, 12f, 19.5f)
                curveToRelative(-4.638f, 0f, -8.573f, -3.007f, -9.963f, -7.178f)
                close()
            }
            path(
                fill = SolidColor(Color.Transparent),
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1.5f,
                strokeLineJoin = StrokeJoin.Miter
            ) {
                moveTo(15f, 12f)
                arcToRelative(3f, 3f, 0f, true, true, -6f, 0f)
                arcToRelative(3f, 3f, 0f, false, true, 6f, 0f)
                close()
            }
        }.build()

        return _HeroiconsEye!!
    }

private var _HeroiconsEye: ImageVector? = null



val PasswordNotVisible: ImageVector
    get() {
        if (_RadixEyeClosed != null) return _RadixEyeClosed!!

        _RadixEyeClosed = ImageVector.Builder(
            name = "eye-closed",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 15f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(5.58284f, 12.5867f)
                curveTo(5.5113f, 12.8531f, 5.23703f, 13.0113f, 4.97054f, 12.9402f)
                curveTo(4.70421f, 12.8686f, 4.54496f, 12.5943f, 4.61605f, 12.3279f)
                lineTo(5.58284f, 12.5867f)
                close()
                moveTo(10.3826f, 12.3279f)
                curveTo(10.4539f, 12.5945f, 10.2956f, 12.8686f, 10.0291f, 12.9402f)
                curveTo(9.76261f, 13.0113f, 9.48834f, 12.8531f, 9.41681f, 12.5867f)
                lineTo(10.3826f, 12.3279f)
                close()
                moveTo(14.1375f, 6.15505f)
                curveTo(14.2977f, 5.98633f, 14.5595f, 5.94799f, 14.7644f, 6.07595f)
                curveTo(14.9692f, 6.20395f, 15.0496f, 6.45576f, 14.9685f, 6.6736f)
                lineTo(14.9236f, 6.76442f)
                curveTo(14.4845f, 7.46696f, 13.962f, 8.1052f, 13.3689f, 8.66188f)
                lineTo(14.5711f, 9.86402f)
                lineTo(14.6355f, 9.94214f)
                curveTo(14.7636f, 10.1362f, 14.7419f, 10.4002f, 14.5711f, 10.571f)
                curveTo(14.4003f, 10.7419f, 14.1363f, 10.7636f, 13.9422f, 10.6355f)
                lineTo(13.8641f, 10.571f)
                lineTo(12.5994f, 9.3064f)
                curveTo(11.8032f, 9.90116f, 10.9077f, 10.3602f, 9.93243f, 10.6492f)
                lineTo(10.3826f, 12.3279f)
                lineTo(9.89923f, 12.4568f)
                lineTo(9.41681f, 12.5867f)
                lineTo(8.95783f, 10.8777f)
                curveTo(8.48648f, 10.958f, 7.99975f, 10.9998f, 7.49983f, 10.9998f)
                curveTo(6.9991f, 10.9998f, 6.51194f, 10.9563f, 6.03987f, 10.8757f)
                lineTo(5.58284f, 12.5867f)
                lineTo(5.09945f, 12.4568f)
                lineTo(4.61605f, 12.3279f)
                lineTo(5.06527f, 10.6492f)
                curveTo(4.09036f, 10.3601f, 3.19423f, 9.90202f, 2.39829f, 9.30738f)
                lineTo(1.1356f, 10.571f)
                curveTo(0.940342f, 10.7662f, 0.623806f, 10.7662f, 0.428567f, 10.571f)
                curveTo(0.233426f, 10.3758f, 0.233415f, 10.0593f, 0.428567f, 9.86402f)
                lineTo(1.62973f, 8.66188f)
                curveTo(1.10375f, 8.1681f, 0.633248f, 7.61f, 0.227396f, 6.99977f)
                lineTo(0.0760295f, 6.76442f)
                lineTo(0.0311078f, 6.6736f)
                curveTo(-0.0498844f, 6.45577f, 0.0304399f, 6.20394f, 0.235208f, 6.07595f)
                curveTo(0.440189f, 5.94795f, 0.701937f, 5.98637f, 0.862159f, 6.15505f)
                lineTo(0.923682f, 6.23513f)
                lineTo(1.20102f, 6.65114f)
                curveTo(2.63903f, 8.68678f, 4.86748f, 9.99974f, 7.49983f, 9.99976f)
                curveTo(10.3076f, 9.99976f, 12.6564f, 8.50638f, 14.076f, 6.23513f)
                lineTo(14.1375f, 6.15505f)
                close()
            }
        }.build()

        return _RadixEyeClosed!!
    }

private var _RadixEyeClosed: ImageVector? = null


val ArrowBack: ImageVector
    get() {
        if (_VscodeCodiconsArrowCircleLeft != null) return _VscodeCodiconsArrowCircleLeft!!

        _VscodeCodiconsArrowCircleLeft = ImageVector.Builder(
            name = "arrow-circle-left",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(7.14645f, 10.8536f)
                lineTo(4.64645f, 8.35355f)
                curveTo(4.45118f, 8.15829f, 4.45118f, 7.84171f, 4.64645f, 7.64645f)
                lineTo(7.14645f, 5.14645f)
                curveTo(7.34171f, 4.95118f, 7.65829f, 4.95118f, 7.85355f, 5.14645f)
                curveTo(8.04882f, 5.34171f, 8.04882f, 5.65829f, 7.85355f, 5.85355f)
                lineTo(6.20711f, 7.5f)
                horizontalLineTo(11f)
                curveTo(11.2761f, 7.5f, 11.5f, 7.72386f, 11.5f, 8f)
                curveTo(11.5f, 8.27614f, 11.2761f, 8.5f, 11f, 8.5f)
                horizontalLineTo(6.20711f)
                lineTo(7.85355f, 10.1464f)
                curveTo(8.04882f, 10.3417f, 8.04882f, 10.6583f, 7.85355f, 10.8536f)
                curveTo(7.65829f, 11.0488f, 7.34171f, 11.0488f, 7.14645f, 10.8536f)
                close()
                moveTo(1f, 8f)
                curveTo(1f, 11.866f, 4.13401f, 15f, 8f, 15f)
                curveTo(11.866f, 15f, 15f, 11.866f, 15f, 8f)
                curveTo(15f, 4.13401f, 11.866f, 1f, 8f, 1f)
                curveTo(4.13401f, 1f, 1f, 4.13401f, 1f, 8f)
                close()
                moveTo(8f, 14f)
                curveTo(4.68629f, 14f, 2f, 11.3137f, 2f, 8f)
                curveTo(2f, 4.68629f, 4.68629f, 2f, 8f, 2f)
                curveTo(11.3137f, 2f, 14f, 4.68629f, 14f, 8f)
                curveTo(14f, 11.3137f, 11.3137f, 14f, 8f, 14f)
                close()
            }
        }.build()

        return _VscodeCodiconsArrowCircleLeft!!
    }

private var _VscodeCodiconsArrowCircleLeft: ImageVector? = null



