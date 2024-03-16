package com.fl_w.checkout

import kotlin.collections.HashMap
import kotlin.collections.sumOf

data class Promo(val count: Int, val price: Int)

class Checkout(
        private val catalog: PriceCatalog = PriceCatalog(),
        private val cart: HashMap<Char, Int> = HashMap()
) {

    fun scan(sku: Char): Checkout {
        if (catalog.containsSku(sku)) {
            cart[sku] = cart.getOrDefault(sku, 0) + 1
        }
        return this
    }

    fun total(): Int {
        return cart.entries.sumOf { (sku: Char, count: Int) -> catalog.getPrice(sku, count) }
    }
}

class PriceCatalog {

    private val unitPrices: HashMap<Char, Int> = HashMap()
    private val promos: HashMap<Char, Promo> = HashMap()

    fun addPricingRule(sku: Char, price: Int, promo: Promo? = null): PriceCatalog {
        unitPrices[sku] = price
        promo?.let {
            promos.put(sku, it)
        }

        return this
    }

    fun getPrice(sku: Char, count: Int): Int {
        val price = unitPrices[sku] ?: throw IllegalArgumentException("Price for SKU $sku not found")
        promos[sku]?.let {
            val bundles = count / it.count
            val remaining = count % it.count
            return (bundles * it.price) + (remaining * price)
        }

        return price * count
    }

    fun containsSku(sku: Char): Boolean {
        return unitPrices.containsKey(sku)
    }
}
