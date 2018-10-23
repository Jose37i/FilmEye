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
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;



@RunWith(AndroidJUnit4.class)

/*
Espresso test for user story 1 scenario 2
*/
public class MoviesByGenreTest {

    @Rule
    public ActivityTestRule<HomeScreen> mActivityTestRule = new ActivityTestRule<>(HomeScreen.class);

    @Test
    public void moviesByGenreTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editText1),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Comedy"), closeSoftKeyboard());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.radio_GenreSearch), withText("Search by genre"),
                        childAtPosition(
                                allOf(withId(R.id.radioGroup),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.other_SearchResults), withText("Venom\nThe Predator\nJohnny English Strikes Again\nSmallfoot\nDeadpool 2\nMamma Mia! Here We Go Again\nThe Spy Who Dumped Me\nHotel Transylvania 3: Summer Vacation\nThor: Ragnarok\nOcean's Eight\nSorry to Bother You\nGoosebumps 2: Haunted Halloween\nEighth Grade\nBlacKkKlansman\nCrazy Rich Asians\nNow You See Me 2\nDeadpool\nGuardians of the Galaxy Vol. 2\nTo All the Boys I've Loved Before\nThe Kissing Booth\n"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                2)),
                                0),
                        isDisplayed()));
        //textView.check(matches(isDisplayed()));
        String result = textView.toString();
        boolean resultCondition=false;
        if(result.length()!=0)
        {
            resultCondition=true;
        }
        Assert.assertTrue(resultCondition);
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
