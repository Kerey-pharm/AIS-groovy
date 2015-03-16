package kz.kerey.services.ejb.impl

import javax.annotation.Resource
import javax.ejb.Local
import javax.ejb.Singleton

import kz.kerey.business.types.PageParam
import kz.kerey.business.types.enums.TaskProperty
import kz.kerey.business.types.enums.TaskStatus
import kz.kerey.business.types.tasks.Task
import kz.kerey.business.wrappers.TaskWrapper
import kz.kerey.constants.Constants
import kz.kerey.exceptions.ServicesException
import kz.kerey.flow.Ladder
import kz.kerey.tools.BarcodeTool

@Singleton
@Local(FlowEJB.class)
class FlowEJB {

	def static dayTimeMillis = 24 * 60 * 60 * 1000
	
	@Resource(mappedName = "java:jboss/drugstoreEntityManagerFactory")
	def emf;

	void createTask(TaskWrapper task) {
		def em
		try {
			em = emf.createEntityManager()
			def ladder = em.find(Ladder.class, task.getLadder())
			if (ladder==null)
				throw new ServicesException(Constants.objectIsNull, "Ladder is NULL")
			def obj = new Task(ladder: ladder, status: TaskStatus.Created, initialDate: new Date(), deadlineDate: new Date((new Date()).getTime() + dayTimeMillis * 5))
			if (ladder.executionTime!=null) {
				def current = new Date()
				def time1 = current.getTime() + ladder.executionTime
				obj.deadlineDate = new Date(time1)
			}
			em.persist(obj)
			def barcode = BarcodeTool.getBarcodeValue(obj.getId())
			if (barcode==null) 
				throw new ServicesException(Constants.objectIsNull, "Barcode is NULL, check Trace")
			obj.barcode = barcode
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	void deleteTask(Long id) {
		def em
		try {
			em = emf.createEntityManager()
			def task = em.find(Task.class, id)
			if (task==null)
				throw new ServicesException(Constants.objectIsNull, "Task with ID: ${id} is NULL")
			if (!task.status.equals(TaskStatus.Created))
				throw new ServicesException(Constants.businessError, "Task status: ${task.status.toString()}, and can not be deleted")
			em.remove(task)
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	void changeTaskProperty(Long id, TaskProperty property, String value) {
		def em
		try {
			em = emf.createEntityManager()
			def obj = em.find(Task.class, id)
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "Task with ID:${id} is NULL")
			em.createQuery("update Task set ${property.toString()}=:value where id=:idValue")
				.setParameter("value", value)
				.setParameter("idValue", id)
				.executeUpdate()
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	void changeTaskDeadlineDate(Long id, Date date) {
		def em
		try {
			em = emf.createEntityManager()
			def obj = em.find(Task.class, id)
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "Task with ID:${id} is NULL")
			obj.deadlineDate = date
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	void changeTaskStatus(Long id, TaskStatus status) {
		def em = null;
		try {
			em = emf.createEntityManager()
			def obj = em.find(Task.class, id)
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "Task with ID:${id} is NULL")
			obj.status = status
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	List<TaskWrapper> getTaskList(PageParam param) {
		// TODO Auto-generated method stub
		null
	}

	List<TaskWrapper> getTaskFiltered(PageParam param, Long userId,
			TaskStatus status) {
		// TODO Auto-generated method stub
		null
	}

	void nextStep(Long taskId) {
		// TODO Auto-generated method stub
	}

	void previousStep(Long taskId) {
		// TODO Auto-generated method stub
	}

	void assignTask(Long taskId, Long userId) {
		// TODO Auto-generated method stub
	}

}