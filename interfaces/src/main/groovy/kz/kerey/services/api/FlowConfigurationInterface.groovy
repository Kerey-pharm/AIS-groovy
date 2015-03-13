package kz.kerey.services.api

import kz.kerey.business.types.enums.LadderProperty
import kz.kerey.business.types.enums.StepProperty
import kz.kerey.business.wrappers.LadderWrapper
import kz.kerey.business.wrappers.StepWrapper;

import java.util.List;

public interface FlowConfigurationInterface {

	void createLadder(LadderWrapper ladder)
	void deleteLadder(Long id)
	List<LadderWrapper> getLadderList(Boolean paged, Integer pageNum, Integer perPage)
	void changeLadderProperty(Long id, LadderProperty property, String newValue)
	
	void createStep(StepWrapper step)
	void deleteStep(Long ladder, Long id)
	void changeStepProperty(Long id, StepProperty property, String newValue)

	List<StepWrapper> getLadderSteps(Long id)
	void swapLadderSteps(Long ladder, Long left, Long right)
	
}
