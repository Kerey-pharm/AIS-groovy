package kz.kerey.services.ejb.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import kz.kerey.business.types.PageParam;
import kz.kerey.business.types.enums.TaskProperty;
import kz.kerey.business.types.enums.TaskStatus;
import kz.kerey.business.types.tasks.Task;
import kz.kerey.business.wrappers.TaskWrapper;
import kz.kerey.constants.Constants;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.flow.Ladder;
import kz.kerey.services.api.FlowInterface;
import kz.kerey.tools.BarcodeTool;

@Default
@Stateless
@Remote(FlowInterface.class)
public class FlowEJB implements FlowInterface {

	final private static long dayTimeMillis = 24 * 60 * 60 * 1000; 
	
	@Resource(mappedName = "java:jboss/drugstoreEntityManagerFactory")
	public EntityManagerFactory emf;
	
	@Override
	public void createTask(TaskWrapper task) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Ladder ladder = em.find(Ladder.class, task.getLadder());
			if (ladder==null)
				throw new ServicesException(Constants.objectIsNull, "Ladder is NULL");
			Task obj = new Task();
			obj.setLadder(ladder);
			obj.setStatus(TaskStatus.Created);
			obj.setInitialDate(new Date());
			obj.setDeadlineDate(new Date((new Date()).getTime() + dayTimeMillis * 5)); //5 days after initiation
			if (ladder.getExecutionTime()!=null) {
				Date current = new Date();
				Long time1 = current.getTime() + ladder.getExecutionTime();
				obj.setDeadlineDate(new Date(time1));
			}
			em.persist(obj);
			String barcode = BarcodeTool.getBarcodeValue(obj.getId());
			if (barcode==null) 
				throw new ServicesException(Constants.objectIsNull, "Barcode is NULL, check Trace");
			obj.setBarcode(barcode);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public void deleteTask(Long id) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Task task = em.find(Task.class, id);
			if (task==null)
				throw new ServicesException(Constants.objectIsNull, "Task with ID: "+id+" is NULL");
			if (!task.getStatus().equals(TaskStatus.Created))
				throw new ServicesException(Constants.businessError, "Task status: "+task.getStatus().toString()+", and can not be deleted");
			em.remove(task);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public void changeTaskProperty(Long id, TaskProperty property, String value) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Task obj = em.find(Task.class, id);
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "Task with ID:"+id+" is NULL");
			em.createQuery("update Task set "+property.toString() +"=:value where id=:idValue")
				.setParameter("value", value)
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
	public void changeTaskDeadlineDate(Long id, Date date) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Task obj = em.find(Task.class, id);
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "Task with ID:"+id+" is NULL");
			obj.setDeadlineDate(date);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public void changeTaskStatus(Long id, TaskStatus status) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Task obj = em.find(Task.class, id);
			if (obj==null)
				throw new ServicesException(Constants.objectIsNull, "Task with ID:"+id+" is NULL");
			obj.setStatus(status);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public List<TaskWrapper> getTaskList(PageParam param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskWrapper> getTaskFiltered(PageParam param, Long userId,
			TaskStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void nextStep(Long taskId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void previousStep(Long taskId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void assignTask(Long taskId, Long userId) {
		// TODO Auto-generated method stub

	}

}
