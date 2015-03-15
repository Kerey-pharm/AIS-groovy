package kz.kerey.services.ejb.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import kz.kerey.business.types.enums.RoleProperty;
import kz.kerey.business.types.enums.UserProperty;
import kz.kerey.business.user.Role;
import kz.kerey.business.user.User;
import kz.kerey.business.wrappers.RoleWrapper;
import kz.kerey.business.wrappers.UserWrapper;
import kz.kerey.constants.Constants;
import kz.kerey.exceptions.ServicesException;
import kz.kerey.services.api.UserInterface;

@Default
@Stateless
@Remote(UserInterface.class)
public class UserEJB implements UserInterface {

	@Resource(mappedName = "java:jboss/drugstoreEntityManagerFactory")
	public EntityManagerFactory emf;
	
	@Override
	public void createRole(RoleWrapper obj) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			List<Role> roles = em.createQuery("from Role r where lower(r.name)=:text1")
					.setParameter("text1", obj.getName().toLowerCase().trim())
					.getResultList();
			if (roles.size()>0)
				throw new ServicesException(Constants.objectExists,"Role with name: "+obj.getName()+" exists");
			Role role = new Role();
			role.setName(obj.getName());
			em.persist(role);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public void deleteRole(Long id) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Role role = em.find(Role.class, id);
			if (role==null)
				throw new ServicesException(Constants.objectIsNull, "Role with ID:"+ id+ " is NULL");
			em.remove(role);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public List<RoleWrapper> getRoleList(Boolean paged, Integer pageNum,
			Integer perPage) {
		EntityManager em = null;
		List<RoleWrapper> result = new ArrayList<>();
		try {
			em = emf.createEntityManager();
			Query query = em.createQuery("from Role r order by r.name");
			if (paged) {
				query = query.setFirstResult(perPage * (pageNum - 1))
						.setMaxResults(perPage);
			}
			List<Role> res = query.getResultList();
			for (Role r : res) {
				RoleWrapper rw = new RoleWrapper();
				rw.setId(r.getId());
				rw.setName(r.getName());
				result.add(rw);
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
	public void changeRoleProperty(Long id, RoleProperty propertyName, String newValue) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Role role = em.find(Role.class, id);
			if (role==null)
				throw new ServicesException(Constants.objectIsNull,"Role with ID:"+ id+ " is NULL");
			em.createQuery("update Role set "+propertyName.toString() +"=:value where id=:idValue")
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
	public void createUser(UserWrapper obj) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			List<User> users = em.createQuery("from User u where lower(u.login)=:login")
					.setParameter("login", obj.getLogin().toLowerCase().trim())
					.getResultList();
			if (users.size()>0)
				throw new ServicesException(Constants.objectExists, "user with login: "+obj.getLogin()+ " exists");
			User user = new User();
			user.setActive(true);
			user.setAvailableFromDate(new Date());
			user.setLogin(obj.getLogin());
			em.persist(user);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public void deleteUser(Long id) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			User user = em.find(User.class, id);
			if (user==null)
				throw new ServicesException(Constants.objectIsNull, "User with ID:"+ id+ " is NULL");
			em.remove(user);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public void changeUserProperty(Long id, UserProperty propertyName, String newValue) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			User user = em.find(User.class, id);
			if (user==null)
				throw new ServicesException(Constants.objectIsNull,"User with ID:"+ id+ " is NULL");
			em.createQuery("update User set "+propertyName.toString() +"=:value where id=:idValue")
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
	public List<UserWrapper> getUserList(Boolean paged, Integer pageNum,
			Integer perPage) {
		EntityManager em = null;
		List<UserWrapper> result = new ArrayList<>();
		try {
			em = emf.createEntityManager();
			Query query = em.createQuery("from User u order by u.lastName, u.name");
			if (paged) {
				query = query.setFirstResult(perPage * (pageNum - 1))
						.setMaxResults(perPage);
			}
			List<User> users = query.getResultList();
			for (User obj : users) {
				UserWrapper user = new UserWrapper();
				user.setId(obj.getId());
				user.setActive(obj.getActive());
				user.setAvailableFromDate(obj.getAvailableFromDate());
				user.setCellPhone(obj.getCellPhone());
				user.setEmail(obj.getEmail());
				user.setLastName(obj.getLastName());
				user.setLogin(obj.getLogin());
				user.setName(obj.getName());
				result.add(user);
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
	public List<UserWrapper> getUserListFiltered(Boolean paged,
			Integer pageNum, Integer perPage, String filter) {
		return this.getUserList(paged, pageNum, perPage);
	}

	@Override
	public void addRoleToUser(Long userId, Long roleId) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			User user = em.find(User.class, userId);
			if (user==null) 
				throw new ServicesException(Constants.objectIsNull,"User with ID:"+ userId+ " is NULL");
			Role role = em.find(Role.class, roleId);
			if (role==null)
				throw new ServicesException(Constants.objectIsNull,"Role with ID:"+ roleId+ " is NULL");
			if (user.getRoles().contains(role))
				throw new ServicesException(Constants.objectExists,"User already has this Role");
			user.getRoles().add(role);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public void removeRoleFromUser(Long userId, Long roleId) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			User user = em.find(User.class, userId);
			if (user==null) 
				throw new ServicesException(Constants.objectIsNull,"User with ID:"+ userId+ " is NULL");
			Role role = em.find(Role.class, roleId);
			if (role==null)
				throw new ServicesException(Constants.objectIsNull,"Role with ID:"+ roleId+ " is NULL");
			if (!user.getRoles().contains(role))
				throw new ServicesException(Constants.objectIsEmpty,"User already has not this Role");
			user.getRoles().remove(role);
		}
		finally {
			if (em!=null)
				if (em.isOpen())
					em.close();
		}
	}

	@Override
	public List<RoleWrapper> getUserRolesList(Long id) {
		EntityManager em = null;
		List<RoleWrapper> result = new ArrayList<>();
		try {
			em = emf.createEntityManager();
			User user = em.find(User.class, id);
			if (user==null) 
				throw new ServicesException(Constants.objectIsNull,"User with ID:"+ id+ " is NULL");
			List<Role> roles = new ArrayList<>();
			roles.addAll(user.getRoles());
			for (Role r : roles) {
				RoleWrapper rw = new RoleWrapper();
				rw.setId(r.getId());
				rw.setName(r.getName());
				result.add(rw);
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
