package inf.puc.rio.opus.composite.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CompositeGroup {

	
	private Set<String> group;
	private List<CompositeEffectDTO> composites;
	

	
	public CompositeGroup(Set<String> group) {
		
		if(this.group == null) {
			
			this.group = group;
		}
		
		composites = new ArrayList<CompositeEffectDTO>();
	}

	public void addComposites(List<CompositeEffectDTO> composites) {
		
		this.composites.addAll(composites);
	}

	public Set<String> getGroupSet() {
		return group;
	}

	public List<CompositeEffectDTO> getComposites() {
		return composites;
	}
	
	
	
	
	
	
}
