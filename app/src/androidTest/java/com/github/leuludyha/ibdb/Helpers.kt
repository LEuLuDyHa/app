import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
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
 * Returns a formatted string in accordance to the one used as a test tag on the BottomToolBar class.
 */
internal fun getBottomToolbarTestTagFrom(descriptor: TabDescriptor): String {
    return "bottomtoolbar::tab_item::${descriptor.displayName}"
}

/**
 * This method will perform a click on the BottomTab's corresponding tab, avoiding the need to take care of the formatting
 * used by the BottomTab class.
 */
internal fun ComposeTestRule.clickOnBottomTab(descriptor: TabDescriptor) {
    this.onNodeWithTag(getBottomToolbarTestTagFrom(descriptor)).performClick()
}
