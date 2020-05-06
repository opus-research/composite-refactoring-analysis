package inf.puc.rio.opus.composite.model;

import java.util.List;
import java.util.Set;

public class CompositeGroup {

	
	private Set<RefactoringTypesEnum> group;
	private int numberOfComposites;
	private List<CompositeEffectDTO> composites;
	

	
	public void addGroup(Set<RefactoringTypesEnum> group) {
		
		if(this.group == null) {
			
			this.group = group;
		}
	}

	public void addNumberOfComposites(int composites) {
		
		this.numberOfComposites = this.numberOfComposites + composites;
	}
	
	
	
}
