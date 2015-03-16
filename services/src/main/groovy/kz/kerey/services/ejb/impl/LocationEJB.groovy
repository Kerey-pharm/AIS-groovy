package kz.kerey.services.ejb.impl

import javax.annotation.Resource
import javax.ejb.Local

import kz.kerey.business.types.enums.LocationProperty
import kz.kerey.business.types.points.Location
import kz.kerey.business.wrappers.LocationWrapper
import kz.kerey.constants.Constants
import kz.kerey.exceptions.ServicesException

import javax.ejb.Stateless

@Stateless
@Local(LocationEJB.class)
class LocationEJB {

	@Resource(mappedName = "java:jboss/drugstoreEntityManagerFactory")
	def emf
	
	void createLocation(LocationWrapper location) {
		def em
		try {
			em = emf.createEntityManager()
			def list = em.createQuery("from Location l where lower(l.name)=:text1")
					.setParameter("text1", location.name.trim().toLowerCase())
					.getResultList()
			if (list.size()>0)
				throw new ServicesException(Constants.objectExists, "Location with name: ${location.name} exists")
			em.persist(new Location(name: location.name))
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	void deleteLocation(Long id) {
		def em
		try {
			em = emf.createEntityManager()
			def loc = em.find(Location.class, id)
			if (loc==null)
				throw new ServicesException(Constants.objectIsNull, "Location with ID: ${id} is NULL")
			em.remove(loc)
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	void changeLocation(Long id, LocationProperty property, String newValue) {
		def em = null;
		try {
			em = emf.createEntityManager()
			def obj = em.find(Location.class, id)
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "Location with ID:${id} is NULL")
			em.createQuery("update Location set ${property.toString()}=:value where id=:idValue")
				.setParameter("value", newValue)
				.setParameter("idValue", id)
				.executeUpdate()
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	List<LocationWrapper> getLocationsList(Boolean paged, Integer pageNum, Integer perPage) {
		def result = new ArrayList<LocationWrapper>()
		def em = null
		try {
			em = emf.createEntityManager()
			def query = em.createQuery("from Location l order by l.name")
			if (paged) {
				query = query.setFirstResult(perPage * (pageNum - 1)).setMaxResults(perPage)
			}
			def res = query.getResultList()
			for (Location l in res) {
				result.add(new LocationWrapper(id: l.id, name: l.name))
			}
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
		result
	}

}
