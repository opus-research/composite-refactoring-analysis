package inf.puc.rio.opus.composite.model.group;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import inf.puc.rio.opus.composite.model.effect.CompositeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CompositeGroup {

	
	private Set<String> group;
	private List<CompositeDTO> composites;
	public String groupId;
	public String groupName;

	@JsonCreator
	public CompositeGroup(@JsonProperty("groupSet") Set<String> groupSet,
						  @JsonProperty("composites") List<CompositeDTO> composites,
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
		
		composites = new ArrayList<CompositeDTO>();
	}

	public CompositeGroup(String groupName, String groupId){
		this.groupId = groupId;
		this.groupName = groupName;
	}


	public void addComposites(List<CompositeDTO> composites) {
		
		this.composites.addAll(composites);
	}

	public Set<String> getGroupSet() {
		return group;
	}

	public List<CompositeDTO> getComposites() {
		return composites;
	}
	
	
	
	
	
	
}
