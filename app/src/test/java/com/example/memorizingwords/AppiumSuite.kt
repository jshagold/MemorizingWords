package com.example.memorizingwords

import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    value = [
        AppiumLoginTest::class,
        AppiumViewerArticleListTest::class,
        AppiumViewerTranslate::class,
        AppiumTTSRead::class,
    ]
)
class AppiumSuite