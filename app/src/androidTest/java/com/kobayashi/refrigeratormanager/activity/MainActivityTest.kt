package com.kobayashi.refrigeratormanager.activity

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    // @Ruleではいけない
    @get:Rule
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    /**
     * 1.アクティビティが起動していること
     * 2.アクティビティが終了していること
     */
    @Test
    fun test_onCreate() {
        // MainActivityを起動
        val activity = rule.launchActivity(Intent())

        // 1
        assertFalse("MainActivity is not running", activity.isFinishing)

        // 2
        rule.finishActivity()
        assertTrue("MainActivity is running", activity.isFinishing)
    }

    /**
     * 1.MainTopFragmentが起動していること
     */
    @Test
    fun test_onCreate_fragment(){
        // MainActivityを起動
        val activity = rule.launchActivity(Intent())

        // 1
        val fragments = activity.supportFragmentManager.fragments
        assertEquals(1, fragments.size)
        assertTrue(fragments[0] is MainTopFragment)

        rule.finishActivity()
    }
}