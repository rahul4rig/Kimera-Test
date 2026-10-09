package com.example.data

import com.example.R
import com.example.data.model.StyleVariation

object SalonAssetProvider {

    val salonSuiteInteriorResId = R.drawable.salon_suite_interior_1790419881262
    val rohanClientPhotoResId = R.drawable.client_rohan_photo_1790419898233
    val defaultReferenceDrawableId = R.drawable.ref_textured_crop_1790418943614
    val defaultClientDrawableId = R.drawable.client_rohan_photo_1790419898233

    val defaultVariations = listOf(
        StyleVariation(
            id = 1,
            title = "Textured French Crop",
            subtitle = "Clean high-taper fade with natural cropped texture",
            tag = "Top Stylist Match",
            description = "Modern taper fade with blunt micro-fringe and choppy point-cut crown movement. Tailored to square and oval jawlines.",
            drawableResName = "style_var_one_1790418892068",
            fadeHeight = "Clean Fade",
            topLength = "1.5 inches",
            textureDetail = "Low Maintenance",
            productRec = "Daily Friendly",
            barberTip = "Texturize crown carefully around the whorl to prevent cowlick lifting.",
            maintenanceLevel = "4-5 weeks"
        ),
        StyleVariation(
            id = 2,
            title = "Mid Drop Taper Crop",
            subtitle = "Graduated drop fade curving behind the ears",
            tag = "High Definition",
            description = "A clean drop fade curving behind the ear down to the nape, paired with a blunt micro-fringe and heavy internal texturizing.",
            drawableResName = "style_var_two_1790418904339",
            fadeHeight = "Mid Drop (#1 to #2)",
            topLength = "1.75 inches",
            textureDetail = "Crisp Directional Flow",
            productRec = "Styling Powder",
            barberTip = "Keep the transition soft over the occipital bone.",
            maintenanceLevel = "3 weeks"
        ),
        StyleVariation(
            id = 3,
            title = "Scissor Soft Flow Crop",
            subtitle = "Zero-fade natural scissor taper for relaxed daily wear",
            tag = "Organic Flow",
            description = "Tailored exclusively using scissor-over-comb techniques. Maintains subtle ear clearing without exposing scalp. Ideal for clients wanting volume and natural wave.",
            drawableResName = "style_var_three_1790418918296",
            fadeHeight = "Scissor Taper",
            topLength = "2.0 inches",
            textureDetail = "Natural Texture",
            productRec = "Light Matte Clay",
            barberTip = "Comb with wide tooth rake while damp for effortless styling.",
            maintenanceLevel = "5-6 weeks"
        )
    )

    fun getVariationDrawableId(varId: Int): Int {
        return when (varId) {
            1 -> R.drawable.style_var_one_1790418892068
            2 -> R.drawable.style_var_two_1790418904339
            3 -> R.drawable.style_var_three_1790418918296
            else -> R.drawable.style_var_one_1790418892068
        }
    }
}
