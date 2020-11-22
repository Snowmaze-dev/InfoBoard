package ru.snowmaze.infoboard

import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
@Category(CheckModuleTest::class)
class CheckModulesTest {

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun checkAllModules() = checkModules {
//        module {
//            single {
//                Tests()
//            }
//            single {
//                Test1(get())
//            }
//        }
    }
}