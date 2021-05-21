package inf.puc.rio.opus.composite.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CompositeGroup {

	
	private Set<String> group;
	private List<CompositeEffectDTO> composites;
	public String groupId;
	public String groupName;

	@JsonCreator
	public CompositeGroup(@JsonProperty("groupSet") Set<String> groupSet,
						  @JsonProperty("composites") List<CompositeEffectDTO> composites,
						  @JsonProperty("groupId") String groupId,
						  @JsonProperty("groupName") String groupName){
		this.group = groupSet;
		this.composites = composites;
		this.groupId = groupId;
		this.groupName = groupName;
	}
	
	public CompositeGroup(Set<String> group) {
		
		if(this.group == null) {
			
			this.group = group;
		}
		
		composites = new ArrayList<CompositeEffectDTO>();
	}

	public CompositeGroup(String groupName, String groupId){
		this.groupId = groupId;
		this.groupName = groupName;
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
