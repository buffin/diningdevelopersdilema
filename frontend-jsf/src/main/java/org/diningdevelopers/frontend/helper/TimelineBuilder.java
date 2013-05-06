package org.diningdevelopers.frontend.helper;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import org.diningdevelopers.frontend.model.TimelineLocationModel;
import org.diningdevelopers.frontend.model.TimelineUserModel;
import org.diningdevelopers.frontend.model.TimelineVoteModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimelineBuilder {

    private Logger logger = LoggerFactory.getLogger(TimelineBuilder.class);

    private CartesianChartModel model;
    private Map<TimelineLocationModel,ChartSeries> chartSeriesForLocation;
    private Map<TimelineUserModel, UserSubmissionHistory> submissionHistoryForUser; //conceptually a map of stacks with most recent vote submission on top of each stack

    public TimelineBuilder(Collection<TimelineLocationModel> locations, Collection<TimelineUserModel> users) {
        model = new CartesianChartModel();
        initializeChartSeriesForLocation(locations);
        initializeSubmissionHistoryForUser(users);
    }

    private void initializeChartSeriesForLocation(Collection<TimelineLocationModel> locations) {
        chartSeriesForLocation = new HashMap<>();
        for ( TimelineLocationModel location : locations ) {
            ChartSeries series = new ChartSeries();
            series.setLabel(location.getName());
            chartSeriesForLocation.put(location, series);
            model.addSeries(series);
        }
    }

    private void initializeSubmissionHistoryForUser(Collection<TimelineUserModel> users) {
        submissionHistoryForUser = new HashMap<>();
        for ( TimelineUserModel user : users ) {
            submissionHistoryForUser.put(user, new UserSubmissionHistory());
        }
    }

    /**
     * @param user
     * @param votes All votes of the given user (towards the same event)
     */
    public void populateUserVotes(TimelineUserModel user, Collection<TimelineVoteModel> votes) {
        //group votes by date to 'submissions' and map each submission by date
        Map<Date, Submission> submissionForDate = new TreeMap<>();
        for ( TimelineVoteModel vote : votes ) {
            Date time = vote.getDate();
            if ( submissionForDate.containsKey(time) == false ) {
                submissionForDate.put(vote.getDate(), new Submission(time));
            }
            submissionForDate.get(time).add(vote);
        }
        //populate ordered user submission history
        UserSubmissionHistory history = new UserSubmissionHistory();
        for ( Submission submission : submissionForDate.values() ) {
            history.addSubmission(submission);
        }
        submissionHistoryForUser.put(user, history);
    }

    public CartesianChartModel build() {
        recursivelyPopulateChartModel(0);
        return model;
    }

    private void recursivelyPopulateChartModel(int depth) {
        logger.trace("recursion depth {}", depth);

        Counters<TimelineLocationModel> counters = new Counters();
        for ( UserSubmissionHistory history : submissionHistoryForUser.values()) {
            if (history.isEmpty() == false) {
                for ( TimelineVoteModel vote : history.getMostRecentSubmission().getVotes() ) {
                    logger.debug("{} -> {}", vote.getLocation().getName(), vote.getPoints());
                    counters.increment( vote.getLocation(),  vote.getPoints() );
                }
            }
        }

        for ( TimelineLocationModel location : counters.getKeys()) {
            ChartSeries chartSeries = chartSeriesForLocation.get(location);
            chartSeries.set(depth, counters.get(location));
        }

        UserSubmissionHistory history = determineHistoryWithMostRecentSubmission();
        if ( history != null && history.isEmpty() == false ) {
            history.removeMostRecentSubmission();
            recursivelyPopulateChartModel(--depth);
        } else {
            return; //terminate recursion
        }
    }

    private UserSubmissionHistory determineHistoryWithMostRecentSubmission() {
        if (submissionHistoryForUser.isEmpty()) {
            return null;
        }
        Set<UserSubmissionHistory> historiesReverselyOrderedByMostRecentSubmission = new TreeSet<>(Collections.reverseOrder());
        historiesReverselyOrderedByMostRecentSubmission.addAll(submissionHistoryForUser.values());
        UserSubmissionHistory history = historiesReverselyOrderedByMostRecentSubmission.iterator().next();
        return history;
    }

    class UserSubmissionHistory implements Comparable<UserSubmissionHistory> {

        private Stack<Submission> history;

        public UserSubmissionHistory() {
            history = new Stack();
        }

        boolean isEmpty() {
            return history.isEmpty();
        }

        /**
         * must be called in chronological order
         */
        void addSubmission(Submission submission) {
            history.push(submission);
        }

        Submission getMostRecentSubmission() {
            return history.peek();
        }

        Submission removeMostRecentSubmission() {
            return history.pop();
        }

        private Date getMostRecentSubmissionDate() {
            if ( history.isEmpty() ) {
                return new Date(Long.MIN_VALUE);
            } else {
                return history.peek().getTime();
            }
        }

        @Override
        public int compareTo(UserSubmissionHistory other) {
            return this.getMostRecentSubmissionDate().compareTo(other.getMostRecentSubmissionDate());
        }

    }

    class Submission {

        private final Date time;
        private List<TimelineVoteModel> votes;

        Submission(Date time) {
            this.time = time;
            votes = new LinkedList<>();
        }

        Date getTime() {
            return time;
        }

        void add(TimelineVoteModel vote) {
            votes.add(vote);
        }

        List<TimelineVoteModel> getVotes() {
            return votes;
        }
    }


    class Counters<T> {
        Map<T,MutableInt> counters = new HashMap<>();

        public void increment(T key, int inc) {
            MutableInt count = counters.get(key);
            if (count == null) {
                counters.put(key, new MutableInt(inc));
            }
            else {
                count.increment(inc);
            }

        }

        public int get(T key) {
            MutableInt count = counters.get(key);
            if ( count == null ) {
                return 0;
            } else {
                return count.get();
            }
        }

        public Set<T> getKeys() {
            return counters.keySet();
        }
    }

    class MutableInt {
        int value;

        public MutableInt(int value) {
            this.value = value;
        }

        public void increment (int inc) {
            this.value += inc;
        }

        public int get() {
            return value;
        }
    }

}
