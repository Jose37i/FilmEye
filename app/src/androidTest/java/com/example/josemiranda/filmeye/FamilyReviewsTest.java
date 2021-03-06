package com.example.josemiranda.filmeye;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.core.StringContains.containsString;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;


@RunWith(AndroidJUnit4.class)
/*
Espresso test for User Story 2, Scenario 2
*/
public class FamilyReviewsTest {

    @Rule
    public ActivityTestRule<HomeScreen> mActivityTestRule = new ActivityTestRule<>(HomeScreen.class);

    @Test
    public void familyReviewsTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editText1),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Frozen"), closeSoftKeyboard());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.Radio_MovieSearch), withText("Search for Movie"),
                        childAtPosition(
                                allOf(withId(R.id.radioGroup),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.Results_Genre), withText("Genres: Animation, Adventure, Family, "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        textView.check(matches(withText(containsString("Family"))));
        //onView(withText(startsWith("Genres:"))).check(matches(isDisplayed()));
        //textView.check(matches(isDisplayed()));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button), withText("Get Reviews"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.other_SearchResults), withText("Reviewer and Review:\njunijubiroke\n\"Frozen,\" the latest Disney musical extravaganza, preaches the importance of embracing your true nature but seems to be at odds with itself.\r\n\r\nThe animated, 3-D adventure wants to enliven and subvert the conventions of typical Disney princess movies while simultaneously remaining true to their aesthetic trappings for maximum merchandising potential. It encourages young women to support and stay loyal to each other—a crucial message when mean girls seem so prevalent—as long as some hunky potential suitors and adorable, wise-cracking creatures also are around to complete them.\r\n\r\nIt all seems so cynical, this attempt to shake things up without shaking them up too much. \"Frozen\" just happens to be reaching theaters as Thanksgiving and the holiday shopping season are arriving. The marketing possibilities are mind-boggling. And in the tradition of the superior \"Beauty and the Beast\" and \"The Little Mermaid,\" surely \"Frozen: The Musical\" will be headed to the Broadway stage soon. The songs – which are lively and amusing if not quite instant hits—are already in place. \r\n\r\nLittle girls will absolutely love it, though. That much is undeniable. And the film from co-directors Chris Buck (\"Surf's Up\") and Jennifer Lee is never less than gorgeous to watch. A majestic mountaintop ice castle is particularly exquisite—glittery and detailed and tactile, especially as rendered in 3-D.\r\n\r\nBut first we must witness the tortured backstory of the film's princesses – not one, but two of them. The script from \"Wreck-It Ralph\" co-writer Lee, inspired by the Hans Christian Andersen story \"The Snow Queen,\" has lots of cheeky, contemporary touches but is firmly and safely rooted in Scandinavian fairy tale traditions.\r\n\r\nWhen they were young girls, sisters Anna and Elsa were joyous playmates and inseparable friends. But Elsa's special power—her ability to turn anything to ice and snow in a flash from her fingertips—comes back to haunt her when she accidentally zaps her sister. (Not unlike the telekinesis in \"Carrie,\" Elsa inadvertently unleashes her power in moments of heightened emotion.) A magical troll king heals Anna and erases the event from her memory, but as for the sisters' relationship, the damage is done.\r\n\r\nElsa's parents lock her away and close down the castle, which devastates the younger Anna. (Of the many tunes from \"Avenue Q\" and \"The Book of Mormon\" songwriter Robert Lopez and his wife, Kristen Anderson-Lopez, the wistful \"Do You Want to Build a Snowman?\" is by far the most poignant.) But once they reach adolescence and it's Elsa's turn to take over the throne at age 18, the two experience an awkward reunion.\r\n\r\nThe perky, quirky Anna (now voiced by a likable Kristen Bell) is a little nervous but overjoyed to see her sister. The reserved and reluctant Elsa (Broadway veteran Idina Menzel) remains distant, and with gloved hands hopes not to freeze anything and reveal her true self on coronation day. But a run-in with an amorous, visiting prince (Santino Fontana) who sets his sights on Anna triggers Elsa's ire, and she inadvertently plunges the sunny, idyllic kingdom into perpetual winter.\r\n\r\nFlustered and fearful, Elsa dashes away in a fit of self-imposed exile – which significantly weakens \"Frozen,\" since she's the film's most complicated and compelling figure. On her way to the highest mountain she can find, Elsa belts out the power ballad \"Let It Go,\" her version of \"I Am Woman.\" This soaring declaration of independence is the reason you want a performer of Menzel's caliber in this role, and it's the film's musical highlight. (Her flashy physical transformation from prim princess to ice queen does make her resemble a real housewife of some sort, however.)\r\n\r\nAfterward, though, the story settles in on Anna's efforts to retrieve her sister and restore order to the kingdom. Along the way she gets help from an underemployed ice salesman named Kristoff (Jonathan Groff) and his trusty reindeer sidekick, Sven. They all meet up with a singing snowman named Olaf (a lovably goofy Josh Gad, star of \"The Book of Mormon\" on Broadway) who dreams of basking in the warmth of the summer sun. This \"Wizard of Oz\"-style quartet makes the obstacle-filled trek to the imposing fortress that awaits. (At least \"Frozen\" has the decency to borrow from excellent source material.)\r\n\r\nWhile the journey may seem overly familiar, the destination has some surprises in store. Some come out of nowhere and don't exactly work. But the biggie—the one that's a real game-changer in terms of the sorts of messages Disney animated classics have sent for decades—is the one that's important not just for the little girls in the audience, but for all viewers. http://newmoviestream.co/watch/2294629/frozen\r\nwish everything about the film met the same clever standard.\nAndres Gomez\nI was excited to watch this movie after all the buzz about it but I've been a bit disappointed.\r\n\r\nYes, the animation is great and it keeps all the typical Disney details and is funny, sometimes. In addition, it features two strong female characters that don't need of a hero to save the story but ...\r\n\r\n... but still they are the super-beautiful and perfect Disney princesses, as if they couldn't be normal girls from normal status, and the role of Queen Elsa is not that important as it would have expected.\r\n\r\nNot a bad movie, but not the best and I think far from the fun I had with Tangled.\n"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                2)),
                                0),
                        isDisplayed()));
        onView(withText(startsWith("Reviewer and Review:"))).check(matches(isDisplayed()));
        //textView2.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
