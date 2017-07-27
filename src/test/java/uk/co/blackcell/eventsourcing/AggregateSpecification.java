package uk.co.blackcell.eventsourcing;

import org.junit.After;
import org.junit.Before;
import uk.co.blackcell.eventsourcing.api.Aggregate;
import uk.co.blackcell.eventsourcing.api.Command;
import uk.co.blackcell.eventsourcing.api.Event;
import uk.co.blackcell.eventsourcing.api.ReflectionUtil;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public abstract class AggregateSpecification<T extends Aggregate> {

    protected T sut;
    public abstract T given();
    public abstract List<Command> when();
    public Exception caught;

    protected List<Event> withExistingEvents() {
        return new ArrayList<>();
    }

    @Before
    public void Setup() {
        try {

            PrintStream out = System.out;

            out.println("--------------------------------------------------------------------------------------");

            out.println("\nScenario: " + this.getClass().getSimpleName().replace("_Test", "").replaceAll("_", " ").toLowerCase());

            sut = given();

            out.println("\nGiven a");
            out.println("\t" + StringUtils.toString(sut));

            if (withExistingEvents().size() > 0) {
                out.println("\nwith the following events");
            }

            for (Event event : withExistingEvents()) {
                out.println("\t" + StringUtils.toString(event));

                ReflectionUtil.invokeHandleMethod(sut, event);
            }

            out.println("\nWhen the following commands were executed");

            for (Command command : when()) {
                out.println("\t" + StringUtils.toString(command));

                ReflectionUtil.invokeHandleMethod(sut, command);
            }

            out.println("\nThen the following events were raised");

            for (Event event : sut.getUncommittedEvents()) {
                out.println("\t" + StringUtils.toString(event));
            }

            out.println("--------------------------------------------------------------------------------------");
        }
        catch (Exception e) {
            caught = e;
        }
    }

    @After
    public void after() {
        sut.getUncommittedEvents().clear();
    }

    protected void assertEventStreamContains(List<Event> events, Event expectedEvent) {
        String expected = StringUtils.toString(expectedEvent);
        for (Event event : events) {
            if (StringUtils.toString(event).equals(expected)) {
                return;
            }
        }
        fail(" -- Expected event did not occur: " + expected);
    }

    protected void assertEventStreamContains(Event expectedEvent) {
        String expected = StringUtils.toString(expectedEvent);
        for (Event event : this.sut.getUncommittedEvents()) {
            if (StringUtils.toString(event).equals(expected)) {
                return;
            }
        }
        fail(" -- Expected event did not occur: " + expected);
    }
}
