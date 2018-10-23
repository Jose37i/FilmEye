package com.example.josemiranda.filmeye;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
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
import static org.hamcrest.core.StringStartsWith.startsWith;

// Espresso test for User Story 1, Scenario 3
@LargeTest
@RunWith(AndroidJUnit4.class)
public class searchForActorTest {

    @Rule
    public ActivityTestRule<HomeScreen> mActivityTestRule = new ActivityTestRule<>(HomeScreen.class);

    @Test
    public void searchForActorTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editText1),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("will smith"), closeSoftKeyboard());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.radio_PersonSearch), withText("Search for a Person"),
                        childAtPosition(
                                allOf(withId(R.id.radioGroup),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.other_SearchResults), withText("Some Filmography: \nSuicide Squad\nI Am Legend\nMen in Black\nPaddington\nIn the Loop\nHampstead\nNational Museum of African American History and Culture Grand Opening Ceremony\nMichael Palin in North Korea\nThe Boy with the Topknot\nConan the Barbarian\nMaverick\nThe Outsiders\nI Am Legend\nMadagascar: Escape 2 Africa\nThe Lorax\nPapillon\n\"US\"]}\nSession 9\nThe Great Adventures of Captain Kidd\nMuddy Waters Rhythm & Blues Band Festival Concert Dortmund\nThe Big Sick\nSkeleton Key 2: 667 Neighbor of the Beast\nDark Side of the Light\nShadowhunters\nThe Legend of Boggy Creek\nThe D.I.\nTINY: A Story About Living Small\nFour in the Afternoon\nEventual Salvation\nKiss Me Again\nSay Ame\n"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                2)),
                                0),
                        isDisplayed()));
        //textView.check(matches(isDisplayed()));
        onView(withText(startsWith("Some Filmography: "))).check(matches(isDisplayed()));
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
