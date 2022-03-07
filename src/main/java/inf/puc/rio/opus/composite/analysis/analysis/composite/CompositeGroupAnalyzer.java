package inf.puc.rio.opus.composite.analysis.analysis.composite;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import inf.puc.rio.opus.composite.analysis.analysis.refactoring.RefactoringAnalyzer;
import inf.puc.rio.opus.composite.analysis.utils.CompositeUtils;
import inf.puc.rio.opus.composite.analysis.utils.CsvWriter;
import inf.puc.rio.opus.composite.model.effect.CodeSmellDTO;
import inf.puc.rio.opus.composite.model.effect.CompositeDTO;
import inf.puc.rio.opus.composite.model.group.CompositeGroup;
import inf.puc.rio.opus.composite.model.RefactoringTypesEnum;

public class CompositeGroupAnalyzer {

	public static void main(String[] args) {

		CompositeGroupAnalyzer groupAnalyzer = new CompositeGroupAnalyzer();

		List<CompositeGroup> summarizedGroups = groupAnalyzer.getCompositeGroupFromJson("summarized-groups.json");

		Set<String> projects = groupAnalyzer.getProjectsSetFromSummarizedGroups(summarizedGroups);

		int countCompositesOfSummmarizedGroups = groupAnalyzer.countCompositesFromSummarizedGroups(summarizedGroups);

	}
	
	public Map<String, List<CompositeDTO>> createCompositeGroups(List<CompositeDTO> composites){
		
		Map<String, List<CompositeDTO>> groups = new HashMap<String, List<CompositeDTO>>();
		
		System.out.println(composites.size());
		
		for(CompositeDTO composite : composites) {
			
			List<String> refs = CompositeUtils.convertRefactoringsTextToRefactoringsList(composite.getRefactoringsAsText());

			Collections.sort(refs);
			
			String groupId = refs.toString();
			
			if(groups.containsKey(groupId)) {
				 
				groups.get(groupId).add(composite);
			}
			else {
				
				groups.put(groupId, new ArrayList<CompositeDTO>());
				groups.get(groupId).add(composite);
			}
					
		}
		
		
		
		return groups;
	}

	public Map<String, List<CompositeDTO>> createCompositeGroupsFromRefactoringAsList(List<CompositeDTO> composites){

		Map<String, List<CompositeDTO>> groups = new HashMap<String, List<CompositeDTO>>();

		System.out.println(composites.size());

		for(CompositeDTO composite : composites) {

			List<String> refs = composite.getRefactoringNamesAsList();

			String groupId = refs.toString();

			if(groups.containsKey(groupId)) {

				groups.get(groupId).add(composite);
			}
			else {

				groups.put(groupId, new ArrayList<CompositeDTO>());
				groups.get(groupId).add(composite);
			}

		}

		return groups;
	}


