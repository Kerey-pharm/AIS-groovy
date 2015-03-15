package kz.kerey.services.ejb.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import kz.kerey.business.types.enums.LadderProperty;
import kz.kerey.business.types.enums.StepProperty;
import kz.kerey.business.wrappers.LadderWrapper;
import kz.kerey.business.wrappers.StepWrapper;
import kz.kerey.constants.Constants;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.flow.Ladder;
import kz.kerey.flow.Step;
import kz.kerey.services.api.FlowConfigurationInterface;

@Default
@Stateless
@Remote(FlowConfigurationInterface.class)
public class FlowConfigurationEJB implements FlowConfigurationInterface {

	@Resource(mappedName = "java:jboss/drugstoreEntityManagerFactory")
	public EntityManagerFactory emf;
	
	@Override
	public void createLadder(LadderWrapper ladder) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			List<Ladder> list = em.createQuery("from Ladder l where lower(l.name)=:text1")
					.setParameter("text1", ladder.getName().trim().toLowerCase())
					.getResultList();
			if (list.size()>0)
				throw new ServicesException(Constants.objectExists, "Ladder with name:"+ladder.getName()+" exists");
			Ladder obj = new Ladder();
			obj.setName(ladder.getName());
			obj.setComment(ladder.getComment());
			em.persist(obj);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public void deleteLadder(Long id) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Ladder obj = em.find(Ladder.class, id);
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "Ladder with ID:"+id+" is NULL");
			if (obj.getSteps().size()>0)
				throw new ServicesException(Constants.objectHasReferences, "Ladder with ID:"+id+" has Steps");
			em.remove(obj);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public List<LadderWrapper> getLadderList(Boolean paged, Integer pageNum,
			Integer perPage) {
		EntityManager em = null;
		List<LadderWrapper> result = new ArrayList<LadderWrapper>();
		try {
			em = emf.createEntityManager();
			Query query = em.createQuery("from Ladder l order by l.name");
			if (paged) {
				query = query.setFirstResult(perPage * (pageNum - 1))
						.setMaxResults(perPage);
			}
			List<Ladder> res = query.getResultList();
			for (Ladder l : res) {
				LadderWrapper o = new LadderWrapper();
				o.setId(l.getId());
				o.setName(l.getName());
				o.setComment(l.getComment());
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

	@Override
	public void changeLadderProperty(Long id, LadderProperty property,
			String newValue) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Ladder obj = em.find(Ladder.class, id);
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "Ladder with ID:"+id+" is NULL");
			em.createQuery("update Ladder set "+property.toString() +"=:value where id=:idValue")
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

	@Override
	public void createStep(StepWrapper step) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Ladder ladder = em.find(Ladder.class, step.getLadder());
			if (ladder==null)
				throw new ServicesException(Constants.objectIsNull, "Ladder with ID:"+step.getLadder()+" is NULL");
			List<Step> list = em.createQuery("from Step s where lower(s.name)=:S_NAME and s.ladder=:S_LADDER")
					.setParameter("S_NAME", step.getName().trim().toLowerCase())
					.setParameter("S_LADDER", ladder)
					.getResultList();
			if (list.size()>0)
				throw new ServicesException(Constants.objectExists, "Step with name:"+step.getName()+" exists");
			Step obj = new Step();
			obj.setName(step.getName());
			obj.setComment(step.getComment());
			obj.setLadder(ladder);
			em.persist(obj);
			ladder.getSteps().add(obj);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public void deleteStep(Long ladderId, Long id) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Ladder ladder = em.find(Ladder.class, ladderId);
			if (ladder==null)
				throw new ServicesException(Constants.objectIsNull, "Ladder with ID:"+id+" is NULL");
			Step step = em.find(Step.class, id);
			if (step==null)
				throw new ServicesException(Constants.objectIsNull, "Step with ID:"+id+" is NULL");
			if (ladder.getSteps().contains(step)) {
				ladder.getSteps().remove(step);
				em.remove(step);
			}
			else 
				throw new ServicesException(Constants.objectIsEmpty, "Ladder does not have Step with ID:"+id);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public void changeStepProperty(Long id, StepProperty property,
			String newValue) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Step obj = em.find(Step.class, id);
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "Step with ID:"+id+" is NULL");
			em.createQuery("update Step set "+property.toString() +"=:value where id=:idValue")
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

	@Override
	public List<StepWrapper> getLadderSteps(Long id) {
		EntityManager em = null;
		List<StepWrapper> result = new ArrayList<StepWrapper>();
		try {
			em = emf.createEntityManager();
			Ladder ladder = em.find(Ladder.class, id);
			if (ladder==null)
				throw new ServicesException(Constants.objectIsNull, "Ladder with ID:"+id+" is NULL");
			for (Step step : ladder.getSteps()) {
				StepWrapper obj = new StepWrapper();
				obj.setId(step.getId());
				obj.setName(step.getName());
				obj.setComment(step.getComment());
				obj.setLadder(ladder.getId());
				result.add(obj);
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
	public void swapLadderSteps(Long ladderId, Long left, Long right) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Ladder ladder = em.find(Ladder.class, ladderId);
			if (ladder==null)
				throw new ServicesException(Constants.objectIsNull, "Ladder with ID:"+ladderId+" is NULL");
			Step leftStep = em.find(Step.class, left);
			if (leftStep==null)
				throw new ServicesException(Constants.objectIsNull, "Left Step with ID:"+left+" is NULL");
			Step rightStep = em.find(Step.class, right);
			if (rightStep==null)
				throw new ServicesException(Constants.objectIsNull, "Right Step with ID:"+right+" is NULL");
			if (!ladder.getSteps().contains(leftStep) || !ladder.getSteps().contains(rightStep))
				throw new ServicesException(Constants.objectHasReferences, "Left Step has different ladder");
			int leftIndex = ladder.getSteps().indexOf(leftStep);
			int rightIndex = ladder.getSteps().indexOf(rightStep);
			Collections.swap(ladder.getSteps(), leftIndex, rightIndex);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}
	
}
