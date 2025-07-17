package com.example.memorizingwords

import com.example.memorizingwords.AppiumViewerTranslate.Companion
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
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Pause
import org.openqa.selenium.interactions.PointerInput
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.time.Duration


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AppiumViewerScrap {

    companion object {
        private lateinit var driver: AndroidDriver

        private val DEFAULT_APPIUM_ADDRESS = "http://localhost:4723"
        private val apkFilePath = "C:/Users/admin/Documents/moa/moazine-aos/app/build/outputs/apk/debug/app-debug.apk"

        private fun makeDriver(): AndroidDriver {

            val options = UiAutomator2Options()
                .setPlatformName(MobilePlatform.ANDROID)
                .setApp(apkFilePath)
                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
                .setAppPackage("com.plantym.mediaservice.moazine")
                .setAppActivity("com.plantym.mediaservice.moazine.MainActivity")
                .setNoReset(true)  // 세션 종료 시 force-stop / 데이터 삭제 안 함
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
            driver.activateApp("com.plantym.mediaservice.moazine")
        }

        @JvmStatic
        @AfterClass
        fun tearDown() {
            driver.quit()
        }
    }

    private val testLoadingTime: Long = 3000

    @Test
    fun test_001_Set_Home_Tab() {
        Thread.sleep(testLoadingTime)
        println("testFindBackButton: ${driver.pageSource}")
        val contexts = driver.contextHandles
        println("All contexts: $contexts")

        // 3. WebView 컨텍스트 찾기
        val webviewContext = contexts.find { it.contains("WEBVIEW") }
            ?: throw RuntimeException("No WEBVIEW context found")

        println("webviewContext: $webviewContext")

        // 4. 컨텍스트 전환
        driver.context(webviewContext)
        println("Switched to context: $webviewContext")

        val homeTab = driver.findElement(By.xpath("//div[@class='item btn_cursor ic_bt_home']") )
        homeTab.click()
    }

    @Test
    fun test_002_Select_Magazine() {
        Thread.sleep(testLoadingTime)
        println("testFindBackButton: ${driver.pageSource}")
        val contexts = driver.contextHandles
        println("All contexts: $contexts")

        val firstMagazine = driver.findElement(By.xpath("//div[contains(@class, 'main_magazine_layer')]//div[contains(@class, 'swiper-slide-active')][1]/img") )

        driver.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", firstMagazine)
        WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.elementToBeClickable(firstMagazine))

        firstMagazine.click()
    }

    @Test
    fun test_003_Click_View_Btn() {
        Thread.sleep(testLoadingTime)
        println("testFindBackButton: ${driver.pageSource}")
        val contexts = driver.contextHandles
        println("All contexts: $contexts")

        val viewBtn = driver.findElement(By.xpath("//span[@class='btn_cursor btn_view']") )
        viewBtn.click()

    }


    @Test
    fun test_004_Open_Viewer_Nav_Tab() {
        Thread.sleep(testLoadingTime * 2)
        val appContext = "NATIVE_APP"
        // 4. 컨텍스트 전환
        driver.context(appContext)
        println("Switched to context: $appContext")
        println("Native App: ${driver.pageSource}", )

        // 1) 터치 좌표
        val x = 540
        val y = 960

// 2) 손가락(pen) 정의
        val finger = PointerInput(PointerInput.Kind.TOUCH, "finger")

// 3) 터치-다운 ➜ 50 ms 대기 ➜ 터치-업 시퀀스
        val tap = org.openqa.selenium.interactions.Sequence(finger, 0).apply {
            addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
            addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
            addAction(Pause(finger, Duration.ofMillis(50)))
            addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))
        }

// 4) 수행
        driver.perform(listOf(tap))

    }

    @Test
    fun test_005_Close_Viewer_Button() {
        Thread.sleep(testLoadingTime)
        println("Native App: ${driver.pageSource}", )

        val closeBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/iv_close") )
        closeBtn.click()
    }

    @Test
    fun test_006_Click_Article() {
        Thread.sleep(testLoadingTime)
        // 3. WebView 컨텍스트 찾기
        val contexts = driver.contextHandles
        val webViewContext = contexts.find { it.contains("WEBVIEW") }
            ?: throw RuntimeException("No WEBVIEW context found")

        println("webviewContext: $webViewContext")

        // 4. 컨텍스트 전환
        driver.context(webViewContext)
        println("Switched to context: $webViewContext")
        println("Web View: ${driver.pageSource}", )

        val articleBtn = driver.findElement(By.xpath("//div[contains(@class, 'main') and contains(@class, 'itembox')]/div[contains(@class, 'swiper-container')]//div[contains(@class, 'swiper-slide')][2]//span[contains(@class, 'detail') and contains(@class, 'btn_cursor')]"))


// 2. 대상 요소 중앙 배치
        driver.executeScript("arguments[0].scrollIntoView({block:'center',inline:'nearest'});", articleBtn)

// 3. 클릭 가능해질 때까지 재확인 (locator로 재검색)
        val clickable = WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'main') and contains(@class, 'itembox')]/div[contains(@class, 'swiper-container')]//div[contains(@class, 'swiper-slide')][2]//span[contains(@class, 'detail') and contains(@class, 'btn_cursor')]")))
        clickable.click()
    }
}