import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.github.leuludyha.domain.util.TestTag
import com.github.leuludyha.ibdb.presentation.navigation.TabDescriptor

/**
 * Uses a [ComposeTestRule] created via [createEmptyComposeRule] that allows setup before the activity
 * is launched via [onBefore]. Assertions on the view can be made in [onAfterLaunched].
 */
inline fun <reified A: Activity> ComposeTestRule.launch(
    onBefore: () -> Unit = {},
    intentFactory: (Context) -> Intent = {
        Intent(
            ApplicationProvider.getApplicationContext(),
            A::class.java
        )
    },
    onAfterLaunched: ComposeTestRule.() -> Unit
) {
    onBefore()

    val context = ApplicationProvider.getApplicationContext<Context>()
    ActivityScenario.launch<A>(intentFactory(context))

    onAfterLaunched()
}

/**
 * From a [TabDescriptor], return the test tag this [TabDescriptor] will have to
 * find it with the [onNodeWithTag]
 */
fun getBottomToolbarTestTagFrom(descriptor: TabDescriptor): String {
    return "bottomtoolbar::tab_item::${descriptor.displayName}"
}

/**
 * Click on the bottom tab specified by [TabDescriptor]
 * This method can be used on a [ComposeTestRule] instance like such
 * [composeTestRule.clickOnBottomTab(tab)]
 */
fun ComposeTestRule.clickOnBottomTab(descriptor: TabDescriptor) {
    this.onNodeWithTag(getBottomToolbarTestTagFrom(descriptor)).performClick()
}

/**
 * Return the [SemanticsNodeInteraction] which has the specified [TestTag]
 * This method can be used on a [ComposeTestRule] instance like such
 * [composeTestRule.clickOnBottomTab(tab)]
 */
fun ComposeTestRule.onNodeByTag(testTag: TestTag): SemanticsNodeInteraction {
    return this.onNodeWithTag(testTag.tag)
}