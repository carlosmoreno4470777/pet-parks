package pet.park.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.park.controller.model.ContributorData;
import pet.park.controller.model.PetParkData;
import pet.park.dao.AmenityDao;
import pet.park.dao.ContributorDao;
import pet.park.dao.PetParkDao;
import pet.park.entity.Amenity;
import pet.park.entity.Contributor;
import pet.park.entity.PetPark;

@Service
public class ParkService {

	  @Autowired
	  private AmenityDao amenityDao;

	  @Autowired
	  private PetParkDao petParkDao;

	  /**
	   * The @Autowired annotation instructs Spring to inject an object of the
	   * required type into the instance variable.
	   */
	  @Autowired
	  private ContributorDao contributorDao;

	@Transactional(readOnly = false)
	public ContributorData saveContributor(ContributorData contributorData) {
		Long contributorId = contributorData.getContributorId();
		// Contributor contributor = findOrCreateContributor(contributorId);
		Contributor contributor = findOrCreateContributor(contributorId, contributorData.getContributorEmail());

		setFieldsInContributor(contributor, contributorData);

		return new ContributorData(contributorDao.save(contributor));

		// return null;
	}

	private void setFieldsInContributor(Contributor contributor, ContributorData contributorData) {
		contributor.setContributorEmail(contributorData.getContributorEmail());
		contributor.setContributorName(contributorData.getContributorName());
	}

	/*
	 * private Contributor findOrCreateContributor(Long contributorId) { Contributor
	 * contributor;
	 * 
	 * if (Objects.isNull(contributorId)) { contributor = new Contributor(); } else
	 * { contributor = findContributorById(contributorId); }
	 * 
	 * return contributor; }
	 */

	private Contributor findOrCreateContributor(Long contributorId, String contributorEmail) {
		Contributor contributor;

		if (Objects.isNull(contributorId)) {
			Optional<Contributor> opContrib = contributorDao.findByContributorEmail(contributorEmail);

			if (opContrib.isPresent()) {
				throw new DuplicateKeyException(" Contributor with email " + contributorEmail + " already exist ");
			} else {

			}

			contributor = new Contributor();
		} else {
			contributor = findContributorById(contributorId);
		}

		return contributor;
	}

	private Contributor findContributorById(Long contributorId) {

		return contributorDao.findById(contributorId).orElseThrow(
				() -> new NoSuchElementException("Contributor with ID=" + contributorId + " was  not found."));
	}

	@Transactional(readOnly = true)
	public List<ContributorData> retrieveAllContributors() {
		/*
		 * Uncomment the code below to retrieve all contributors using traditional
		 * (non-functional) coding techniques. An enhanced for loop is used to convert
		 * from the list of Contributor to the list of ContributorData required by the
		 * method return type.
		 */
		// List<Contributor> contributors = contributorDao.findAll();
		// List<ContributorData> response = new LinkedList<>();
		//
		// for(Contributor contributor : contributors) {
		// response.add(new ContributorData(contributor));
		// }
		//
		// return response;

		// @formatter:off
		    return contributorDao.findAll() // Retrieves a List of Contributor
		        .stream()                   // Converts to a Stream of Contributor
		        .map(ContributorData::new)  // Converts to a Stream of ContributorData
		        .toList();                  // Converts to a List of ContributorData
		    // @formatter:on
	}

	@Transactional(readOnly = true)
	public ContributorData retrieveContributorById(Long contributorId) {
		Contributor contributor = findContributorById(contributorId);
		return new ContributorData(contributor);
	}

	@Transactional(readOnly = false)
	public void deleteContributorById(Long contributorId) {
		Contributor contributor = findContributorById(contributorId);
		contributorDao.delete(contributor);
	}

	@Transactional(readOnly = false)
	public PetParkData savePetPark(Long contributorId, PetParkData petParkData) {
		// find contributor by finding contributor ID
		Contributor contributor = findContributorById(contributorId);

		Set<Amenity> amenities = amenityDao.findAllByAmenityIn(petParkData.getAmenities());

		// passing pet park ID that might be null
		PetPark petPark = findOrCreatePetPark(petParkData.getPetParkId());
		setPetParkFields(petPark, petParkData);

		petPark.setContributor(contributor);
		contributor.getPetParks().add(petPark);

		for (Amenity amenity : amenities) {
			amenity.getPetParks().add(petPark);
			petPark.getAmenities().add(amenity);
		}

		PetPark dbPetPark = petParkDao.save(petPark);
		return new PetParkData(dbPetPark);

	}

	private void setPetParkFields(PetPark petPark, PetParkData petParkData) {
	    petPark.setCountry(petParkData.getCountry());
	    petPark.setDirections(petParkData.getDirections());
	    petPark.setGeoLocation(petParkData.getGeoLocation());
	    petPark.setParkName(petParkData.getParkName());
	    petPark.setPetParkId(petParkData.getPetParkId());
	    petPark.setStateOrProvince(petParkData.getStateOrProvince());		
	}

	private PetPark findOrCreatePetPark(Long petParkId) {
		PetPark petPark;

		if (Objects.isNull(petParkId)) {
			petPark = new PetPark();
		} else {
			petPark = findPetParkById(petParkId);
		}

		return petPark;
	}

	private PetPark findPetParkById(Long petParkId) {
		return petParkDao.findById(petParkId)
				.orElseThrow(() -> new NoSuchElementException("Pet park with ID=" + petParkId + " does not exist."));
	}

}
