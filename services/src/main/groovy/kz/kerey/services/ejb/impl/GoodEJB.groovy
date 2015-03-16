package kz.kerey.services.ejb.impl

import javax.annotation.Resource
import javax.ejb.Local
import javax.ejb.Singleton

import kz.kerey.business.types.Good
import kz.kerey.business.types.PageParam
import kz.kerey.business.types.enums.GoodProperty
import kz.kerey.business.wrappers.GoodWrapper
import kz.kerey.constants.Constants
import kz.kerey.exceptions.ServicesException
import kz.kerey.tools.ClassTool

@Singleton
@Local(GoodEJB.class)
class GoodEJB {

	@Resource(mappedName = "java:jboss/drugstoreEntityManagerFactory")
	def emf

	void createGood(GoodWrapper obj) {
		def em
		try {
			em = emf.createEntityManager()
			def list = em.createQuery("from Good g where lower(g.name)=:text1")
					.setParameter("text1", obj.getName())
					.getResultList()
			if (list.size()>0)
				throw new ServicesException(Constants.objectExists, "Good with name: ${obj.name} exists")
			em.persist(new Good(
                    name: obj.name,
                    countPerBox: obj.countPerBox,
                    countSellable: obj.countSellable,
                    partialSelling: obj.partialSelling,
                    primaryBarcode: obj.primaryBarcode))
		}
		finally {
			if (em!=null && em.isOpen())
                em.close()
		}
	}

	public void deleteGood(Long id) {
		def em
		try {
			em = emf.createEntityManager()
			def good = em.find(Good.class, id)
			if (good==null)
				throw new ServicesException(Constants.objectIsNull, "Good with ID: ${id} is NULL")
			em.remove(good)
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	List<GoodWrapper> getGoodList(PageParam params, String nameFilter, String barcode) {
		def result = new ArrayList<GoodWrapper>()
		def em
		try {
			em = emf.createEntityManager()
			def query
			
			if (nameFilter!=null && nameFilter.trim().length()>1 && barcode==null) {
				query = em.createQuery("from Good g where lower(g.name) like :text1 order by g.name")
						.setParameter("text1", "%${nameFilter.trim().toLowerCase()}%")
			}
			else if (barcode!=null && barcode.trim().length()>1 && nameFilter==null) {
				query = em.createQuery("from Good g where lower(g.primaryBarcode) = :text1 order by g.name")
						.setParameter("text1", barcode)
			}
			else {
				query = em.createQuery("from Good g order by g.name")
			}
			
			if (params!=null) {
				if (params.paged) {
					query = query.setFirstResult(params.perPage * (params.pageNum - 1))
							.setMaxResults(params.perPage)
				}
			}
			
			def list = query.getResultList()
			for (Good obj in list) {
				result.add(new GoodWrapper(
                        id: obj.id,
                        name: obj.name,
                        countPerBox: obj.countPerBox,
                        countSellable: obj.countSellable,
                        partialSelling: obj.partialSelling,
                        primaryBarcode: obj.primaryBarcode
                ))
			}
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
		result
	}

	void changeGoodProperty(Long id, GoodProperty property, String newValue) {
		def em
		try {
			em = emf.createEntityManager()
			def good = em.find(Good.class, id)
			if (good==null)
				throw new ServicesException(Constants.objectIsNull, "Good with ID: ${id} is NULL")
			def val = ClassTool.getValuableProperty(Good.class,property.toString(),newValue)
			if (val==null)
				throw new ServicesException(Constants.objectIsNull, "Value is not parsed properly: ${newValue}")
			em.createQuery("update Good set ${property.toString()}=:value where id=:idValue")
				.setParameter("value", val)
				.setParameter("idValue", id)
				.executeUpdate()
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}
	
}