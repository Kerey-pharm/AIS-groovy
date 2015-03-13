package kz.kerey.services.api

import kz.kerey.business.types.PageParam
import kz.kerey.business.types.enums.TaskProperty
import kz.kerey.business.types.enums.TaskStatus
import kz.kerey.business.wrappers.TaskWrapper

import java.util.Date
import java.util.List

interface FlowInterface {

	void createTask(TaskWrapper task)
	void deleteTask(Long id)
	void changeTaskProperty(Long id, TaskProperty property, String value)
	void changeTaskDeadlineDate(Long id, Date date)
	void changeTaskStatus(Long id, TaskStatus status)
	List<TaskWrapper> getTaskList(PageParam param)
	List<TaskWrapper> getTaskFiltered(PageParam param, Long userId, TaskStatus status)
	
	void nextStep(Long taskId)
	void previousStep(Long taskId)

    void assignTask(Long taskId, Long userId)
	
}