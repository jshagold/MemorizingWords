package com.example.memorizingwords

import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    value = [
        AppiumLoginTest::class,
        AppiumViewerTest::class
    ]
)
class AppiumSuite