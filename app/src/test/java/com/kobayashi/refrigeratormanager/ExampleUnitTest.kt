package com.kobayashi.refrigeratormanager

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        val inte = 1
        val inte2 = 2
        val inte3 = 3
        val result = testInt(inte){
            inte2 * inte3
        }

        assertEquals(6, result)

        val res2 = addOnes{
            1 + 1
        }
    }

    fun testInt(a : Int, b : () -> Int):Int {
        return b() * a
    }

    fun addOnes(a : () -> Int):Int {
        return a()
    }
}