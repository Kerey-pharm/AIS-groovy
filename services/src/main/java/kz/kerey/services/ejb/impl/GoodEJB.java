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

import kz.kerey.business.types.Good;
import kz.kerey.business.types.GoodType;
import kz.kerey.business.types.PageParam;
import kz.kerey.business.types.enums.GoodProperty;
import kz.kerey.business.wrappers.GoodWrapper;
import kz.kerey.constants.Constants;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.services.api.GoodInterface;
import kz.kerey.tools.ClassTool;

@Stateless
@Default
@Remote(GoodInterface.class)
public class GoodEJB implements GoodInterface {

	@Resource(mappedName = "java:jboss/drugstoreEntityManagerFactory")
	private EntityManagerFactory emf;
	
	@Override
	public void createGood(GoodWrapper obj) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			List<Good> list = em.createQuery("from Good g where lower(g.name)=:text1")
					.setParameter("text1", obj.getName())
					.getResultList();
			if (list.size()>0)
				throw new ServicesException(Constants.objectExists, "Good with name: "+obj.getName()+" exists");
			Good good = new Good();
			good.setName(obj.getName());
			good.setCountPerBox(obj.getCountPerBox());
			good.setCountSellable(obj.getCountSellable());
			good.setPartialSelling(obj.getPartialSelling());
			good.setPrimaryBarcode(obj.getPrimaryBarcode());
			em.persist(good);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public void deleteGood(Long id) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Good good = em.find(Good.class, id);
			if (good==null)
				throw new ServicesException(Constants.objectIsNull, "Good with ID: "+id+" is NULL");
			em.remove(good);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public List<GoodWrapper> getGoodList(PageParam params, String nameFilter,
			String barcode) {
		List<GoodWrapper> result = new ArrayList<GoodWrapper>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Query query = null;
			
			if (nameFilter!=null && nameFilter.trim().length()>1 && barcode==null) {
				query = em.createQuery("from Good g where lower(g.name) like :text1 order by g.name")
						.setParameter("text1", "%"+nameFilter.trim().toLowerCase()+"%");
			}
			else if (barcode!=null && barcode.trim().length()>1 && nameFilter==null) {
				query = em.createQuery("from Good g where lower(g.primaryBarcode) = :text1 order by g.name")
						.setParameter("text1", barcode);
			}
			else {
				query = em.createQuery("from Good g order by g.name");
			}
			
			if (params!=null) {
				if (params.getPaged()) {
					query = query.setFirstResult(params.getPerPage() * (params.getPageNum() - 1))
							.setMaxResults(params.getPerPage());
				}
			}
			
			List<Good> list = query.getResultList();
			for (Good obj : list) {
				GoodWrapper good = new GoodWrapper();
				good.setId(obj.getId());
				good.setName(obj.getName());
				good.setCountPerBox(obj.getCountPerBox());
				good.setCountSellable(obj.getCountSellable());
				good.setPartialSelling(obj.getPartialSelling());
				good.setPrimaryBarcode(obj.getPrimaryBarcode());
				result.add(good);
			}
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
		return result;
	}

	@Override
	public void changeGoodProperty(Long id, GoodProperty property,
			String newValue) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Good good = em.find(Good.class, id);
			if (good==null)
				throw new ServicesException(Constants.objectIsNull, "Good with ID: "+id+" is NULL");
			Object val = ClassTool.getValuableProperty(Good.class,property.toString(),newValue);
			if (val==null)
				throw new ServicesException(Constants.objectIsNull, "Value is not parsed properly: "+ newValue);
			em.createQuery("update Good set "+property.toString() +"=:value where id=:idValue")
				.setParameter("value", val)
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