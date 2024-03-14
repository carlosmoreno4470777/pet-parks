package pet.park.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import pet.park.controller.model.ContributorData;
import pet.park.service.parkService;

@RestController
@RequestMapping("/pet_park")
@Slf4j
public class ParkController {
  //@Autowired
  //private ParkService parkService;

    @PostMapping("/contributor")
	public ContributorData insertContributor(@RequestBody ContributorData contributorData) {
        log.info("Creating contributor {}", contributorData);
        return parkService.saveContributor(contributorData);
	}
    
}// end of class ParkController