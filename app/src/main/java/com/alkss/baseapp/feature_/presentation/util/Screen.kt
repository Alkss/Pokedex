package com.alkss.baseapp.feature_.presentation.util

/**
 * Represents the different screens in the delivery feature.
 *
 * @property route The route associated with the screen.
 */
sealed class Screen(val route: String) {
    /**
     * Represents the home screen.
     */
    data object HomeScreen: Screen("home_screen")

    /**
     * Represents the delivery screen.
     */
    data object DeliveryScreen: Screen("delivery_screen")

    /**
     * Represents the invoice details screen.
     */
    data object InvoiceDetailsScreen: Screen("invoice_details_screen")
}
