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

import kz.kerey.services.api.GoodTypeInterface;
import kz.kerey.business.types.GoodType;
import kz.kerey.business.types.enums.GoodTypeProperty;
import kz.kerey.business.wrappers.GoodTypeWrapper;
import kz.kerey.constants.Constants;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.flow.Ladder;

@Default
@Stateless
@Remote(GoodTypeInterface.class)
public class GoodTypeEJB implements GoodTypeInterface {

	@Resource(mappedName = "java:jboss/drugstoreEntityManagerFactory")
	public EntityManagerFactory emf;
	
	public void createGoodType(GoodTypeWrapper type) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			List<GoodType> list = em.createQuery("from GoodType where lower(name)=:text1")
					.setParameter("text1", type.getName().toLowerCase())
					.getResultList();
			if (list.size()>0)
				throw new ServicesException(Constants.objectExists, "GoodType with name:"+type.getName()+" exists");
			GoodType obj = new GoodType();
			obj.setName(type.getName());
			em.persist(obj);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}
	
	public void deleteGoodType(Long id) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			GoodType obj = em.find(GoodType.class, id);
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "GoodType with ID:"+id+" is NULL");
			em.remove(obj);
		}
		finally {
			if (em!=null) 
				if (em.isOpen())
					em.close();
		}
	}
	
	public List<GoodTypeWrapper> getGoodTypeList(Boolean paged, Integer pageNum, Integer perPage) {
		EntityManager em = null;
		List<GoodTypeWrapper> result = new ArrayList<GoodTypeWrapper>();
		try {
			em = emf.createEntityManager();
			Query query = em.createQuery("from GoodType order by name");
			if (paged) {
				query = query.setFirstResult(perPage * (pageNum - 1))
						.setMaxResults(perPage);
			}
			List<GoodType> list = query.getResultList();
			for(GoodType type : list) {
				GoodTypeWrapper r = new GoodTypeWrapper();
				r.setId(type.getId());
				r.setName(type.getName());
				result.add(r);
			}
			return result;
		}
		finally {
			if (em!=null) 
				if (em.isOpen())
					em.close();
		}
	}

	public void changeGoodTypeProperty(Long id, GoodTypeProperty property,
			String newValue) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			GoodType obj = em.find(GoodType.class, id);
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "GoodType with ID:"+id+" is NULL");
			em.createQuery("update GoodType set "+property.toString() +"=:value where id=:idValue")
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
	
}
