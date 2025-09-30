package com.example.memorizingwords

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
import org.junit.runners.MethodSorters
import org.openqa.selenium.By
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.interactions.Pause
import org.openqa.selenium.interactions.PointerInput
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
class AppiumViewerArticleListTest {

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
    fun test_000_PagerUIScreen() {
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
        
    }


    @Test
    fun test_001_Set_Home_Tab() {
        Thread.sleep(testLoadingTime)
        println("testFindBackButton: ${driver.pageSource}", )
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
    fun test_010_Click_MyBook_Tab() {
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
//        driver.context(webviewContext)
//        println("Switched to context: $webviewContext")

        // 5. WebView 내부 요소 상호작용
        val myBookTab = driver.findElement(By.xpath("//div[@class='item btn_cursor ic_bt_book']") )
        myBookTab.click()
    }

    //    @Test
    fun test_011_Click_Offline_Storage_Button() {
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
//        driver.context(webviewContext)
//        println("Switched to context: $webviewContext")

        // 5. WebView 내부 요소 상호작용
        val offLineBtn = driver.findElement(By.xpath("//span[@class='offline btn_cursor']") )
        offLineBtn.click()
    }

    @Test
    fun test_012_Click_Magazine() {
        Thread.sleep(testLoadingTime)
        println("testFindBackButton: ${driver.pageSource}", )

        // 2. 컨텍스트 목록 출력
        val contexts = driver.contextHandles
        println("Available contexts: $contexts")

        // 3. WebView 컨텍스트 찾기
        val webviewContext = contexts.find { it.contains("WEBVIEW") }
            ?: throw RuntimeException("No WEBVIEW context found")

        println("webviewContext: $webviewContext")


        val firstMagazineViewBtn = driver.findElement(By.xpath("//div/div[1]//span[contains(@class, 'btn_cursor') and contains(@class, 'bottom_btn')]") )
        firstMagazineViewBtn.click()





        // 5. WebView 내부 요소 상호작용
//        val offLineBtn = driver.findElement(By.xpath("//span[@class='offline btn_cursor']") )
//        offLineBtn.click()
    }

    @Test
    fun test_013_Open_Viewer_Nav_Tab() {
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


//        val curlView = driver.findElement(By.xpath("//div/div[1]//span[contains(@class, 'btn_cursor') and contains(@class, 'bottom_btn')]") )
//        curlView.click()
    }

    @Test
    fun test_014_Click_Article_List_Button() {
        Thread.sleep(testLoadingTime)
        println("Native App: ${driver.pageSource}", )


        val scrapBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/menu_toc_list") )
        scrapBtn.click()
    }

    @Test
    fun test_015_Select_Article_From_List() {
        Thread.sleep(testLoadingTime)
        println("Native App: ${driver.pageSource}", )

        val n = 2   // 0-based index you want
        val locator = AppiumBy.androidUIAutomator(
            // 한 줄 문자열이어야 파서가 오류 없이 읽습니다
            "new UiScrollable(new UiSelector().resourceId(\"com.plantym.mediaservice.moazine:id/rv_toc_list\"))" +
                    ".getChildByInstance(new UiSelector().className(\"android.view.ViewGroup\")," + n + ");"
        )

        driver.findElement(locator).click()
    }

    @Test
    fun test_016_Click_Text_Viewer_Button() {
        Thread.sleep(testLoadingTime)
        println("Native App: ${driver.pageSource}", )


        val textViewerBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/menu_item") )
        textViewerBtn.click()
    }


    @Test
    fun test_017_Close_Text_Viewer_Button() {
        Thread.sleep(testLoadingTime)
        println("Native App: ${driver.pageSource}", )


        val closeBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/iv_close") )
        closeBtn.click()
    }

    @Test
    fun test_017_Close_Viewer_Button() {
        Thread.sleep(testLoadingTime)
        println("Native App: ${driver.pageSource}", )


        val closeBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/iv_close") )
        closeBtn.click()
    }








//    @Test
//    fun test_016_Select_Scrap_Folder_From_Popup() {
//        Thread.sleep(testLoadingTime)
//        println("Native App: ${driver.pageSource}", )
//
//
//        val recycler = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/rv_scrap_dir_list"))
//        val items = recycler.findElements(AppiumBy.className("android.widget.TextView"))
//        items[0].click()
//
//        val confirmBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/button_confirm") )
//        confirmBtn.click()
//    }
//
//    @Test
//    fun test_017_Close_Confirm_Popup() {
//        Thread.sleep(testLoadingTime)
//        println("Native App: ${driver.pageSource}", )
//
//        val leftCloseBtn = driver.findElement(AppiumBy.id("com.plantym.mediaservice.moazine:id/button_left") )
//        leftCloseBtn.click()
//
//        // 뒤로가기
//        driver.pressKey(KeyEvent(AndroidKey.BACK))
//    }


    /**
     * 매거진 보기를 눌러서 다운로드중 progress ui가 나오자 마자 시스템 뒤로가기 버튼을 누르면 앱 크래시
     */
//    @Test
    fun test_xxx_Crash_App() {
        Thread.sleep(testLoadingTime)
//        println("testFindBackButton: ${driver.pageSource}", )

        // 2. 컨텍스트 목록 출력
        val contexts = driver.contextHandles
        println("Available contexts: $contexts")

        val appContext = "NATIVE_APP"

        // 3. WebView 컨텍스트 찾기
        val webviewContext = contexts.find { it.contains("WEBVIEW") }
            ?: throw RuntimeException("No WEBVIEW context found")
        println("webviewContext: $webviewContext")



        // 5. WebView 내부 요소 상호작용
        val firstMagazineViewBtn = driver.findElement(By.xpath("//div/div[1]//span[contains(@class, 'btn_cursor') and contains(@class, 'bottom_btn')]") )
        firstMagazineViewBtn.click()

        // 4. 컨텍스트 전환
        driver.context(appContext)
//        println("Switched to context: $appContext")
//        println("app pageSource: ${driver.pageSource}", )

        val locator = AppiumBy.id("com.plantym.mediaservice.moazine:id/magazine_download_info_view")

//        val wait = FluentWait(driver)
//            .withTimeout(Duration.ofSeconds(6))        // ProgressView가 5초 안에 뜬다고 가정
//            .pollingEvery(Duration.ofMillis(100))      // 150 ms 간격으로 빠르게 확인
//            .ignoring(NoSuchElementException::class.java)
//            .until { drv ->
//                val list = drv.findElements(locator)
//                println("POLL: found=${list.size}")
//                if (list.isEmpty()) null else list[0]   // null 이면 계속 polling
//            }

        WebDriverWait(driver, Duration.ofSeconds(8))
            .pollingEvery(Duration.ofMillis(100))
            .ignoring(NoSuchElementException::class.java)
            .until(ExpectedConditions.presenceOfElementLocated(locator))

        println("✅ magazine_download_info_view 발견!")


        // 뒤로가기
        driver.pressKey(KeyEvent(AndroidKey.BACK))
    }

}