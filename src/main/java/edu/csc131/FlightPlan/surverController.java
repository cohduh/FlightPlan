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
   * @param insurenceRate
   * @return Forward to Get mapped function.
   */
  @PostMapping(path = "/takeFlight") // Map ONLY POST Requests
  public String saveData(@RequestParam String address, @RequestParam double mpg,
      @RequestParam("insRate") double insurenceRate) {
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

    //TODO add transportation travel tiem
    //TODO add bicycle travel time

    //TODO Add the Ranking here
    //TODO Save the ranking to userData + add UserData fields. Save results? Maybe. 

     
    //Hard-coded input
         double walking_d = 11; 
         double walking_t = 10; 
        
         double biking_d = 9; 
         double biking_t = 8;
         
         double public_d = 7; 
         double public_t = 8; 
         
         double driving_d = 9; 
         double driving_t = 10;

         String carType = "Sedan";
   
      //Creates Ranking object
         Ranking r = new Ranking(walking_d, walking_t, biking_d, biking_t, public_d, public_t, driving_d, driving_t, carType);
         
      /* NOTE:
      Array itself is not sorted so Alec can tag them and rank them as he said in the meeting
      Below: Sample Ranking Output based on arbitary scores Justin created in TransportMode.java:
         
      double[] ranked_by_whatever = r.rank_all();
         for (double each : ranked_by_whatever)
            System.out.println(each);
      */ 
      
    userData.setAddress(address);
    userData.settravelTimeByCar(travelTimeByCar);
    userData.setTravelTimeByWalking(travelTimeByWalk);
    userData.setTravelTimeByTransit(travelTimeByTransit);
    userData.setTravelTimeByBike(travelTimeByBike);

    userData.setTravelDistanceByCar(travelDistanceByCar);
    userData.setTravelDistanceByWalking(travelDistanceByWalking);
    userData.setTravelDistanceByTransit(travelDistanceByTransit);
    userData.setTravelDistanceByBike(travelDistanceByBike);

    userData.setInsurenceRate(insurenceRate);
    userData.setMpg(mpg);

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
    model.addAttribute("mpg", userData.getMpg());
    model.addAttribute("insurenceRate", userData.getInsurenceRate());
    //TODO add Ranking results to the model
    return "/takeFlight.html";
  }
}
