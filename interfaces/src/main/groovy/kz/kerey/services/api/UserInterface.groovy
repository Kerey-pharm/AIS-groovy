package kz.kerey.services.api

import java.util.List

import kz.kerey.business.types.enums.RoleProperty
import kz.kerey.business.types.enums.UserProperty
import kz.kerey.business.wrappers.RoleWrapper
import kz.kerey.business.wrappers.UserWrapper

interface UserInterface {

	void createRole(RoleWrapper obj)
	void deleteRole(Long id)
	List<RoleWrapper> getRoleList(Boolean paged, Integer pageNum, Integer perPage)
	void changeRoleProperty(Long id, RoleProperty propertyName, String newValue)
	
	void createUser(UserWrapper obj)
	void deleteUser(Long id)
	List<UserWrapper> getUserList(Boolean paged, Integer pageNum, Integer perPage)
	List<UserWrapper> getUserListFiltered(Boolean paged, Integer pageNum, Integer perPage, String filter)
	void changeUserProperty(Long id, UserProperty propertyName, String newValue)
	
	void addRoleToUser(Long userId, Long roleId)
	void removeRoleFromUser(Long userId, Long roleId)
	List<RoleWrapper> getUserRolesList(Long id)
	
}