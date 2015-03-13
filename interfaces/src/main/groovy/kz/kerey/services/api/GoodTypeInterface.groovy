package kz.kerey.services.api

import java.util.List

import kz.kerey.business.types.enums.GoodTypeProperty
import kz.kerey.business.wrappers.GoodTypeWrapper

interface GoodTypeInterface {

	void createGoodType(GoodTypeWrapper type)
	void deleteGoodType(Long id)
	List<GoodTypeWrapper> getGoodTypeList(Boolean paged, Integer pageNum, Integer perPage)
	void changeGoodTypeProperty(Long id, GoodTypeProperty property, String newValue)
	
}
