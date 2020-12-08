package edu.csc131.FlightPlan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <i>Here be dragons.</i> This class contains the controler for flightPlan. It
 * contains the post and get methods. We are using Spring. It is a framework
 * that heavely uses anotations to do a lot of fancy things, really rather
 * easiely.
 * 
 * @author ingrumm
 */
@Controller // This means that this class is a Controller
@RequestMapping(path = "/flightPlan") // This means URL's start with /flightPlan (after Application path)
public class surverController {
  @Autowired // This means to get the bean called DataRepo
             // Which is auto-generated by Spring, we will use it to handle the data
  private DataRepo dataRepo;// This is the repo object. You use it to save or load objects.
  private UserData userData = new UserData();// This is the User Data object. We store this object in the database.
                                             // Neat!

  /**
   * All params are from request attrbutes.
   * 
   * @param address
   * @param mpg
   * @param email
   * @return Forward to Get mapped function.
   */
  @PostMapping(path = "/takeFlight") // Map ONLY POST Requests
  public String saveData(@RequestParam String address, @RequestParam("rank") String preferredRank, @RequestParam("email") String email) {
    // @RequestParam means it is a parameter from the GET or POST request. Here we
    // are using POST.

    // Creates a TimeTo object. This class will give us the time to a destnation.
    // We can also add functionality to get distance as well.

    TimeTo time;
    try {
      time = new TimeTo(address);
    } catch (NotValidAddressException e) {

      e.printStackTrace();
      return "/invalidAddress";
    }
    // Get times
    double travelTimeByCar = Double.parseDouble(time.getTimeByCar());
    double travelTimeByWalk = Double.parseDouble(time.getTimeByWalking());
    double travelTimeByTransit = Double.parseDouble(time.getTimeByTransportation());
    double travelTimeByBike = Double.parseDouble(time.getTimeByBike());

    //Get Distance
    double travelDistanceByCar = Double.parseDouble(time.getDistanceByCar());
    double travelDistanceByWalking = Double.parseDouble(time.getDistanceByWalking());
    double travelDistanceByTransit = Double.parseDouble(time.getDistanceByTransportation());
    double travelDistanceByBike = Double.parseDouble(time.getDistanceByBike());

    //Hard-coded input

         String carType = "Sedan";
   
      //Creates Ranking object
         Ranking r = new Ranking(travelDistanceByWalking, travelTimeByWalk, travelDistanceByBike, travelDistanceByBike,
                                 travelDistanceByTransit, travelTimeByTransit, travelDistanceByCar, travelTimeByCar, carType);
         
         
      /* NOTE:
      Array itself is not sorted so Alec can tag them and rank them as he said in the meeting
      Below: Sample Ranking Output based on arbitary scores Justin created in TransportMode.java:
         
      double[] ranked_by_whatever = r.rank_all();
         for (double each : ranked_by_whatever)
            System.out.println(each);
      */ 
    userData.setUserEmail(email);
    userData.setAddress(address);
    userData.setPreferredRank(preferredRank);

    userData.settravelTimeByCar(travelTimeByCar);
    userData.setTravelTimeByWalking(travelTimeByWalk);
    userData.setTravelTimeByTransit(travelTimeByTransit);
    userData.setTravelTimeByBike(travelTimeByBike);

    userData.setTravelDistanceByCar(travelDistanceByCar);
    userData.setTravelDistanceByWalking(travelDistanceByWalking);
    userData.setTravelDistanceByTransit(travelDistanceByTransit);
    userData.setTravelDistanceByBike(travelDistanceByBike);

    userData.setRankCarbon(r.rank_carbon());
    userData.setRankCost(r.rank_cost());
    userData.setRankTime(r.rank_time());

    // adds the row to the database.
    dataRepo.save(userData);

    // redirects to class member mapped to /results
    return "redirect:/flightPlan/results";
  }

