package com.credits.scapi.implemetation;

import com.credits.scapi.v2.MapChangeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class TokenBalancesTest extends TokenBalancesTestContext {

    TokenBalances<String, Integer> balances;

    @BeforeEach
    void setUp() {
        balances = new TokenBalances<>();
        verifierListener = new MapChangeVerifier();
    }

    @Test
    @SuppressWarnings("unchecked")
    void removeListener() {
        final var changeListener = mock(MapChangeListener.class);

        balances.addListener(changeListener);
        balances.put("a", 1);
        balances.removeListener(changeListener);
        balances.put("b", 1);

        verify(changeListener, times(1)).onChanged(any(MapChangeListener.EntryChange.class));
    }

    @Test
    void put() {
        balances.addListener(verifierListener);

        configureVerifier("A", null, 1);
        balances.put("A", 1);
        assertThat(verifierListener.changesCounter, is(1));
    }

    @Test
    void putIfAbsent() {
        balances.addListener(verifierListener);

        configureVerifier("A", null, 1);
        balances.put("A", 1);

        configureVerifier("B", null, 2);
        balances.put("B", 2);

        configureVerifier("A", 1, 2);
        balances.put("A", 2);

        assertThat(balances.size(), is(2));
        assertThat(balances.get("A"), is(2));
        assertThat(verifierListener.changesCounter, is(3));
    }

    @Test
    void putAll() {
        TokenBalances<String, Integer> anotherBalances = new TokenBalances<>();

        balances.put("A", 1);
        balances.put("D", 1);
        anotherBalances.put("B", 1);
        anotherBalances.put("C", 1);
        anotherBalances.put("D", 2);

        balances.addListener(verifierListener);
        configureVerifier(new Assertions("B", null, 1),
                          new Assertions("C", null, 1),
                          new Assertions("D", 1, 2));

        balances.putAll(anotherBalances);

        assertThat(balances.size(), is(4));
        assertThat(balances.get("A"), is(1));
        assertThat(balances.get("B"), is(1));
        assertThat(balances.get("C"), is(1));
        assertThat(balances.get("D"), is(2));
        assertThat(verifierListener.changesCounter, is(3));
    }

    @Test
    void remove() {
        balances.put("A", 1);
        balances.addListener(verifierListener);
        configureVerifier("A", 1, null);

        final var removed = balances.remove("A");

        assertThat(removed, is(1));
        assertThat(verifierListener.changesCounter, is(1));
    }

    @Test
    void remove1() {
        balances.put("A", 1);
        balances.addListener(verifierListener);
        configureVerifier("A", 1, null);

        var isRemoved = balances.remove("A", 2);
        assertThat(isRemoved, is(false));

        isRemoved = balances.remove("A", 1);
        assertThat(isRemoved, is(true));

        assertThat(verifierListener.changesCounter, is(1));
    }

    @Test
    void replace() {
        balances.put("A", 1);
        balances.addListener(verifierListener);
        configureVerifier("A", 1, 2);

        final var replaced = balances.replace("A", 2);

        assertThat(replaced, is(1));
        assertThat(verifierListener.changesCounter, is(1));
    }

    @Test
    void replace1() {
        balances.put("A", 1);
        balances.addListener(verifierListener);
        configureVerifier("A", 1, 2);

        final var isReplaced = balances.replace("A", 1, 2);
        assertThat(isReplaced, is(true));
        assertThat(verifierListener.changesCounter, is(1));
    }

    @Test
    void replaceAll() {
        balances.put("A", 1);
        balances.put("B", 1);
        balances.addListener(verifierListener);
        configureVerifier(
                new Assertions("A", 1, 2),
                new Assertions("B", 1, 2));

        balances.replaceAll((k, v) -> v = 2);

        assertThat(verifierListener.changesCounter, is(2));
    }

    @Test
    void computeIfAbsent() {
        balances.put("A", 1);
        balances.addListener(verifierListener);
        configureVerifier("B", null, 1);

        balances.computeIfAbsent("B", k -> balances.get("A"));
        balances.computeIfAbsent("A", k -> 3);

        assertThat(verifierListener.changesCounter, is(1));
        assertThat(balances.get("A"), is(1));
        assertThat(balances.get("B"), is(1));
    }

    @Test
    void computeIfPresent() {
        balances.put("A", 1);
        balances.addListener(verifierListener);
        configureVerifier("A", 1, 2);

        balances.computeIfPresent("A", (k, v) -> v = 2);
        balances.computeIfPresent("B", (k, v) -> v = 2);

        assertThat(balances.get("A"), is(2));
        assertThat(balances.containsKey("B"), is(false));
        assertThat(verifierListener.changesCounter, is(1));
    }

    @Test
    void compute() {
        balances.put("A", 1);
        balances.addListener(verifierListener);
        configureVerifier("A", 1, 2);

        balances.compute("A", (k, v) -> v = 2);

        assertThat(balances.get("A"), is(2));
        assertThat(verifierListener.changesCounter, is(1));
    }

    @Test
    void merge() {
        balances.put("A", 1);
        balances.addListener(verifierListener);
        configureVerifier("A", 1, 3);

        balances.merge("A", 2, Integer::sum);

        assertThat(balances.get("A"), is(3));
        assertThat(verifierListener.changesCounter, is(1));
    }

    @Test
    void clear() {
        balances.put("A", 1);
        balances.put("B", 1);
        balances.addListener(verifierListener);
        configureVerifier(new Assertions("A", 1, null),
                          new Assertions("B", 1, null));

        balances.clear();

        assertThat(balances.size(), is(0));
        assertThat(verifierListener.changesCounter, is(2));
    }

    @SuppressWarnings("unchecked")
    @Test
    void clone1() {
        balances.put("A", 1);
        balances.addListener(verifierListener);

        TokenBalances<String, Integer> copiedBalances = (TokenBalances<String, Integer>) balances.clone();

        configureVerifier("B", null, 1);
        copiedBalances.put("B", 1);

        assertThat(balances == copiedBalances, is(false));
        assertThat(copiedBalances.size(), is(2));
        assertThat(copiedBalances.get("A"), is(1));
        assertThat(copiedBalances.get("B"), is(1));
    }
}