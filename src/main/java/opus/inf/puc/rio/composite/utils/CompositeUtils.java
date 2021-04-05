package opus.inf.puc.rio.composite.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import inf.puc.rio.opus.composite.model.CompositeRefactoring;

public class CompositeUtils {

	public static void main(String[] args) {

		//CompositeUtils.countElementsFromTextList(CompositeUtils.smellsValidacaoManualICSME21Before);
		//CompositeUtils.countElementsFromTextList(CompositeUtils.smellsValidacaoManualICSME21After);

		CompositeUtils compositeUtils = new CompositeUtils();
		List<CompositeRefactoring> composites = compositeUtils.filterCompositesByIds("fresco-compositesRangeBased.json","128,158,159,369,393,435,505,510,557,687,724,768");

		ObjectMapper mapper = new ObjectMapper();

		// Java object to JSON file
		try {
			mapper.writeValue(new File("composites-fresco-manual-validation.json"), composites);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static String refactoringsValidacaoManualICSME21 = "'Extract Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Extract Method', " +
			"'Extract Method', 'Extract Method', 'Extract Method', 'Inline Variable', 'Extract Method', 'Parameterize Variable'," +
			" 'Extract Method', 'Inline Method', 'Inline Method', 'Inline Method', 'Inline Method', 'Inline Method', 'Inline Method', " +
			"'Inline Method', 'Inline Method', 'Inline Method', 'Extract Variable', 'Inline Method', 'Rename Variable', 'Change Return Type', " +
			"'Extract Variable', 'Change Return Type', 'Change Return Type', 'Extract Variable', 'Change Return Type', 'Change Variable Type', 'Rename Parameter'," +
			" 'Pull Up Method', 'Rename Variable', 'Change Variable Type', 'Rename Variable', 'Change Return Type', 'Change Variable Type'," +
			" 'Inline Variable', 'Change Variable Type', 'Change Return Type', 'Rename Method', 'Rename Method', 'Change Return Type', " +
			"'Change Variable Type', 'Inline Variable', 'Rename Attribute', 'Rename Parameter', 'Rename Method', 'Rename Method', 'Pull Up Attribute', " +
			"'Pull Up Method', 'Pull Up Method', 'Change Return Type', 'Change Return Type', 'Rename Variable', 'Extract Method', 'Pull Up Method', " +
			"'Pull Up Attribute', 'Move Class', 'Change Parameter Type', 'Rename Parameter', 'Change Variable Type', 'Change Parameter Type', " +
			"'Rename Parameter', 'Rename Parameter', 'Change Variable Type', 'Rename Parameter', 'Rename Parameter', 'Rename Method', " +
			"'Change Return Type', 'Rename Method', 'Rename Method', 'Change Return Type', 'Extract Method', 'Rename Method', 'Change Attribute Type'," +
			" 'Change Return Type', 'Pull Up Attribute', 'Pull Up Attribute', 'Pull Up Attribute', 'Pull Up Attribute', 'Extract Superclass', 'Pull Up Method', " +
			"'Move Method', 'Move Method', 'Pull Up Attribute', 'Pull Up Attribute', 'Extract Method', 'Change Variable Type', 'Rename Variable', 'Rename Variable'," +
			" 'Extract Method', 'Change Variable Type', 'Rename Variable', 'Change Attribute Type', 'Rename Variable', 'Change Attribute Type', 'Inline Method', " +
			"'Rename Method', 'Rename Method', 'Change Variable Type', 'Change Return Type', 'Extract Method', 'Rename Method', 'Extract Variable', 'Inline Variable'," +
			" 'Change Return Type', 'Change Variable Type', 'Change Variable Type', 'Change Variable Type', 'Extract Method', 'Extract Method', 'Inline Variable', " +
			"'Parameterize Variable', 'Extract Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Move Attribute', 'Rename Variable', " +
			"'Change Variable Type', 'Rename Variable', 'Extract Method', 'Extract Method', 'Rename Method', 'Rename Variable', 'Change Variable Type', 'Extract Method', " +
			"'Extract Method', 'Extract Method', 'Rename Variable', 'Extract Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Rename Method', 'Change Variable Type', 'Extract Method', 'Extract Method', " +
			"'Extract Method', 'Rename Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Change Attribute Type', 'Change Return Type', 'Change Return Type', 'Extract Method', 'Change Attribute Type', " +
			"'Change Return Type', 'Change Attribute Type', 'Rename Variable', 'Change Attribute Type', 'Change Attribute Type', 'Change Return Type', 'Change Return Type', 'Extract Method', " +
			"'Change Variable Type', 'Extract Method', 'Rename Parameter', 'Rename Attribute', 'Rename Attribute', 'Rename Attribute', 'Move Attribute', 'Move Attribute', 'Extract Class', " +
			"'Change Return Type', 'Move Method', 'Move Method', 'Move Attribute', 'Move Attribute', 'Move Attribute', 'Change Attribute Type', 'Move Method', 'Move Attribute', 'Rename Attribute', " +
			"'Extract Class', 'Change Return Type', 'Rename Attribute', 'Rename Parameter', 'Move Method', 'Extract Method', 'Rename Parameter', 'Change Parameter Type', 'Rename Parameter', 'Extract Method', " +
			"'Extract Method', 'Change Parameter Type', 'Change Parameter Type', 'Change Parameter Type', 'Change Parameter Type', 'Change Parameter Type', 'Change Parameter Type', 'Move Method', 'Move Method', " +
			"'Move Method', 'Extract Class', 'Extract Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Rename Method', 'Rename Variable'," +
			"'Rename Method', 'Rename Variable', 'Extract Method', 'Extract Method', 'Extract Method', 'Rename Variable', 'Extract Method', 'Extract Method', 'Extract Method', 'Rename Method', 'Extract Method', 'Rename Variable', 'Extract Method', " +
			"Extract Variable', 'Extract Variable', 'Change Variable Type', 'Move Method', 'Rename Parameter', 'Rename Variable', 'Change Parameter Type', 'Move Method', 'Extract Variable, 'Pull Up Method', 'Pull Up Method', " +
			"'Pull Up Method', 'Pull Up Attribute', 'Pull Up Method', 'Pull Up Attribute', 'Pull Up Method', 'Extract Variable', 'Pull Up Method', 'Pull Up Method', 'Pull Up Attribute', 'Pull Up Attribute', 'Pull Up Method', " +
			"'Extract Method', 'Move Class', 'Extract Variable', 'Extract Method', 'Extract Method', 'Rename Variable', 'Rename Variable', 'Rename Parameter', 'Rename Variable', 'Rename Method', 'Rename Attribute', " +
			"'Rename Parameter', 'Extract Variable', 'Replace Variable With Attribute', 'Push Down Method', 'Pull Up Attribute', 'Pull Up Method', 'Pull Up Method', 'Pull Up Method', 'Pull Up Method', 'Pull Up Attribute', " +
			"'Pull Up Attribute', 'Pull Up Attribute', 'Pull Up Method', 'Pull Up Attribute', 'Pull Up Method', 'Pull Up Method', 'Pull Up Method', 'Pull Up Method', 'Pull Up Method', 'Pull Up Attribute', 'Pull Up Attribute', " +
			"'Pull Up Method', 'Pull Up Attribute', 'Pull Up Method', 'Pull Up Method','Rename Attribute', 'Move Method', 'Move Method', 'Move Attribute', 'Move Method', 'Move Method', 'Move Attribute', 'Move Attribute', 'Move Method'," +
			" 'Move Method', 'Move Method', 'Rename Attribute', 'Change Attribute Type', 'Change Attribute Type', 'Rename Attribute', 'Rename Attribute', 'Change Attribute Type', 'Rename Method', 'Extract Method', 'Extract Method', " +
			"'Rename Method', 'Rename Method', 'Extract Method','Extract Method', 'Rename Method', 'Rename Method', 'Rename Method', 'Extract Method', 'Extract Method', 'Move Method', 'Move Method', 'Move Attribute', 'Move Attribute', " +
			"'Move Method', 'Move Attribute', 'Move Method', 'Move Method', 'Move Method', 'Extract Class', 'Move Attribute', 'Rename Parameter', 'Rename Parameter', 'Change Parameter Type', 'Change Parameter Type', 'Rename Parameter', " +
			"'Rename Method', 'Replace Variable With Attribute', 'Extract Method', 'Replace Variable With Attribute', 'Extract Method', 'Replace Variable With Attribute', 'Rename Method', 'Replace Variable With Attribute', 'Extract Method', " +
			"'Replace Variable With Attribute', 'Rename Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Rename Method', 'Rename Method', 'Rename Method', 'Rename Method', 'Rename Method', 'Rename Method', " +
			"'Rename Method', 'Rename Parameter', 'Rename Method', 'Rename Method', 'Rename Method', 'Rename Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Change Parameter Type', 'Change Parameter Type', " +
			"'Change Parameter Type', 'Rename Method', 'Change Attribute Type', 'Inline Method', 'Inline Method', 'Rename Method', 'Inline Method', 'Inline Method', 'Rename Variable', 'Change Return Type', 'Move Method', 'Move Method', 'Move Method', " +
			"'Move Method', 'Extract Class', 'Move Method', 'Move Method', 'Move Attribute', 'Extract Method', 'Rename Attribute', 'Extract Method', 'Rename Method', 'Rename Method', 'Rename Method', 'Rename Attribute', 'Split Attribute', " +
			"'Rename Attribute', 'Rename Attribute', 'Rename Attribute', 'Change Variable Type', 'Change Variable Type', 'Change Variable Type', 'Change Variable Type', 'Rename Variable', 'Change Variable Type', 'Rename Variable', 'Rename Method', " +
			"'Extract Method', 'Extract Variable', 'Rename Method', 'Rename Method','Move Method', 'Rename Parameter', 'Extract Method', 'Rename Method', 'Extract Method', 'Extract Method', 'Extract Method', 'Rename Method', 'Move Attribute', 'Move Attribute'," +
			"'Move Method','Extract Method', 'Rename Variable', 'Extract Method', 'Extract Method'";

	private static String smellsValidacaoManualICSME21Before = "GodClass, ComplexClass, BrainClass, FeatureEnvy, 'ComplexClass, SpaghettiCode, 'IntensiveCoupling, " +
			"'DataClass, 'ComplexClass, SpaghettiCode, 'ComplexClass, 'FeatureEnvy, LongMethod, DispersedCoupling, 'IntensiveCoupling, 'ComplexClass, " +
			"'FeatureEnvy, LongMethod, 'ComplexClass, 'FeatureEnvy, 'FeatureEnvy, 'GodClass, ComplexClass, BrainClass, 'FeatureEnvy, 'LongMethod, 'FeatureEnvy, LongMethod, " +
			"LongParameterList, 'ComplexClass, 'FeatureEnvy, LongMethod, IntensiveCoupling, 'ComplexClass, SpaghettiCode, FeatureEnvy, LongMethod, DispersedCoupling, 'ComplexClass, " +
			"'FeatureEnvy, 'FeatureEnvy, 'ComplexClass, 'ClassDataShouldBePrivate, 'FeatureEnvy, 'FeatureEnvy, DispersedCoupling, 'SpeculativeGenerality, DataClass, 'DataClass, 'ComplexClass, " +
			"'FeatureEnvy, LongMethod, IntensiveCoupling, 'ComplexClass, SpeculativeGenerality, 'ComplexClass, 'FeatureEnvy, LongMethod, IntensiveCoupling, 'SpeculativeGenerality, " +
			"'ClassDataShouldBePrivate, SpaghettiCode, 'FeatureEnvy, LongMethod, MessageChain, 'LongMethod, MessageChain, 'ClassDataShouldBePrivate, 'FeatureEnvy, LongMethod, IntensiveCoupling, " +
			"'SpaghettiCode, 'FeatureEnvy, MessageChain, 'FeatureEnvy, LongMethod, MessageChain, 'FeatureEnvy, LongMethod, MessageChain,'FeatureEnvy, MessageChain, 'FeatureEnvy, MessageChain," +
			" 'DataClass, 'FeatureEnvy, LongParameterList";

	private static String smellsValidacaoManualICSME21After = "'ComplexClass, 'FeatureEnvy, 'ComplexClass, 'MessageChain, IntensiveCoupling, 'FeatureEnvy, MessageChain, 'DataClass, 'SpeculativeGenerality, " +
			"DataClass, SpeculativeGenerality, ComplexClass, MessageChain, DispersedCoupling, ComplexClass, SpeculativeGenerality, IntensiveCoupling, IntensiveCoupling, " +
			"MessageChain, ComplexClass, FeatureEnvy, DispersedCoupling, ComplexClass, SpaghettiCode, LongMethod, FeatureEnvy, LongMethod, ComplexClass, FeatureEnvy, FeatureEnvy, " +
			"FeatureEnvy, FeatureEnvy, LongParameterList, LongParameterList, FeatureEnvy, LongParameterList, ComplexClass, FeatureEnvy, IntensiveCoupling, ComplexClass, " +
			"FeatureEnvy, MessageChain, DispersedCoupling, ComplexClass, IntensiveCoupling, IntensiveCoupling, ClassDataShouldBePrivate, FeatureEnvy, IntensiveCoupling, ComplexClass," +
			"ClassDataShouldBePrivate, FeatureEnvy, FeatureEnvy, ComplexClass, DispersedCoupling, LongMethod, SpeculativeGenerality, DataClass, DataClass, ComplexClass, " +
			"FeatureEnvy, LongMethod, IntensiveCoupling, SpeculativeGenerality, DataClass, ComplexClass, SpaghettiCode, ComplexClass, FeatureEnvy, IntensiveCoupling, FeatureEnvy, " +
			"DispersedCoupling, SpeculativeGenerality, DataClass, FeatureEnvy, LongParameterList, ClassDataShouldBePrivate, MessageChain, FeatureEnvy, LongParameterList, MessageChain, " +
			"MessageChain, LongParameterList, ClassDataShouldBePrivate, FeatureEnvy, LongParameterList, LongParameterList, FeatureEnvy, FeatureEnvy, FeatureEnvy, IntensiveCoupling, " +
			"LongParameterList, FeatureEnvy, FeatureEnvy, LongMethod, DispersedCoupling, GodClass, SpaghettiCode, FeatureEnvy, MessageChain, FeatureEnvy, LongMethod, MessageChain, " +
			"FeatureEnvy, LongMethod, MessageChain, MessageChain, FeatureEnvy, MessageChain, DataClass, LongParameterList, FeatureEnvy, FeatureEnvy, DispersedCoupling, LongParameterList";


	public static List<String> convertRefactoringsTextToRefactoringsList(String refactoringsText){
		
		List<String> refactorings = new ArrayList<String>();
		
		refactoringsText = refactoringsText.replace("[", "");
		refactoringsText = refactoringsText.replace("]", "");
		
		refactorings = Arrays.asList(refactoringsText.split("\\s*,\\s*"));
		
		return refactorings;
		
	}

	public static void countElementsFromTextList(String textList){
		List<String> myList = new ArrayList<String>(Arrays.asList(textList.split(",")));
		System.out.println(myList.size());
	}

	public List<CompositeRefactoring> getComposites(String pathComposites){
		ObjectMapper mapper = new ObjectMapper();
		List<CompositeRefactoring> compositeList = new ArrayList<>();
		try {

			CompositeRefactoring[] composites = mapper.readValue(new File(pathComposites),
					CompositeRefactoring[].class);

			compositeList = Arrays.asList(composites);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return compositeList;
	}

	public List<CompositeRefactoring> filterCompositesByIds(String pathComposites, String ids){
		List<String> idsList = new ArrayList<String>(Arrays.asList(ids.split(",")));

		List<CompositeRefactoring> allComposites = new ArrayList<>();
		allComposites = getComposites(pathComposites);

		List<CompositeRefactoring> filteredComposites = new ArrayList<>();

		for (String id : idsList) {
			for (CompositeRefactoring composite : allComposites) {
				if(composite.getId().equals(id)){
					filteredComposites.add(composite);
				}

			}
		}

		return filteredComposites;
	}

}
