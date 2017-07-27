package uk.co.blackcell.eventsourcing.api;

import java.util.UUID;

public interface Event {
    UUID aggregateId();
}