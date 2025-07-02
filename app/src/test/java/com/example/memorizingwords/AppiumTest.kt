package com.example.memorizingwords

import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.remote.AutomationName
import io.appium.java_client.remote.MobilePlatform
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.openqa.selenium.By
import java.net.URL

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AppiumTest {


    companion object {
        private lateinit var driver: AndroidDriver

        private val DEFAULT_APPIUM_ADDRESS = "http://localhost:4723"
        private val apkFilePath = "C:/Users/admin/Documents/moa/moazine-aos/app/build/outputs/apk/debug/app-debug.apk"

        private fun makeDriver(): AndroidDriver {

            val options = UiAutomator2Options()
//                .setDeviceName("R9TX706CB9J")
                .setPlatformName(MobilePlatform.ANDROID)
                .setApp(apkFilePath)
                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
                .setAppPackage("com.plantym.mediaservice.moazine")
                .setAppActivity("com.plantym.mediaservice.moazine.MainActivity")
                .setNoReset(false)
                .apply {
                    setCapability("chromedriverAutodownload", true)
                    setCapability("ensureWebviewsHavePages", false)
                    setCapability("fullContextList", true)
                    setCapability("autoWebviewTimeout", 20000)
                }



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
    fun test_0_Waiting_And_Open_Popup_Authorization() {
        Thread.sleep(3000)
        val nextBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/button_next"))
        nextBtn.click()
    }

    @Test
    fun test_1_Click_Popup_Authorization() {
        Thread.sleep(3000)
        println("testFindBackButton: ${driver.pageSource}", )
        val contexts = driver.contextHandles
        println("All contexts: $contexts")
        val allowBtn = driver.findElement(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_button"))
        allowBtn.click()
    }

    @Test
    fun test_2_Close_Popup_TapTv() {
        Thread.sleep(3000)
        val closeBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/iv_close"))
        closeBtn.click()
    }



    @Test
    fun test_3_PagerUIScreen() {
        Thread.sleep(3000)
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
//        driver.context("NATIVE_APP")


//        val nextBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/button_next"))
//        nextBtn.click()

    }

    @Test
    fun test_4_Close_Advertise_Popup() {
        Thread.sleep(3000)
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
        val button = driver.findElement(By.xpath("//span[text()='닫기']") )
        button.click()
    }

    @Test
    fun test_5_Click_Profile_Button() {
        Thread.sleep(3000)
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
        val button = driver.findElement(By.xpath("//img[@class='profile btn_cursor']") )
        button.click()
    }


    @Test
    fun test_6_Click_Login_Button_Login_Popup() {
        Thread.sleep(3000)
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
        val button = driver.findElement(By.xpath("//span[text()='로그인 하기']") )
        button.click()
    }

    @Test
    fun test_7_Input_ID_PW() {
        Thread.sleep(3000)
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
        val inputElementID = driver.findElement(By.xpath("//input[@placeholder='아이디를 입력해주세요.']") )
        inputElementID.sendKeys("boss5821")

        val inputElementPW = driver.findElement(By.xpath("//input[@placeholder='영문,숫자,특수문자 조합 8자리 이상']") )
        inputElementPW.sendKeys("moa5821!")
    }

    @Test
    fun test_8_Click_Login_Button() {
        Thread.sleep(3000)
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
        val loginBtn = driver.findElement(By.xpath("//div[@class='btn_full btn_login active']") )
        loginBtn.click()

    }

}