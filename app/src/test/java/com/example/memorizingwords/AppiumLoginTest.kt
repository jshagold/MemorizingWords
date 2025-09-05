package com.example.memorizingwords

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.nativekey.AndroidKey
import io.appium.java_client.android.nativekey.KeyEvent
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.remote.AutomationName
import io.appium.java_client.remote.MobilePlatform
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.openqa.selenium.By
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.interactions.Pause
import org.openqa.selenium.interactions.PointerInput
import org.openqa.selenium.interactions.Sequence
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AppiumLoginTest {


    companion object {
        private lateinit var driver: AndroidDriver

        private val DEFAULT_APPIUM_ADDRESS = "http://localhost:4723"
        private val apkFilePath = "C:/Users/testroom/Desktop/TestFile/app-debug.apk"

        private fun makeDriver(): AndroidDriver {

            val options = UiAutomator2Options()
                .setPlatformName(MobilePlatform.ANDROID)
                .setApp(apkFilePath)
                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
                .setAppPackage("com.plantym.mediaservice.moazine")
                .setAppActivity("com.plantym.mediaservice.moazine.MainActivity")
                .setNoReset(false)  // 세션 종료 시 force-stop / 데이터 삭제 안 함
                .apply {
                    setCapability("appium:skipServerInstallation",true)
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
            driver.quit()
        }
    }

    private val testLoadingTime: Long = 5000


    private fun safeScreenshotPath(description: Description, tag: String = "FAIL"): File {
        val ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"))
        val dir = File("screenshots/${description.className}")
        dir.mkdirs()
        return File(dir, "${description.methodName}_${tag}_$ts.png")
    }

    private fun captureScreenshotFallback(description: Description) {
        // 1차: 현재 컨텍스트에서 캡처
        runCatching {
            val out = safeScreenshotPath(description)
            val src = (driver as TakesScreenshot).getScreenshotAs(OutputType.FILE)
            Files.copy(src.toPath(), out.toPath(), StandardCopyOption.REPLACE_EXISTING)
            println("📸 Saved screenshot: ${out.absolutePath}")
            return
        }.onFailure { println("⚠️ Screenshot (current context) failed: ${it.message}") }

        // 2차: NATIVE_APP으로 전환 후 재시도
        runCatching {
            val original = runCatching { driver.context }.getOrNull()
            runCatching { driver.context("NATIVE_APP") }
            val out = safeScreenshotPath(description, "NATIVE")
            val src = (driver as TakesScreenshot).getScreenshotAs(OutputType.FILE)
            Files.copy(src.toPath(), out.toPath(), StandardCopyOption.REPLACE_EXISTING)
            println("📸 Saved screenshot (NATIVE): ${out.absolutePath}")
            // 원래 컨텍스트 복귀(가능하면)
            runCatching { if (original != null) driver.context(original) }
        }.onFailure { println("❌ Screenshot (NATIVE) failed: ${it.message}") }
    }

    @JvmField
    @Rule
    val screenshotOnFailure: TestWatcher = object : TestWatcher() {
        override fun starting(description: Description) {
            println("▶️ start: ${description.className}.${description.methodName}")
        }
        override fun succeeded(description: Description) {
            println("✅ pass: ${description.className}.${description.methodName}")
        }
        override fun failed(e: Throwable?, description: Description) {
            println("❌ fail: ${description.className}.${description.methodName} -> ${e?.message}")
            captureScreenshotFallback(description)
        }
        override fun finished(description: Description) {
            println("⏹ finished: ${description.className}.${description.methodName}")
        }
    }



    @Test
    fun test_000_Waiting_And_Open_Popup_Authorization() {
        Thread.sleep(testLoadingTime)
        val nextBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/button_next"))
        nextBtn.click()
    }

//    @Test
    fun test_001_Click_Popup_Authorization() {
        Thread.sleep(testLoadingTime)
        println("testFindBackButton: ${driver.pageSource}", )
        val contexts = driver.contextHandles
        println("All contexts: $contexts")
        val allowBtn = driver.findElement(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_button"))
        allowBtn.click()
    }


    /**
     * 오늘 하루 보지 않기 설정
     */
//    @Test
    fun test_002_Close_Popup_TapTv() {
        Thread.sleep(testLoadingTime)
        println("testFindBackButton: ${driver.pageSource}", )
        val closeBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/button_dont_show_today"))
        closeBtn.click()
    }



    @Test
    fun test_003_PagerUIScreen() {
        Thread.sleep(testLoadingTime)
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
    fun test_004_Close_Advertise_Popup() {
        Thread.sleep(testLoadingTime)
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
    fun test_005_Click_Profile_Button() {
        Thread.sleep(testLoadingTime)
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
    fun test_006_Click_Login_Button_Login_Popup() {
        Thread.sleep(testLoadingTime)
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
    fun test_007_Input_ID_PW() {
        Thread.sleep(testLoadingTime)
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
    fun test_008_Click_Login_Button() {
        Thread.sleep(testLoadingTime)
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

    @Test
    fun test_009_Check_Success_Login() {
        Thread.sleep(testLoadingTime)
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
        val loginBtn = driver.findElement(By.xpath("//div[@class='main_top_text']") )

    }

}