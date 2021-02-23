package opus.inf.puc.rio.composite.analysis;

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

import inf.puc.rio.opus.composite.model.CodeSmellDTO;
import inf.puc.rio.opus.composite.model.CompositeEffectDTO;
import inf.puc.rio.opus.composite.model.CompositeGroup;
import inf.puc.rio.opus.composite.model.RefactoringTypesEnum;
import opus.inf.puc.rio.composite.utils.CompositeUtils;

public class CompositeGroupAnalyzer {
	
	
	public Map<String, List<CompositeEffectDTO>> createCompositeGroups(List<CompositeEffectDTO> composites){
		
		Map<String, List<CompositeEffectDTO>> groups = new HashMap<String, List<CompositeEffectDTO>>();
		
		System.out.println(composites.size());
		
		for(CompositeEffectDTO composite : composites) {
			
			List<String> refs = CompositeUtils.convertRefactoringsTextToRefactoringsList(composite.getRefactorings());

			Collections.sort(refs);
			
			String groupId = refs.toString();
			
			if(groups.containsKey(groupId)) {
				 
				groups.get(groupId).add(composite);
			}
			else {
				
				groups.put(groupId, new ArrayList<CompositeEffectDTO>());
				groups.get(groupId).add(composite);
			}
					
		}
		
		
		
		return groups;
	}
	
	
	
	public List<CompositeGroup> summarizeGroups(Map<String, List<CompositeEffectDTO>> groups){

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
                for (CompositeEffectDTO compositeDTO : group.getComposites()) {

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
										   List<CompositeEffectDTO> groupComposite) {
		
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


	public List<CompositeEffectDTO> getRefactoringsNPS(List<CompositeEffectDTO> composites) {

		RefactoringAnalyzer refAnalyzer = new RefactoringAnalyzer();

		for (int i=0; i < composites.size(); i++){

			List<String> refactorings = refAnalyzer
					.getRefactoringsFromRefMiner("C:\\Users\\anaca\\" + composites.get(i).getProject(), composites.get(i).getCurrentCommit());

			if(refactorings != null && refactorings.size() > 1){
				composites.get(i).setRefactorings(refactorings.toString());
			}

		}
		return composites;
	}
}
