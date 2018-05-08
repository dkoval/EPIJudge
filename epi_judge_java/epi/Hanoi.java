package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

import java.util.*;

public class Hanoi {

    private static final int NUM_PEGS = 3;

    public static List<List<Integer>> computeTowerHanoi(int numRings) {
        Peg src = new Peg(0), dst = new Peg(1), tmp = new Peg(2);
        for (int i = numRings; i >= 1; i--) {
            src.placeRing(i);
        }
        return computeTowerHanoiSteps(numRings, src, dst, tmp, new LinkedList<>());
    }

    private static List<List<Integer>> computeTowerHanoiSteps(int numRings, Peg src, Peg dst, Peg tmp, List<List<Integer>> steps) {
        if (numRings > 0) {
            computeTowerHanoiSteps(numRings - 1, src, tmp, dst, steps);
            src.moveTopRing(dst);
            trackStep(src, dst, steps);
            computeTowerHanoiSteps(numRings - 1, tmp, dst, src, steps);
        }
        return steps;
    }

    private static void trackStep(Peg src, Peg dst, List<List<Integer>> moves) {
        moves.add(Arrays.asList(src.index, dst.index));
    }

    private static class Peg {
        final Deque<Integer> rings = new LinkedList<>();
        final int index;

        private Peg(int index) {
            this.index = index;
        }

        void placeRing(int ring) {
            Integer topRing = rings.peek();
            if (topRing != null && topRing < ring) {
                throw new IllegalArgumentException("You must never place a larger ring above a smaller one");
            }
            rings.push(ring);
        }

        int takeRing() {
            return rings.pop();
        }

        void moveTopRing(Peg dst) {
            int topDisc = takeRing();
            dst.placeRing(topDisc);
        }
    }

    @EpiTest(testfile = "hanoi.tsv")
    public static void computeTowerHanoiWrapper(TestTimer timer, int numRings)
            throws TestFailureException {
        List<Deque<Integer>> pegs = new ArrayList<>();
        for (int i = 0; i < NUM_PEGS; i++) {
            pegs.add(new LinkedList<>());
        }
        for (int i = numRings; i >= 1; --i) {
            pegs.get(0).addFirst(i);
        }

        timer.start();
        List<List<Integer>> result = computeTowerHanoi(numRings);
        timer.stop();

        for (List<Integer> operation : result) {
            int fromPeg = operation.get(0);
            int toPeg = operation.get(1);
            if (!pegs.get(toPeg).isEmpty() &&
                    pegs.get(fromPeg).getFirst() >= pegs.get(toPeg).getFirst()) {
                throw new TestFailureException(
                        "Illegal move from " +
                                String.valueOf(pegs.get(fromPeg).getFirst()) + " to " +
                                String.valueOf(pegs.get(toPeg).getFirst()));
            }
            pegs.get(toPeg).addFirst(pegs.get(fromPeg).removeFirst());
        }

        List<Deque<Integer>> expectedPegs1 = new ArrayList<>();
        for (int i = 0; i < NUM_PEGS; i++) {
            expectedPegs1.add(new LinkedList<Integer>());
        }
        for (int i = numRings; i >= 1; --i) {
            expectedPegs1.get(1).addFirst(i);
        }

        List<Deque<Integer>> expectedPegs2 = new ArrayList<>();
        for (int i = 0; i < NUM_PEGS; i++) {
            expectedPegs2.add(new LinkedList<Integer>());
        }
        for (int i = numRings; i >= 1; --i) {
            expectedPegs2.get(2).addFirst(i);
        }
        if (!pegs.equals(expectedPegs1) && !pegs.equals(expectedPegs2)) {
            throw new TestFailureException(
                    "Pegs doesn't place in the right configuration");
        }
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {}.getClass().getEnclosingClass(), args);
    }
}
