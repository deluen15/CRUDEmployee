package com.example.employer.markers;

import net.logstash.logback.marker.*;
import org.slf4j.Marker;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public class Markers {

    private Markers() {
    }

    /*
     * @see MapEntriesAppendingMarker
     */
    public static LogstashMarker appendEntries(Map<?, ?> map) {
        return new MapEntriesAppendingMarker(map);
    }

    /*
     * @see ObjectFieldsAppendingMarker
     */
    public static LogstashMarker appendFields(Object object) {
        return new ObjectFieldsAppendingMarker(object);
    }

    /*
     * @see ObjectAppendingMarker
     */
    public static LogstashMarker append(String fieldName, Object object) {
        return new ObjectAppendingMarker(fieldName, object);
    }

    /*
     * @see ObjectAppendingMarker
     */
    public static LogstashMarker appendArray(String fieldName, Object... objects) {
        return new ObjectAppendingMarker(fieldName, objects);
    }

    /*
     * @see RawJsonAppendingMarker
     */
    public static LogstashMarker appendRaw(String fieldName, String rawJsonValue) {
        return new RawJsonAppendingMarker(fieldName, rawJsonValue);
    }

    /**
     * Aggregates the given markers into a single marker.
     *
     * @param markers the markers to aggregate
     * @return the aggregated marker.
     */
    public static LogstashMarker aggregate(Marker... markers) {
        LogstashMarker m = empty();
        if (markers != null) {
            for (Marker marker : markers) {
                m.add(marker);
            }
        }
        return m;
    }

    /**
     * Aggregates the given markers into a single marker.
     *
     * @param markers the markers to aggregate
     * @return the aggregated marker.
     */
    public static LogstashMarker aggregate(Collection<? extends Marker> markers) {
        LogstashMarker m = empty();
        if (markers != null) {
            for (Marker marker : markers) {
                m.add(marker);
            }
        }
        return m;
    }

    /*
     * @see DeferredLogstashMarker
     */
    public static LogstashMarker defer(Supplier<? extends LogstashMarker> logstashMarkerSupplier) {
        return new DeferredLogstashMarker(logstashMarkerSupplier);
    }

    /*
     * @see EmptyLogstashMarker
     */
    public static LogstashMarker empty() {
        return new EmptyLogstashMarker();
    }

}
