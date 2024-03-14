package pet.park.service;

import org.springframework.transaction.annotation.Transactional;
import pet.park.controller.model.ContributorData;

public class parkService {

	  @Transactional(readOnly = false)
	  public static ContributorData saveContributor(ContributorData contributorData) {
			/*
			 * Long contributorId = contributorData.getContributorId(); Contributor
			 * contributor = findOrCreateContributor(contributorId);
			 * 
			 * setFieldsInContributor(contributor, contributorData); return new
			 * ContributorData(contributorDao.save(contributor));
			 */
		  return null;
	  }

}
