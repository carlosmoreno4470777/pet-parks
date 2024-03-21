package pet.park.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.park.controller.model.ContributorData;
import pet.park.controller.model.PetParkData;
import pet.park.service.ParkService;

@RestController
@RequestMapping("/pet_park")
@Slf4j
public class ParkController {
	@Autowired
	private ParkService parkService;

    @PostMapping("/contributor")
    @ResponseStatus(code = HttpStatus.CREATED)
	public ContributorData insertContributor(@RequestBody ContributorData contributorData) {
        log.info("Creating contributor {}", contributorData);
        return parkService.saveContributor(contributorData);
        //return parkService.saveContributor(contributorData);
	}
    
    @PutMapping("/contributor/{contributorId}")
    public ContributorData updateContributor(@PathVariable Long contributorId,
        @RequestBody ContributorData contributorData) {
      contributorData.setContributorId(contributorId);
      log.info("Updating contributor {}", contributorData);
      return parkService.saveContributor(contributorData);
    }
    
    
    @GetMapping("/contributor")
    public List<ContributorData> retrieveAllContributors() {
      log.info("Retrieving all contributors.");
      return parkService.retrieveAllContributors();
    }
    
    @GetMapping("/contributor/{contributorId}")
    public ContributorData retrieveContributorById(
        @PathVariable Long contributorId) {
      log.info("Retrieving contributor with ID={}", contributorId);
      return parkService.retrieveContributorById(contributorId);
    }
    
    @DeleteMapping("/contributor")
    public void deleteAllContributors(
        @PathVariable Long contributorId) {
      log.info("Attempting to delte all contributors");
      throw new UnsupportedOperationException("Deleting all contributors is not allowed ");
    }
    
    //We are accepting a contributorId for the table in the URL
    @DeleteMapping("/contributor/{contributorId}")
    public Map<String, String> deleteContributorById(
        @PathVariable Long contributorId) {
      log.info("Deleting contributor with ID={}", contributorId);

      //we are deleting on the service
      parkService.deleteContributorById(contributorId);

      //if it succesfull it will get to this point
      return Map.of("message",
          "Contributor with ID=" + contributorId + " deleted successfully.");
    }
    
    @PostMapping("/contributor/{contributorId}/park")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetParkData insertPetPark(@PathVariable Long contributorId,
        @RequestBody PetParkData petParkData) {

      log.info("Creating park {} for contributor with ID={}", petParkData,
          contributorId);
      return parkService.savePetPark(contributorId, petParkData);
    }
    
    
    
}// end of class ParkController