package navigation;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class DistanceCalculator {

    public static void showMap(String origin, String destination) {
        GeoPosition originPosition = getGeoPosition(origin);
        GeoPosition destinationPosition = getGeoPosition(destination);

        // Create a JFrame to display the map
        JFrame mapFrame = new JFrame("Map Viewer");
        mapFrame.setSize(800, 600);
        mapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a JXMapViewer
        JXMapViewer mapViewer = new JXMapViewer();

        // Set the tile factory to use the OpenStreetMap tiles
        mapViewer.setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));

        // Set the center position and zoom level
        GeoPosition center = new GeoPosition((originPosition.getLatitude() + destinationPosition.getLatitude()) / 2,
                (originPosition.getLongitude() + destinationPosition.getLongitude()) / 2);
        mapViewer.setZoom(3);
        mapViewer.setAddressLocation(center);

        // Create waypoints
        Set<Waypoint> waypoints = new HashSet<>();
        waypoints.add(new DefaultWaypoint(originPosition));
        waypoints.add(new DefaultWaypoint(destinationPosition));

        // Create a WaypointPainter to display waypoints on the map
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);

        // Add the WaypointPainter to the mapViewer
        mapViewer.setOverlayPainter(waypointPainter);

        // Add the mapViewer to the frame
        mapFrame.getContentPane().add(mapViewer, BorderLayout.CENTER);

        // Display the frame
        mapFrame.setVisible(true);
    }

    private static GeoPosition getGeoPosition(String location) {
        int index = getIndex(location);

        if (index == -1) {
            return null;
        }

        return COORDINATES[index];
    }

    private static int getIndex(String location) {
        String[] locations = { "MainGate", "Exit", "Library", "Cafeteria", "Gym" };

        for (int i = 0; i < locations.length; i++) {
            if (locations[i].equals(location)) {
                return i;
            }
        }
        return -1;
    }

    private static final GeoPosition[] COORDINATES = {
            new GeoPosition(37.4254342, -122.1911694), // MainGate
            new GeoPosition(37.4189235, -122.1763556), // Exit
            new GeoPosition(37.4240128, -122.2052675), // Library
            new GeoPosition(37.4275486, -122.1749845), // Cafeteria
            new GeoPosition(37.4240774, -122.2052676), // Gym
    };

    public static void main(String[] args) {
        showMap("MainGate", "Exit");
    }
}
