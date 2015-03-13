package kz.kerey.services.api

import java.util.List

import kz.kerey.business.types.enums.LocationProperty
import kz.kerey.business.wrappers.LocationWrapper

interface LocationInterface {

	void createLocation(LocationWrapper location)
	void deleteLocation(Long id)
	void changeLocation(Long id, LocationProperty property, String newValue)
	List<LocationWrapper> getLocationsList(Boolean paged, Integer pageNum, Integer perPage)
	
}
