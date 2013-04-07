package org.diningdevelopers.utils;

import org.diningdevelopers.model.TimelineLocationModel;
import org.diningdevelopers.model.TimelineUserModel;
import org.diningdevelopers.model.TimelineVoteModel;
import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TimelineBuilderTest {

    TimelineBuilder builder;

    Set<TimelineUserModel> users;
    TimelineUserModel userA, userB;
    Set<TimelineVoteModel> userAVotes, userBVotes;

    Set<TimelineLocationModel> locations;
    TimelineLocationModel locationX, locationY;

    Date[] timesteps;

    CartesianChartModel model;

    final static long USER_A = 1;
    final static long USER_B = 2;
    final static long LOCATION_X = 3;
    final static String LOCATION_X_NAME = "X";
    final static long LOCATION_Y = 4;
    final static String LOCATION_Y_NAME = "Y";
    final static int TIMESTEP_ONE = 0;
    final static int TIMESTEP_TWO = 1;
    final static int TIMESTEP_THREE = 2;

    @Before
    public void setup() {
        userA = new TimelineUserModel();
        userA.setId(USER_A);
        userB = new TimelineUserModel();
        userB.setId(USER_B);
        users = new LinkedHashSet<>();
        userAVotes = new LinkedHashSet<>();
        userBVotes = new LinkedHashSet<>();
        locations = new LinkedHashSet<>();
        locationX = new TimelineLocationModel();
        locationX.setId(LOCATION_X);
        locationX.setName(LOCATION_X_NAME);
        locationY = new TimelineLocationModel();
        locationY.setId(LOCATION_Y);
        locationY.setName(LOCATION_Y_NAME);
        timesteps = new Date[3];
        GregorianCalendar calendar = new GregorianCalendar(2013, 01, 01);
        timesteps[TIMESTEP_ONE] = calendar.getTime();
        calendar.add(Calendar.MINUTE, 1);
        timesteps[TIMESTEP_TWO] = calendar.getTime();
        calendar.add(Calendar.MINUTE, 1);
        timesteps[TIMESTEP_THREE] = calendar.getTime();
    }


    /**
     * User votes (locationX/locationY):
     *   A: --->
     */
    @Test
    public void singleUserWithoutVotes() {
        users.add(userA);
        buildChartModel();
        assertTrue(model.getSeries().isEmpty());
    }

    /**
     * User votes (locationX/locationY):
     *   A: --->
     *   B: --->
     */
    @Test
    public void twoUsersWithoutVotes() {
        users.add(userA);
        users.add(userB);
        buildChartModel();
        assertTrue(model.getSeries().isEmpty());
    }

    /**
     * User votes (locationX/locationY):
     * A: ---(100/000)--->
     *
     * =>
     *
     * Location timelines:
     *    0
     * X: 100
     */
    @Test
    public void singleVote() {
        users.add(userA);
        locations.add(locationX);
        userAVotes.add(vote(locationX, 100, 0));

        buildChartModel();

        assertFalse(model.getSeries().isEmpty());
        assertThat(model.getSeries().size(), is(1));

        ChartSeries series = model.getSeries().iterator().next();
        assertThat(series.getLabel(), is(LOCATION_X_NAME));
        assertThat(series.getData(), is(
                expect(0,100)
        ));
    }

    /**
     * User votes (locationX/locationY):
     * A: ---(100/000)--------------->
     * B: ---------------(025/075)--->
     *
     * =>
     *
     * Location timelines:
     *    -1   0
     * X: 100  125
     * Y: ---  075
     *
     */
    @Test
    public void twoVotes() {
        users.add(userA);
        users.add(userB);
        locations.add(locationX);
        locations.add(locationY);

        userAVotes.add(vote(locationX, 100, TIMESTEP_ONE));
        userBVotes.add(vote(locationX,  25, TIMESTEP_TWO));
        userBVotes.add(vote(locationY,  75, TIMESTEP_TWO));

        buildChartModel();

        assertFalse(model.getSeries().isEmpty());
        assertThat(model.getSeries().size(), is(2));

        Iterator<ChartSeries> seriesIter = model.getSeries().iterator();
        ChartSeries seriesX = seriesIter.next();
        assertThat(seriesX.getLabel(), is(LOCATION_X_NAME));
        assertThat(seriesX.getData(), is(expect(
            -1, 100,
             0, 125
        )));
        ChartSeries seriesY = seriesIter.next();
        assertThat(seriesY.getLabel(), is(LOCATION_Y_NAME));
        assertThat(seriesY.getData(), is(expect(
            0, 75
        )));
    }

    /**
     * User votes (locationX/locationY):
     * A: ---(080/020)---------------(050/050)--->
     * B: ---------------(010/090)--------------->
     *
     * =>
     *
     * Location timelines:
     *    -2   -1   0
     * X: 080  090  060
     * Y: 020  110  140
     *
     */
    @Test
    public void threeVotes() {
        users.add(userA);
        users.add(userB);
        locations.add(locationX);
        locations.add(locationY);

        userAVotes.add(vote(locationX,  80, TIMESTEP_ONE));
        userAVotes.add(vote(locationY,  20, TIMESTEP_ONE));
        userAVotes.add(vote(locationX,  50, TIMESTEP_THREE));
        userAVotes.add(vote(locationY,  50, TIMESTEP_THREE));

        userBVotes.add(vote(locationX,  10, TIMESTEP_TWO));
        userBVotes.add(vote(locationY,  90, TIMESTEP_TWO));

        buildChartModel();

        assertFalse(model.getSeries().isEmpty());
        assertThat(model.getSeries().size(), is(2));

        Iterator<ChartSeries> seriesIter = model.getSeries().iterator();
        ChartSeries seriesX = seriesIter.next();
        assertThat(seriesX.getLabel(), is(LOCATION_X_NAME));
        assertThat(seriesX.getData(), is(expect(
                -2, 80,
                -1, 90,
                 0, 60
        )));
        ChartSeries seriesY = seriesIter.next();
        assertThat(seriesY.getLabel(), is(LOCATION_Y_NAME));
        assertThat(seriesY.getData(), is(expect(
                -2,  20,
                -1, 110,
                 0, 140
        )));
    }

    /**
     * Votes:
     * A: ---(000/100)--->
     * B: ---(100/000)--->
     *
     * =>
     *
     * Timeline series:
     *    0   1
     * A: 100 100
     * B: 000 100
     *
     * To keep TimelineBuilder logic simple, this case is not
     * treated precisely correct. Instead, timestep one is expanded to two timeline entries.
     * Note: In reality two simultaneous votes will hardly occur.
     */
    @Test
    public void twoSimultaneousVotes() {
        users.add(userA);
        users.add(userB);
        locations.add(locationX);
        locations.add(locationY);

        userAVotes.add(vote(locationY, 100, TIMESTEP_ONE));
        userBVotes.add(vote(locationX, 100, TIMESTEP_ONE));

        buildChartModel();

        assertFalse(model.getSeries().isEmpty());
        assertThat(model.getSeries().size(), is(2));

        Iterator<ChartSeries> seriesIter = model.getSeries().iterator();
        ChartSeries seriesX = seriesIter.next();
        assertThat(seriesX.getLabel(), is(LOCATION_X_NAME));
        assertThat(seriesX.getData(), is(expect(
               -1, 100,
                0, 100
        )));
        ChartSeries seriesY = seriesIter.next();
        assertThat(seriesY.getLabel(), is(LOCATION_Y_NAME));
        assertThat(seriesY.getData(), is(expect(
                0, 100
        )));
    }

    private TimelineVoteModel vote(TimelineLocationModel location, int points, int timestep) {
        TimelineVoteModel timelineVoteModel = new TimelineVoteModel();
        timelineVoteModel.setLocation(location);
        timelineVoteModel.setPoints(points);
        timelineVoteModel.setDate(timesteps[timestep]);
        return timelineVoteModel;
    }

    private static Map<Object,Number> expect(Integer... keysAndValues) {
        assertTrue("Even number of keys and values required", keysAndValues.length % 2 == 0);
        TreeMap<Object,Number> theExpected = new TreeMap<>();
        for( int i = 0; i < keysAndValues.length; i += 2) {
            int key = keysAndValues[i];
            int value = keysAndValues[i+1];
            theExpected.put(key,value);
        }
        return theExpected;
    }

    private void buildChartModel() {
        builder = new TimelineBuilder(locations, users);
        builder.populateUserVotes(userA, userAVotes);
        builder.populateUserVotes(userB, userBVotes);
        model = builder.build();
    }

}
