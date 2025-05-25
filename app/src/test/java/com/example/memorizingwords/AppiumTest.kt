package com.example.memorizingwords

import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.remote.AutomationName
import io.appium.java_client.remote.MobilePlatform
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.openqa.selenium.By
import java.net.URL


class AppiumTest {


    companion object {
        private lateinit var driver: AndroidDriver

        private val DEFAULT_APPIUM_ADDRESS = "http://localhost:4723"
        private val apkFilePath = "C:/Users/admin/Documents/moa/moazine-aos/app/build/outputs/apk/debug/app-debug.apk"

        private fun makeDriver(): AndroidDriver {

            val options = UiAutomator2Options()
                .setDeviceName("R9TX706CB9J")
                .setPlatformName(MobilePlatform.ANDROID)
                .setApp(apkFilePath)
                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
                .setAppPackage("com.plantym.mediaservice.moazine")
                .setAppActivity("com.plantym.mediaservice.moazine.MainActivity")
                .setNoReset(false)

            return AndroidDriver(URL(DEFAULT_APPIUM_ADDRESS), options)
        }

        @JvmStatic
        @BeforeClass
        fun setup() {
            driver = makeDriver()
        }

        @JvmStatic
        @AfterClass
        fun tearDown() {
//        driver.quit()
        }
    }

    @Test
    fun test_0_UpdatePopup() {
        Thread.sleep(3000)
        val closeBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/button_left"))
        closeBtn.click()
    }


    @Test
    fun test_1_WaitingScreen() {
        Thread.sleep(3000)
        println("testFindBackButton: ${driver.pageSource}", )
        val contexts = driver.contextHandles
        println("All contexts: $contexts")
        val nextBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/button_next"))
        nextBtn.click()
//        val nextBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/button_next"))
//        nextBtn.click()
//        val eraseBtn = driver.findElement(AppiumBy.accessibilityId("erase_btn"))
//        println("eraseBtn Text = ${eraseBtn.getAttribute("stateDescription")}")
//
//        eraseBtn.click()


    }

    @Test
    fun test_2_PagerUIScreen() {
        println("testFindBackButton: ${driver.pageSource}", )

        // 2. 컨텍스트 목록 출력
        val contexts = driver.contextHandles
        println("Available contexts: $contexts")

        // 3. WebView 컨텍스트 찾기
        val webviewContext = contexts.find { it.contains("WEBVIEW") }
            ?: throw RuntimeException("No WEBVIEW context found")

        println("webviewContext: $webviewContext")

        // 4. 컨텍스트 전환
        driver.context(webviewContext)
        println("Switched to context: $webviewContext")

        // 5. WebView 내부 요소 상호작용
        val button = driver.findElement(By.xpath("//span[text()='다음']") )
        button.click()
        button.click()
        button.click()
        button.click()

        // 6. 다시 네이티브로 전환
        driver.context("NATIVE_APP")


//        val nextBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/button_next"))
//        nextBtn.click()




    }

}