	public List<CompositeGroup> getCompositeGroupFromJson(String compositeGroupPath){
		ObjectMapper mapper = new ObjectMapper();
		List<CompositeGroup> compositeList = new ArrayList<>();
		try {

			CompositeGroup[] composites = mapper.readValue(new File(compositeGroupPath),
					CompositeGroup[].class);

			compositeList = new ArrayList<CompositeGroup>(Arrays.asList(composites));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return compositeList;
	}
	
	
	public List<CompositeGroup> summarizeGroups(Map<String, List<CompositeDTO>> groups){

		List<CompositeGroup> summarizedGroups = new ArrayList<CompositeGroup>();
        final int[] refactoringsQuantity = {0};

		groups.entrySet().forEach(group -> {
			refactoringsQuantity[0] += group.getValue().size();
			String refactorings = group.getKey();

            refactorings = refactorings.replace("[", "");
            refactorings  = refactorings.replace("'", "");
            refactorings =  refactorings.replace("]", "");
			refactorings =  refactorings.replace("\"", "");
			
			List<String> refList = new ArrayList<String>(Arrays.asList(refactorings.split(",")));
			
			//All refs of one group 
			Set<String> groupSet = new HashSet<String>();
			for(String refType : refList) {
				
				for(RefactoringTypesEnum refTypeEnum: RefactoringTypesEnum.values()) {
					
					if(equalsToRefactoringTypes(refType, refTypeEnum)) {

						if(refTypeEnum.name().equals("REFACT_VARIABLE")){
							System.out.println("x");
						}
						groupSet.add(refTypeEnum.name().trim());
						
					}
					
				}
			}
			
			addSummarizedGroup(summarizedGroups, groupSet, group.getValue());
			
		});

		int refsGroup = 0;
        for (CompositeGroup E : summarizedGroups) {
            refsGroup += E.getComposites().size();
        }
        System.out.println("Size");
        System.out.println(refactoringsQuantity[0]);
        System.out.println(refsGroup);


		return summarizedGroups;
	}
	
	public void writeCompositeGroup(List<CompositeGroup> summarizedGroups, String path) {

		CsvWriter csv = new CsvWriter(path, ',', Charset.forName("ISO-8859-1"));


		try {
			csv.write("Group");
			csv.write("# Composites");
			csv.endRecord();

			for(CompositeGroup group : summarizedGroups) {

				List<String> compositeGroupList = new ArrayList<String>(group.getGroupSet());
				Collections.sort(compositeGroupList);
				csv.write(compositeGroupList.toString());
				csv.write(String.valueOf(group.getComposites().size()));
				csv.endRecord();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		csv.close();


	}

	public Map<String, Set<CodeSmellDTO>> getEffectByGroup(List<CompositeGroup> summarizedGroup, String groupName, String groupID){

	    Map<String, Set<CodeSmellDTO>> effectByGroup = new HashMap<String, Set<CodeSmellDTO>>();
        Map<String, CodeSmellDTO> effect = null;

	    for(CompositeGroup group : summarizedGroup){

            List<String> compositeGroupList = new ArrayList<String>(group.getGroupSet());
            Collections.sort(compositeGroupList);

	        if(compositeGroupList.toString().equals("[" + groupName + "]")) {

                if (effect == null){
                    effect = new HashMap<>();
                }
                for (CompositeDTO compositeDTO : group.getComposites()) {

                    for (CodeSmellDTO codeSmellDTO : compositeDTO.getCodeSmells()) {

                        if (!effect.containsKey(codeSmellDTO.getType())) {
                            CodeSmellDTO smell = new CodeSmellDTO();
                            effect.put(codeSmellDTO.getType(), new CodeSmellDTO());
                            effect.get(codeSmellDTO.getType()).setType(codeSmellDTO.getType());
                        }


                        int addedSmells = effect.get(codeSmellDTO.getType()).getAddedSmells() + codeSmellDTO.getAddedSmells();
                        effect.get(codeSmellDTO.getType()).setAddedSmells(addedSmells);

                        int removedSmells = effect.get(codeSmellDTO.getType()).getRemovedSmells() + codeSmellDTO.getRemovedSmells();
                        effect.get(codeSmellDTO.getType()).setRemovedSmells(removedSmells);

                        int notAffectedSmells = effect.get(codeSmellDTO.getType()).getNotAffectSmells() + codeSmellDTO.getNotAffectSmells();
                        effect.get(codeSmellDTO.getType()).setNotAffectSmells(notAffectedSmells);
                    }
                }

                Set<CodeSmellDTO> smellSet = new HashSet<>(effect.values());

                effectByGroup.put(groupID,smellSet);
            }
        }

	    return effectByGroup;

    }
    
    public void writeEffectByGroup(Map<String, Set<CodeSmellDTO>> effectByGroup, String groupId){

        CsvWriter csv = new CsvWriter("effect-by-group-" + groupId +"-composites.csv", ',',
                Charset.forName("ISO-8859-1"));

        try {
            csv.write("Code Smell Type");
            csv.write("Added");
            csv.write("Removed");
            csv.write("Not Affected");
            csv.endRecord();

            if(effectByGroup.get(groupId) != null){

				for (CodeSmellDTO  codeSmellDTO: effectByGroup.get(groupId)) {
					csv.write(codeSmellDTO.getType());

					csv.write(String.valueOf(codeSmellDTO.getAddedSmells()));
					csv.write(String.valueOf(codeSmellDTO.getRemovedSmells()));
					csv.write(String.valueOf(codeSmellDTO.getNotAffectSmells()));

					csv.endRecord();
				}
			}



        } catch (IOException e) {
            e.printStackTrace();
        }

        csv.close();

    }
	
	private void addSummarizedGroup(List<CompositeGroup> summarizedGroups,
										   Set<String> groupSet, 
										   List<CompositeDTO> groupComposite) {
		
		List<String> groupList = new ArrayList<String>(groupSet);
		Collections.sort(groupList);
		
		boolean addedGroup = false; 
		for(CompositeGroup group : summarizedGroups) {
			
			List<String> summarizedGroupList = new ArrayList<String>(group.getGroupSet());
			Collections.sort(summarizedGroupList);
			
		    if(groupList.equals(summarizedGroupList)) {
                addedGroup =  true;
		    	group.addComposites(groupComposite);

		    }
		}
		
		if(! addedGroup) {
			CompositeGroup summarizedGroup = new CompositeGroup(groupSet);
			
			summarizedGroup.addComposites(groupComposite);
			
			summarizedGroups.add(summarizedGroup);
		}
		
		
	}
	
	private boolean equalsToRefactoringTypes(String refType, RefactoringTypesEnum refTypeEnum) {
		
		List<String> refTypesList = new ArrayList<String>(Arrays.asList(refTypeEnum.toString().split(",")));
		
		for(String refTypeAsText : refTypesList) {
		
			if(refType.trim().equals(refTypeAsText.trim())) {
				return true;
			}
		}
		
		return false;
	}


	public List<CompositeDTO> getRefactoringsNPS(List<CompositeDTO> composites) {

		RefactoringAnalyzer refAnalyzer = new RefactoringAnalyzer();

		for (int i=0; i < composites.size(); i++){

			List<String> refactorings = new ArrayList<>();
				//	refAnalyzer.getRefactoringsFromRefMiner("C:\\Users\\anaca\\" + composites.get(i).getProject(), composites.get(i).getCurrentCommit());

			if(refactorings != null && refactorings.size() > 1){
				composites.get(i).setRefactoringsAsText(refactorings.toString());
			}

		}
		return composites;
	}

	private Set<String> getProjectsSetFromSummarizedGroups(List<CompositeGroup> summarizedGroups){

		Set<String> projects = new HashSet<>();

		for(CompositeGroup summarizedGroup : summarizedGroups){

			for(CompositeDTO compositeEffectDTO: summarizedGroup.getComposites()){

				projects.add(compositeEffectDTO.getProject());
			}

		}

		return projects;
	}

	private int countCompositesFromSummarizedGroups(List<CompositeGroup> summarizedGroups){

		List<CompositeDTO> allCompositeEffectDTOS = new ArrayList<>();
		for(CompositeGroup summarizedGroup : summarizedGroups){
			allCompositeEffectDTOS.addAll(summarizedGroup.getComposites());
		}

		return allCompositeEffectDTOS.size();
	}

	public List<CompositeDTO> gettAllCompositesFromSpecificGroups(List<String> groupNames, List<CompositeGroup> summarizedGroups){

		List<CompositeDTO>  compositeEffectDTOS = new ArrayList<>();

		for(String groupName: groupNames) {

			int index = 0;
			for (CompositeGroup summarizedGroup : summarizedGroups) {
				index ++;

				List<String> groupList = new ArrayList<>(summarizedGroup.getGroupSet());
				Collections.sort(groupList);
                String groupListText = groupList.toString();

				if (groupListText.equals(groupName)) {
					compositeEffectDTOS.addAll(summarizedGroup.getComposites());
				}

			}
		}

        return compositeEffectDTOS;
	}

	private List<CompositeGroup> getSpecificGroups(List<String> groupNames, List<CompositeGroup> summarizedGroups){

		List<CompositeGroup> specificSummarizedGroups = new ArrayList<>();

		for(String groupName : groupNames){
			for(CompositeGroup summarizedGroup : summarizedGroups){
				//List<String> compositeGroupList = new ArrayList<String>(group.getGroupSet());
				//Collections.sort(compositeGroupList);

				if(summarizedGroup.getGroupSet().toString().equals(groupName)){
					specificSummarizedGroups.add(summarizedGroup);
				}

			}
		}
		return specificSummarizedGroups;

	}


}