  /**
   * 
   * @param model
   * @return Returns the View called takeFlight.html
   * 
   *         We set can add attrbutes to the model here. Thymeleaf then parses our
   *         html file, and replaces the corrasponding variables. We can also do
   *         this with a jsp file instead og an htm file. That gives us more toys,
   *         but I had trouble with Spring serving the file correctly.
   */
  @GetMapping(path = "/results")
  public String getResults(Model model) {
    // ModelAndView mv = new ModelAndView();
    model.addAttribute("address", userData.getAddress());

    model.addAttribute("travelTimeByCar", userData.gettravelTimeByCar());
    model.addAttribute("travelTimeByWalking", userData.getTravelTimeByWalking());
    model.addAttribute("travelTimeByTransit", userData.getTravelTimeByTransit());
    model.addAttribute("travelTimeBybike", userData.getTravelTimeByBike());

    model.addAttribute("travelDistanceByCar", userData.getTravelDistanceByCar());
    model.addAttribute("travelDistanceByWalking", userData.getTravelDistanceByWalking());
    model.addAttribute("travelDistanceByTransit", userData.getTravelDistanceByTransit());
    model.addAttribute("travelDistanceBybike", userData.getTravelDistanceByBike());

    model.addAttribute("rankTime", userData.getRankTime());
    model.addAttribute("rankCarbon", userData.getRankCarbon());
    model.addAttribute("rankCost", userData.getRankCost());

    FormatOutput format = new FormatOutput();
    model.addAttribute("travelTimeByCarString", format.getTimeFormatted(userData.gettravelTimeByCar()));
    model.addAttribute("travelTimeByTransitString", format.getTimeFormatted(userData.getTravelTimeByTransit()));
    model.addAttribute("travelTimeByBikeString", format.getTimeFormatted(userData.getTravelTimeByBike()));
    model.addAttribute("travelTimeByWalkingString", format.getTimeFormatted(userData.getTravelTimeByWalking()));

    model.addAttribute("travelDistanceByCarFormatted", format.getMetersToMiles(userData.getTravelDistanceByCar()));
    model.addAttribute("travelDistanceByTransitFormatted", format.getMetersToMiles(userData.getTravelDistanceByTransit()));
    model.addAttribute("travelDistanceByBikeFormatted", format.getMetersToMiles(userData.getTravelDistanceByBike()));
    model.addAttribute("travelDistanceByWalkingFormatted", format.getMetersToMiles(userData.getTravelDistanceByWalking()));

    model.addAttribute("preferredRank", userData.getPreferredRank());
    model.addAttribute("mapURLForCar", "https://www.google.com/maps/embed/v1/directions?key=AIzaSyDVo4or3BW6Gcuz7FOMPsyGBBi0MbvoIRQ&origin="
                        +userData.getAddress().replace(" ", "+")+"&destination=6000+J+St,+Sacramento,+CA+95819&mode=driving");

    model.addAttribute("mapURLForWalk", "https://www.google.com/maps/embed/v1/directions?key=AIzaSyDVo4or3BW6Gcuz7FOMPsyGBBi0MbvoIRQ&origin="
                                        +userData.getAddress().replace(" ", "+")+"&destination=6000+J+St,+Sacramento,+CA+95819&mode=walking");

    model.addAttribute("mapURLForTransit", "https://www.google.com/maps/embed/v1/directions?key=AIzaSyDVo4or3BW6Gcuz7FOMPsyGBBi0MbvoIRQ&origin="
                                        +userData.getAddress().replace(" ", "+")+"&destination=6000+J+St,+Sacramento,+CA+95819&mode=transit");

    model.addAttribute("mapURLForBike", "https://www.google.com/maps/embed/v1/directions?key=AIzaSyDVo4or3BW6Gcuz7FOMPsyGBBi0MbvoIRQ&origin="
                                        +userData.getAddress().replace(" ", "+")+"&destination=6000+J+St,+Sacramento,+CA+95819&mode=bicycling");
    
    
    return "/takeFlight.html"; 
  }
}
