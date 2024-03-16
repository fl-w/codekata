package com.fl_w.kata09

import kotlin.test.Test
import kotlin.test.assertEquals

class CheckoutTest {

    private val pricing_rules = PriceCatalog()
                .addPromo('A', MultiBuyPromo(3, 130))
                .addPromo('B',  MultiBuyPromo(2, 45))
                .addPricingRule('A', 50)
                .addPricingRule('B', 30)
                .addPricingRule('C', 20)
                .addPricingRule('D', 15)

    private fun checkout(basket: String): Int {
        val co = Checkout(pricing_rules)
        basket.forEach { c -> co.scan(c) }
        return co.total()
    }

    @Test
    fun testTotals() {
        assertEquals(0, checkout(""))
        assertEquals(50, checkout("A"))
        assertEquals(80, checkout("AB"))
        assertEquals(115, checkout("CDBA"))

        assertEquals(100, checkout("AA"))
        assertEquals(130, checkout("AAA"))
        assertEquals(180, checkout("AAAA"))
        assertEquals(230, checkout("AAAAA"))
        assertEquals(260, checkout("AAAAAA"))

        assertEquals(160, checkout("AAAB"))
        assertEquals(175, checkout("AAABB"))
        assertEquals(190, checkout("AAABBD"))
        assertEquals(190, checkout("DABABA"))
    }

    @Test
    fun testIncremental() {
        val co = Checkout(pricing_rules)
        assertEquals(0, co.total())
        co.scan('A');  assertEquals(50, co.total())
        co.scan('B');  assertEquals(80, co.total())
        co.scan('A');  assertEquals(130, co.total())
        co.scan('A');  assertEquals(160, co.total())
        co.scan('B');  assertEquals(175, co.total())
    }

}
