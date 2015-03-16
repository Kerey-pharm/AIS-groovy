package kz.kerey.services.ejb.impl

import javax.annotation.Resource
import javax.ejb.Local
import javax.ejb.Singleton

import kz.kerey.business.types.enums.RoleProperty
import kz.kerey.business.types.enums.UserProperty
import kz.kerey.business.user.Role
import kz.kerey.business.user.User
import kz.kerey.business.wrappers.RoleWrapper
import kz.kerey.business.wrappers.UserWrapper
import kz.kerey.constants.Constants
import kz.kerey.exceptions.ServicesException

@Singleton
@Local(UserEJB.class)
class UserEJB {

	@Resource(mappedName = "java:jboss/drugstoreEntityManagerFactory")
	def emf

	void createRole(RoleWrapper obj) {
		def em
		try {
			em = emf.createEntityManager()
			def roles = em.createQuery("from Role r where lower(r.name)=:text1")
					.setParameter("text1", obj.name.toLowerCase().trim())
					.getResultList()
			if (roles.size()>0)
				throw new ServicesException(Constants.objectExists,"Role with name: ${obj.name} exists")
			em.persist(new Role(name: obj.name))
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	void deleteRole(Long id) {
		def em
		try {
			em = emf.createEntityManager()
			def role = em.find(Role.class, id)
			if (role==null)
				throw new ServicesException(Constants.objectIsNull, "Role with ID:${id} is NULL")
			em.remove(role)
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	List<RoleWrapper> getRoleList(Boolean paged, Integer pageNum, Integer perPage) {
		def em
		def result = new ArrayList<RoleWrapper>()
		try {
			em = emf.createEntityManager()
			def query = em.createQuery("from Role r order by r.name")
			if (paged) {
				query = query.setFirstResult(perPage * (pageNum - 1))
						.setMaxResults(perPage)
			}
			def res = query.getResultList()
			for (Role r in res) {
				result.add(new RoleWrapper(id: r.id, name: r.name))
			}
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
		result
	}

	void changeRoleProperty(Long id, RoleProperty propertyName, String newValue) {
		def em
		try {
			em = emf.createEntityManager()
			def role = em.find(Role.class, id)
			if (role==null)
				throw new ServicesException(Constants.objectIsNull,"Role with ID:${id} is NULL")
			em.createQuery("update Role set ${propertyName.toString()}=:value where id=:idValue")
					.setParameter("value", newValue)
					.setParameter("idValue", id)
					.executeUpdate()
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	void createUser(UserWrapper obj) {
		def em
		try {
			em = emf.createEntityManager()
			def users = em.createQuery("from User u where lower(u.login)=:login")
					.setParameter("login", obj.getLogin().toLowerCase().trim())
					.getResultList()
			if (users.size()>0)
				throw new ServicesException(Constants.objectExists, "user with login: ${obj.login} exists")
			em.persist(new User(active: true, availableFromDate: new Date(), login: obj.login))
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	void deleteUser(Long id) {
		def em
		try {
			em = emf.createEntityManager()
			def user = em.find(User.class, id)
			if (user==null)
				throw new ServicesException(Constants.objectIsNull, "User with ID:${id} is NULL")
			em.remove(user)
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	void changeUserProperty(Long id, UserProperty propertyName, String newValue) {
		def em
		try {
			em = emf.createEntityManager()
			def user = em.find(User.class, id)
			if (user==null)
				throw new ServicesException(Constants.objectIsNull,"User with ID:${id} is NULL")
			em.createQuery("update User set ${propertyName.toString()}=:value where id=:idValue")
					.setParameter("value", newValue)
					.setParameter("idValue", id)
					.executeUpdate()
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	List<UserWrapper> getUserList(Boolean paged, Integer pageNum, Integer perPage) {
		def em
		def result = new ArrayList<UserWrapper>()
		try {
			em = emf.createEntityManager()
			def query = em.createQuery("from User u order by u.lastName, u.name")
			if (paged) {
				query = query.setFirstResult(perPage * (pageNum - 1))
						.setMaxResults(perPage)
			}
			def users = query.getResultList()
			for (User obj : users) {
				result.add(new UserWrapper(
                        id: obj.id,
                        active: obj.active,
                        availableFromDate: obj.availableFromDate,
                        cellPhone: obj.cellPhone,
                        email: obj.email,
                        lastName: obj.lastName,
                        login: obj.login,
                        name: obj.name
                ))
			}
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
		result
	}

	List<UserWrapper> getUserListFiltered(Boolean paged, Integer pageNum, Integer perPage, String filter) {
		this.getUserList(paged, pageNum, perPage)
	}

	void addRoleToUser(Long userId, Long roleId) {
		def em
		try {
			em = emf.createEntityManager()
			def user = em.find(User.class, userId)
			if (user==null) 
				throw new ServicesException(Constants.objectIsNull,"User with ID:${userId} is NULL")
			def role = em.find(Role.class, roleId)
			if (role==null)
				throw new ServicesException(Constants.objectIsNull,"Role with ID:${roleId} is NULL")
			if (user.roles.contains(role))
				throw new ServicesException(Constants.objectExists,"User already has this Role")
			user.roles.add(role)
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	void removeRoleFromUser(Long userId, Long roleId) {
		def em
		try {
			em = emf.createEntityManager()
			def user = em.find(User.class, userId)
			if (user==null) 
				throw new ServicesException(Constants.objectIsNull,"User with ID:${userId} is NULL")
			def role = em.find(Role.class, roleId)
			if (role==null)
				throw new ServicesException(Constants.objectIsNull,"Role with ID:${roleId} is NULL")
			if (!user.roles.contains(role))
				throw new ServicesException(Constants.objectIsEmpty,"User already has not this Role")
			user.roles.remove(role)
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
	}

	List<RoleWrapper> getUserRolesList(Long id) {
		def em
		def result = new ArrayList<RoleWrapper>()
		try {
			em = emf.createEntityManager()
			def user = em.find(User.class, id)
			if (user==null) 
				throw new ServicesException(Constants.objectIsNull,"User with ID:${id} is NULL")
			def roles = new ArrayList<Role>()
			roles.addAll(user.roles)
			for (Role r in roles) {
				result.add(new RoleWrapper(id: r.id, name: r.name))
			}
		}
		finally {
			if (em!=null && em.isOpen())
				em.close()
		}
		result
	}
	
}