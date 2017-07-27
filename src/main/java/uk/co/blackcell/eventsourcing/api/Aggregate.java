package uk.co.blackcell.eventsourcing.api;

import java.util.List;
import java.util.UUID;

public interface Aggregate {
    List<Event> getUncommittedEvents();
    UUID aggregateId();
}
