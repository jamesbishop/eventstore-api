package uk.co.blackcell.eventsourcing.api;

import java.util.UUID;

public interface Command {
    UUID aggregateId();
}
