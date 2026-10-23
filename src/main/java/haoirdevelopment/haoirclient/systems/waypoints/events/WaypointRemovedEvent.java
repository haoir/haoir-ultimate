/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.systems.waypoints.events;

import haoirdevelopment.haoirclient.systems.waypoints.Waypoint;

public record WaypointRemovedEvent(Waypoint waypoint) {
}
