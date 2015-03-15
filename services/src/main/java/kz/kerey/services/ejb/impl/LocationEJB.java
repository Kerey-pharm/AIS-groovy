package kz.kerey.services.ejb.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import kz.kerey.business.types.GoodType;
import kz.kerey.business.types.enums.LocationProperty;
import kz.kerey.business.types.points.Location;
import kz.kerey.business.wrappers.LadderWrapper;
import kz.kerey.business.wrappers.LocationWrapper;
import kz.kerey.constants.Constants;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.flow.Ladder;
import kz.kerey.services.api.LocationInterface;

@Stateless
@Default
@Remote(LocationInterface.class)
public class LocationEJB implements LocationInterface {

	@Resource(mappedName = "java:jboss/drugstoreEntityManagerFactory")
	public EntityManagerFactory emf;
	
	public void createLocation(LocationWrapper location) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			List<Location> list = em.createQuery("from Location l where lower(l.name)=:text1")
					.setParameter("text1", location.getName().trim().toLowerCase())
					.getResultList();
			if (list.size()>0)
				throw new ServicesException(Constants.objectExists, "Location with name: "+location.getName()+" exists");
			Location loc = new Location();
			loc.setName(location.getName());
			em.persist(loc);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	public void deleteLocation(Long id) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Location loc = em.find(Location.class, id);
			if (loc==null)
				throw new ServicesException(Constants.objectIsNull, "Location with ID: "+ id +" is NULL");
			em.remove(loc);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	public void changeLocation(Long id, LocationProperty property,
			String newValue) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Location obj = em.find(Location.class, id);
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "Location with ID:"+id+" is NULL");
			em.createQuery("update Location set "+property.toString() +"=:value where id=:idValue")
				.setParameter("value", newValue)
				.setParameter("idValue", id)
				.executeUpdate();
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	public List<LocationWrapper> getLocationsList(Boolean paged,
			Integer pageNum, Integer perPage) {
		List<LocationWrapper> result = new ArrayList<LocationWrapper>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Query query = em.createQuery("from Location l order by l.name");
			if (paged) {
				query = query.setFirstResult(perPage * (pageNum - 1))
						.setMaxResults(perPage);
			}
			List<Location> res = query.getResultList();
			for (Location l : res) {
				LocationWrapper o = new LocationWrapper();
				o.setId(l.getId());
				o.setName(l.getName());
				result.add(o);
			}
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
		return result;
	}

}
