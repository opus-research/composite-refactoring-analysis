package opus.inf.puc.rio.composite.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import inf.puc.rio.opus.composite.model.CodeElement;
import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.Refactoring;
import inf.puc.rio.opus.composite.model.RefactoringTypesEnum;
import inf.puc.rio.opus.composite.model.dto.single.refactoring.CodeElementDTO;
import inf.puc.rio.opus.composite.model.dto.single.refactoring.SingleRefactoringDTO;
import inf.puc.rio.opus.composite.model.dto.single.refactoring.refMiner.output.CommitOutput;
import inf.puc.rio.opus.composite.model.dto.single.refactoring.refMiner.output.RefInfo;
import inf.puc.rio.opus.composite.model.dto.single.refactoring.refMiner.output.RefactoringOutput;

public class RefactoringAnalyzer {

	public static void main(String[] args) throws IOException {

		RefactoringAnalyzer analyzer = new RefactoringAnalyzer();

		//analyzer.getRefactoringsFromRefMiner("C:\\Users\\anaca\\Executaveis\\RefactoringMiner-2.0.0\\"
		//		+ "RefactoringMiner-2.0.0\\build\\distributions\\RefactoringMiner-1.0\\"
		//		+ "RefactoringMiner-1.0\\bin\\refactoring-toy-example",
		//		"36287f7c3b09eff78395267a3ac0d7da067863fd");
	}



