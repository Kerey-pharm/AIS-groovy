package kz.kerey.services.api

import java.util.List

import kz.kerey.business.types.PageParam
import kz.kerey.business.types.enums.GoodProperty
import kz.kerey.business.wrappers.GoodWrapper

interface GoodInterface {

	void createGood(GoodWrapper obj)
	void deleteGood(Long id)
	List<GoodWrapper> getGoodList(PageParam params, String nameFilter, String barcode)
	void changeGoodProperty(Long id, GoodProperty property, String newValue)

}