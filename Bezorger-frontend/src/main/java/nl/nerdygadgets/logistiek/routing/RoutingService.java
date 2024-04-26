package nl.nerdygadgets.logistiek.routing;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RoutingService {

    private static RoutingService instance;
    private final GraphHopper hopper;

    public static RoutingService getInstance() {
        if (instance == null) {
            instance = new RoutingService();
        }
        return instance;
    }

    private RoutingService() { // TODO: Verander naar flevoland-latest als je sneller wilt opstarten en je geen adressen buiten flevoland hebt.
        hopper = createGraphHopperInstance("src/main/resources/osn/netherlands-latest.osm.pbf");
    }

    private GraphHopper createGraphHopperInstance(String ghLoc) {
        GraphHopper graphHopper = new GraphHopper();
        graphHopper.setOSMFile(ghLoc);
        // specify where to store graphhopper files
        graphHopper.setGraphHopperLocation("target/routing-graph-cache");

        // add all encoded values that are used in the custom model, these are also available as path details or for client-side custom models
        graphHopper.setEncodedValuesString("car_access, car_average_speed");
        // see docs/core/profiles.md to learn more about profiles
        graphHopper.setProfiles(new Profile("car").setCustomModel(GHUtility.loadCustomModelFromJar("car.json")));

        // this enables speed mode for the profile we called car
        graphHopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));

        // now this can take minutes if it imports or a few seconds for loading of course this is dependent on the area you import
        graphHopper.importOrLoad();
        return graphHopper;
    }

    public List<RoutingData> routing(Double fromLat, double fromLon, double toLat, double toLon) {
        // simple configuration of the request object
        GHRequest req = new GHRequest(fromLat, fromLon, toLat, toLon).
                // note that we have to specify which profile we are using even when there is only one like here
                        setProfile("car").
                // define the language for the turn instructions
                        setLocale(Locale.US);
        GHResponse rsp = hopper.route(req);

        // handle errors
        if (rsp.hasErrors())
            System.out.println(rsp.getErrors().toString());

        // use the best path, see the GHResponse class for more possibilities.
        ResponsePath path = rsp.getBest();

        // points, distance in meters and time in millis of the full path
        PointList pointList = path.getPoints();
        double distance = path.getDistance();
        long timeInMs = path.getTime();

        Translation tr = hopper.getTranslationMap().getWithFallBack(Locale.UK);
        InstructionList il = path.getInstructions();
        // iterate over all turn instructions
        List<RoutingData> list = new ArrayList<>();
        for (Instruction instruction : il) {
            list.add(new RoutingData(instruction.getDistance(), instruction.getTurnDescription(tr), instruction.getPoints()));
        }
        assert il.size() == 6;
        assert Helper.round(path.getDistance(), -2) == 600;

        return list;
    }

}
