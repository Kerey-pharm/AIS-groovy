package kz.kerey.services.ejb.impl

import javax.annotation.Resource

import kz.kerey.business.types.enums.LadderProperty
import kz.kerey.business.types.enums.StepProperty
import kz.kerey.business.wrappers.LadderWrapper
import kz.kerey.business.wrappers.StepWrapper
import kz.kerey.constants.Constants
import kz.kerey.exceptions.ServicesException
import kz.kerey.flow.Ladder
import kz.kerey.flow.Step

import javax.ejb.Local
import javax.ejb.Singleton

@Singleton
@Local(FlowConfigurationEJB.class)
class FlowConfigurationEJB {

	@Resource(mappedName = "java:jboss/drugstoreEntityManagerFactory")
	def emf;

	void createLadder(LadderWrapper ladder) {
		def em = null;
		try {
			em = emf.createEntityManager()
			def list = em.createQuery("from Ladder l where lower(l.name)=:text1")
					.setParameter("text1", ladder.name.trim().toLowerCase())
					.getResultList()
			if (list.size()>0)
				throw new ServicesException(Constants.objectExists, "Ladder with name:${ladder.name} exists")
			Ladder obj = new Ladder(name: ladder.name, comment: ladder.comment)
			em.persist(obj)
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	public void deleteLadder(Long id) {
		def em = null;
		try {
			em = emf.createEntityManager()
			def obj = em.find(Ladder.class, id)
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "Ladder with ID:${id} is NULL")
			if (obj.steps.size()>0)
				throw new ServicesException(Constants.objectHasReferences, "Ladder with ID:${id} has Steps")
			em.remove(obj);
		}
		finally {
			if (em!=null && em.isOpen())
                em.close()
		}
	}

	List<LadderWrapper> getLadderList(Boolean paged, Integer pageNum, Integer perPage) {
		def em = null;
		def result = new ArrayList<LadderWrapper>()
		try {
			em = emf.createEntityManager()
			def query = em.createQuery("from Ladder l order by l.name")
			if (paged) {
				query = query.setFirstResult(perPage * (pageNum - 1))
						.setMaxResults(perPage)
			}
			def res = query.getResultList()
			for (Ladder l in res) {
				result.add(new LadderWrapper(id: l.id, name: l.name, comment: l.comment))
			}
		}
		finally {
			if (em!=null && em.isOpen())
                em.close()
		}
		result
	}

	void changeLadderProperty(Long id, LadderProperty property, String newValue) {
		def em
		try {
			em = emf.createEntityManager()
			def obj = em.find(Ladder.class, id)
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "Ladder with ID:${id} is NULL")
			em.createQuery("update Ladder set ${property.toString()}=:value where id=:idValue")
				.setParameter("value", newValue)
				.setParameter("idValue", id)
				.executeUpdate()
		}
		finally {
			if (em!=null && em.isOpen())
					em.close()
		}
	}

	void createStep(StepWrapper step) {
		def em = null
		try {
			em = emf.createEntityManager()
			def ladder = em.find(Ladder.class, step.getLadder())
			if (ladder==null)
				throw new ServicesException(Constants.objectIsNull, "Ladder with ID:${step.ladder} is NULL")
			def list = em.createQuery("from Step s where lower(s.name)=:S_NAME and s.ladder=:S_LADDER")
					.setParameter("S_NAME", step.getName().trim().toLowerCase())
					.setParameter("S_LADDER", ladder)
					.getResultList()
			if (list.size()>0)
				throw new ServicesException(Constants.objectExists, "Step with name:${step.name} exists")
			def obj = new Step(name: step.name, comment: step.comment, ladder: ladder)
			em.persist(obj)
			ladder.steps.add(obj)
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	void deleteStep(Long ladderId, Long id) {
		def em
		try {
			em = emf.createEntityManager()
			def ladder = em.find(Ladder.class, ladderId)
			if (ladder==null)
				throw new ServicesException(Constants.objectIsNull, "Ladder with ID:${id} is NULL")
			def step = em.find(Step.class, id)
			if (step==null)
				throw new ServicesException(Constants.objectIsNull, "Step with ID:${id} is NULL")
			if (ladder.steps.contains(step)) {
				ladder.steps.remove(step)
				em.remove(step)
			}
			else 
				throw new ServicesException(Constants.objectIsEmpty, "Ladder does not have Step with ID:${id}")
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	void changeStepProperty(Long id, StepProperty property,
			String newValue) {
		def em
		try {
			em = emf.createEntityManager()
			def obj = em.find(Step.class, id)
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "Step with ID:${id} is NULL")
			em.createQuery("update Step set ${property.toString()}=:value where id=:idValue")
				.setParameter("value", newValue)
				.setParameter("idValue", id)
				.executeUpdate()
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	List<StepWrapper> getLadderSteps(Long id) {
		def em
		def result = new ArrayList<StepWrapper>()
		try {
			em = emf.createEntityManager()
			def ladder = em.find(Ladder.class, id)
			if (ladder==null)
				throw new ServicesException(Constants.objectIsNull, "Ladder with ID:${id} is NULL")
			for (Step step in ladder.steps) {
				result.add(new StepWrapper(id: step.id, name: step.name, comment: step.comment, ladder: ladder.id))
			}
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
		result
	}

	void swapLadderSteps(Long ladderId, Long left, Long right) {
		def em
		try {
			em = emf.createEntityManager()
			def ladder = em.find(Ladder.class, ladderId)
			if (ladder==null)
				throw new ServicesException(Constants.objectIsNull, "Ladder with ID:${ladderId} is NULL")
			def leftStep = em.find(Step.class, left)
			if (leftStep==null)
				throw new ServicesException(Constants.objectIsNull, "Left Step with ID:${left} is NULL")
			def rightStep = em.find(Step.class, right)
			if (rightStep==null)
				throw new ServicesException(Constants.objectIsNull, "Right Step with ID:${right} is NULL")
			if (!ladder.steps.contains(leftStep) || !ladder.steps.contains(rightStep))
				throw new ServicesException(Constants.objectHasReferences, "Left Step has different ladder")
			int leftIndex = ladder.steps.indexOf(leftStep)
			int rightIndex = ladder.steps.indexOf(rightStep)
			Collections.swap(ladder.getSteps(), leftIndex, rightIndex)
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}
	
}