	private void writeGroupsOfRefactoredClasses(Map<String, Set<SingleRefactoringDTO>> groupsOfRefactoredClasses) {
		// TODO Auto-generated method stub
		CsvWriter csv = new CsvWriter("groupsOfRefactoredClasses-presto.csv", ',', Charset.forName("ISO-8859-1"));
		try {
			csv.write("Class");
			csv.write("Number of Refactorings");
			csv.write("Refactoring ID");
			csv.write("Refactoring Type");
			csv.write("Commit");
			csv.endRecord();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		groupsOfRefactoredClasses.entrySet().forEach(refClass -> {

			try {
				
				
				Set<SingleRefactoringDTO> refsSet = groupsOfRefactoredClasses.get(refClass.getKey());
				for (SingleRefactoringDTO ref : refsSet) {
					
					// System.out.println(ref.getRefactoringType());
					
								//Refactored Class
								csv.write(refClass.getKey());
								
								
								//Number of Refactorings 
								int numberOfRefs = refsSet.size();
								csv.write(String.valueOf(numberOfRefs));
								
								//Refactoring ID 
								csv.write(ref.getRefactoringId());
								
								//Refactoring Type
								csv.write(ref.getRefactoringType());
								
								//Commit
								csv.write(ref.getCurrentCommit());
								csv.endRecord();
									
				}
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		csv.close();
		
	}
	
	
	
	public void writeGroupsOfRefactoredClassesByCommits(Map<String, Set<SingleRefactoringDTO>> groupsOfRefactoredClasses) {
		// TODO Auto-generated method stub
		   String commits = "27c35db739b0146b2a5e96314d1165517a10a256, 5149d92be7295862532a7e4dc2db38641294e94e, "
		   		+ "dd7f571d2d27c049384c14d72fdf3cbdef346c0d, 3379b4f4979bb482bd185774207ed31fcde2da3d, "
		   		+ "2376361ed289157843fd8c20a24832a29be15013, 4705a17147b325f4933066652403009bf80e0e70, "
		   		+ "d8b8a0d733be4c64e3afa73c17ee05d1ccb7dd42, 57b570494bd8348d76af6c8a4d5384435b3ad18f,"
		   		+ " 997e8aa4cd2fbf9821285b60d03b10e971b90398";

		   List<String> commitList = new ArrayList<String>(Arrays.asList(commits.split(",")));
		   CsvWriter csv = new CsvWriter("groupsOfRefactoredClasses-presto-by-commits.csv", ',', Charset.forName("ISO-8859-1"));
		   
		   try {
				csv.write("Class");
				csv.write("Number of Refactorings");
				csv.write("Refactoring ID");
				csv.write("Refactoring Type");
				csv.write("Commit");
				csv.endRecord();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			for(String commit : commitList) {
				
				System.out.println(commit);
				groupsOfRefactoredClasses.entrySet().forEach(refClass -> {
	
					try {
						
						
						Set<SingleRefactoringDTO> refsSet = groupsOfRefactoredClasses.get(refClass.getKey());
						
						for (SingleRefactoringDTO ref : refsSet) {
						
							if(ref.getCurrentCommit().equals(commit.trim())) {
								
								// System.out.println(ref.getRefactoringType());
								
								//Refactored Class
								csv.write(refClass.getKey());
								
								//Number of Refactorings 
								int numberOfRefs = refsSet.size();
								csv.write(String.valueOf(numberOfRefs));
								
								//Refactoring ID 
								csv.write(ref.getRefactoringId());
								
								//Refactoring Type
								csv.write(ref.getRefactoringType());
								
								//Commit
								csv.write(ref.getCurrentCommit());
								csv.endRecord();
										
							}			
											
						}
						
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	
				});
			}

			csv.close();
	}

	public List<String> getRefactoringsFromRefMiner(String pathProject, String commit){

		String resultRefMiner = executeRefMiner(pathProject, commit);
		List<String> refactoringsList = new ArrayList<>();
		//convert string to json
		//get refactorings from json

		ObjectMapper mapper = new ObjectMapper();

		try {

			RefInfo refInfo = mapper.readValue(resultRefMiner, new TypeReference<RefInfo>(){});
			List<CommitOutput> commitOutputs = refInfo.getCommits();

			for(CommitOutput commitOutput : commitOutputs){

				if(commitOutput.getRefactorings() != null && commitOutput.getRefactorings().size() > 1) {
					for (RefactoringOutput refactoringOutput : commitOutput.getRefactorings()) {

						if(Refactoring.equalsToRefactoringType(refactoringOutput.getType())){
							refactoringsList.add(refactoringOutput.getType());
						}

					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return refactoringsList;

	}
	public String executeRefMiner(String pathProject, String commit) {
		
		String REF_MINER_PATH = "C:\\Users\\anaca\\Executaveis\\RefactoringMiner-2.0.1\\RefactoringMiner-2.0.1\\"
				+ "build\\distributions\\RefactoringMiner-2.0.1\\RefactoringMiner-2.0.1\\bin";
		//"" && ./RefactoringMiner -c " + pathProject + " " + commit"
	
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd " + REF_MINER_PATH + 
				" && RefactoringMiner -c " + pathProject + " " + commit);
		builder.redirectErrorStream(true);
		String line = null;
		String refactorings = "";
		try {
			Process process = builder.start();
			InputStream is = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));


			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				refactorings+=line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		int jsonIndex = refactorings.indexOf("{");
		refactorings = refactorings.substring(jsonIndex);
		return refactorings;

	
	}

	private Map<String, Set<SingleRefactoringDTO>> getRefsByRefactoredClasses(
			Map<String, Long> rankingOfRefactoredClasses,
			List<SingleRefactoringDTO> refs) {
		// TODO Auto-generated method stub, 
		
		
		Map<String, Set<SingleRefactoringDTO>> refsByRefactoredClasses = new HashMap<String, 
				Set<SingleRefactoringDTO>>();
		
		for (Entry<String, Long> refactoreClass : rankingOfRefactoredClasses.entrySet()) {

			Set<SingleRefactoringDTO> refsList = new HashSet<SingleRefactoringDTO>();
			
			for (SingleRefactoringDTO ref : refs) {

				if (ref.getElements() != null) {
					for (CodeElementDTO elem : ref.getElements()) {
						
						if (elem.getClassName() != null) {

							if (elem.getClassName().equals(refactoreClass.getKey())) {

								refsList.add(ref);
							}
						}
					}
				}
			}
			
			refsByRefactoredClasses.put(refactoreClass.getKey(), refsList);
			
		}
		
		return refsByRefactoredClasses;
		
	
	}

	private List<SingleRefactoringDTO> getRefactoringsFromJson(String jsonPath) {

		List<SingleRefactoringDTO> refsList = new ArrayList<SingleRefactoringDTO>();
		ObjectMapper mapper = new ObjectMapper();

		try {

			SingleRefactoringDTO[] refs = mapper.readValue(new File(jsonPath),
					SingleRefactoringDTO[].class);

			refsList = Arrays.asList(refs);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return refsList;

	}



	private Map<String, Set<SingleRefactoringDTO>> getGroupsOfRefactoredClasses(List<SingleRefactoringDTO> refs) {

		Map<String, Set<SingleRefactoringDTO>> groupsOfRefactoredClasses = 
				new HashMap<String, Set<SingleRefactoringDTO>>();
		
		
		for (SingleRefactoringDTO ref : refs) {

			if (ref.getElements() != null) {
				
				for (CodeElementDTO elem : ref.getElements()) {

					if (elem.getClassName() != null) {

						if (!elem.getClassName().isEmpty()) {
							
							if(groupsOfRefactoredClasses.containsKey(elem.getClassName())) {
								
								groupsOfRefactoredClasses.get(elem.getClassName()).add(ref);
								
							}else {
								groupsOfRefactoredClasses.put(elem.getClassName(), new HashSet<SingleRefactoringDTO>());
								groupsOfRefactoredClasses.get(elem.getClassName()).add(ref);
							}
							
						}
					}
				}
			}

		}

		return groupsOfRefactoredClasses;

	}



	

}
