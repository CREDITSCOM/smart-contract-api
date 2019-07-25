package com.credits.scapi.implemetation;

import com.credits.scapi.v2.MapChangeListener;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TokenBalancesTestContext {

    protected MapChangeVerifier verifierListener;

    protected void configureVerifier(String expectedKey, Integer expectedOldValue, Integer expectedNewValue) {
        verifierListener.setAssertionsAndIndexReset(expectedKey, expectedOldValue, expectedNewValue);
    }

    protected void configureVerifier(Assertions... assertionsSequence) {
        verifierListener.setAssertionsSequence(assertionsSequence);
    }


    protected static class Assertions {
        final String expectedKey;
        final Integer expectedOldValue;
        final Integer expectedNewValue;

        public Assertions(String expectedKey, Integer expectedOldValue, Integer expectedNewValue) {
            this.expectedKey = expectedKey;
            this.expectedOldValue = expectedOldValue;
            this.expectedNewValue = expectedNewValue;
        }
    }

    protected static class MapChangeVerifier implements MapChangeListener<String, Integer> {
        private Assertions[] assertionsSequence;
        private int assertionsIndex;
        public int changesCounter;

        public void setAssertionsAndIndexReset(String expectedKey, Integer expectedOldValue, Integer expectedNewValue) {
            assertionsSequence = new Assertions[]{new Assertions(expectedKey, expectedOldValue, expectedNewValue)};
            assertionsIndex = 0;
        }

        public void setAssertionsSequence(Assertions[] assertions) {
            assertionsSequence = assertions;
        }

        @Override
        public void onChanged(EntryChange<? extends String, ? extends Integer> entryChange) {
            assertThat(entryChange.getKey(), is(assertionsSequence[assertionsIndex].expectedKey));
            assertThat(entryChange.getOldValue(), is(assertionsSequence[assertionsIndex].expectedOldValue));
            assertThat(entryChange.getNewValue(), is(assertionsSequence[assertionsIndex].expectedNewValue));
            if (assertionsIndex < assertionsSequence.length) assertionsIndex++;
            changesCounter++;
        }
    }
}
