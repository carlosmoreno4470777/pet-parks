package pet.park.entity;

import java.math.BigDecimal;
import java.util.Objects;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class GeoLocation {
	private BigDecimal latitude;
	private BigDecimal longitude;

	//Constructor
	public GeoLocation(GeoLocation geoLocation) {
		
		//this.latitude = geoLocation.latitude;
		//this.longitude = geoLocation.longitude;
	    if(Objects.nonNull(geoLocation)) {
	        if(Objects.nonNull(geoLocation.latitude)) {
	          this.latitude = new BigDecimal(geoLocation.latitude.toString());
	        }

	        if(Objects.nonNull(geoLocation.longitude)) {
	          this.longitude = new BigDecimal(geoLocation.longitude.toString());
	        }
	      }
		
	}
	
}
