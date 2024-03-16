package com.fl_w.kata09

import kotlin.collections.HashMap
import kotlin.collections.sumOf

interface Promo {
    fun calcPrice(unitPrice: Int, count: Int,): Int
}

class MultiBuyPromo(val quantity: Int, val price: Int) : Promo {

    override fun calcPrice(unitPrice: Int, count: Int): Int {
        val bundles = count / quantity
        val remaining = count % quantity
        return (bundles * price) + (remaining * unitPrice)
    }

}

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
        promo?.let { promos.put(sku, it) }

        return this
    }

    fun addPromo(sku: Char, promo: Promo): PriceCatalog {
        promos.put(sku, promo)
        return this
    }

    fun getPrice(sku: Char, count: Int): Int {
        val price = unitPrices[sku]
            ?: throw IllegalArgumentException("Price for SKU $sku not found")
        return promos[sku]?.calcPrice(price, count) ?: price * count
    }

    fun containsSku(sku: Char): Boolean {
        return unitPrices.containsKey(sku)
    }
}

