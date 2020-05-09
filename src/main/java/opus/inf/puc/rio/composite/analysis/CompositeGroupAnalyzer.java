package opus.inf.puc.rio.composite.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import inf.puc.rio.opus.composite.model.CompositeEffectDTO;
import inf.puc.rio.opus.composite.model.CompositeGroup;
import inf.puc.rio.opus.composite.model.RefactoringTypesEnum;

public class CompositeGroupAnalyzer {
	
	
	private List<CompositeGroup> summarizeGroups(Map<String, List<CompositeEffectDTO>> groups){
		
		List<CompositeGroup> summarizedGroups = new ArrayList<CompositeGroup>();
		
		groups.entrySet().forEach(group -> {
							
			String refactorings = group.getKey();
			
			List<String> refList = new ArrayList<String>(Arrays.asList(refactorings.split(",")));
			
			//All refs of one group 
			Set<String> groupSet = new HashSet<String>();
			for(String refType : refList) {
				
				for(RefactoringTypesEnum refTypeEnum: RefactoringTypesEnum.values()) {
					
					if(equalsToRefactoringTypes(refType, refTypeEnum)) {
							
						groupSet.add(refTypeEnum.name());
						
					}
					
				}
			}
			
			if(! containsSummarizedGroup(summarizedGroups, groupSet, group.getValue())) {
				
				CompositeGroup summarizedGroup = new CompositeGroup(groupSet);
				
				summarizedGroup.addComposites(group.getValue());
				
				summarizedGroups.add(summarizedGroup);
			}
			
			
			
			
			
		});
		
		return summarizedGroups;
	}
	
	private void writeCompositeGroup(List<CompositeGroup> summarizedGroups) {
		
		for(CompositeGroup group : summarizedGroups) {
			
			System.out.println(group.getGroupSet());
			System.out.println(group.getComposites().size());
		}
	}
	
	private boolean containsSummarizedGroup(List<CompositeGroup> summarizedGroups,
										   Set<String> groupSet, 
										   List<CompositeEffectDTO> groupComposite) {
		
		List<String> groupList = new ArrayList<String>(groupSet);
		Collections.sort(groupList);
		
		for(CompositeGroup group : summarizedGroups) {
			
			List<String> summarizedGroupList = new ArrayList<String>(group.getGroupSet());
			Collections.sort(summarizedGroupList);
			
		    if(groupList.equals(summarizedGroupList)) {
		    	
		    	group.addComposites(groupComposite);
		    	return true;
		    }
		}
		
		return false;
	}
	
	private boolean equalsToRefactoringTypes(String refType, RefactoringTypesEnum refTypeEnum) {
		
		List<String> refTypesList = new ArrayList<String>(Arrays.asList(refTypeEnum.toString().split(",")));
		
		for(String refTypeAsText : refTypesList) {
		
			if(refType.equals(refTypeAsText)) {
				return true;
			}
		}
		
		return false;
		
		
	}
	

}
