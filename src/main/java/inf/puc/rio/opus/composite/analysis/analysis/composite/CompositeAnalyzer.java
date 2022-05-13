package inf.puc.rio.opus.composite.analysis.analysis.composite;

import com.fasterxml.jackson.databind.ObjectMapper;
import inf.puc.rio.opus.composite.analysis.utils.CompositeUtils;
import inf.puc.rio.opus.composite.database.composites.CompositeCollector;
import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.effect.CompositeDTO;
import inf.puc.rio.opus.composite.model.group.CompositeGroup;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CompositeAnalyzer {

    public CompositeCollector collector;
    public CompositeGroupAnalyzer groupAnalyzer;

    public CompositeAnalyzer() {
        this.groupAnalyzer = new CompositeGroupAnalyzer();
    }

   /*
   * Ver quais complete composites dos projetos restantes se enquadram nos 5 cenarios do Catalogo
   * Para isso precisa-se primeiro gerar um summarized-groups no formato antigo para esses projetos
   * a partir dos CSV
   * */

    public static void main(String[] args) {

        CompositeAnalyzer analyzer = new CompositeAnalyzer();
        analyzer.collector = new CompositeCollector(args);

        // Procedure 1 - Collect summarized groups of remaining projects
        // Get Composite and Smells data from CSV
        // List<CompositeDTO> compositeDTO = compositeEffectAnalyzer.getCompositeEffectDTO(csv);

        // Get Composite Effect from composite dto list
        // List<CompositeDTO> compositeDTO  = getCompleteComposite(List<CompositeDTO> composites)
        // Write composite effect in json

        // Get groups
        // Map<String, List<CompositeDTO>> groups = createCompositeGroupsFromRefactoringAsList(List<CompositeDTO> composites)
        // Write groups in json

        // Get summarized groups
        // List<CompositeGroup> summarized groups - summarizeGroups(Map<String, List<CompositeDTO>> groups)
        // Write summarized group in json

        // Procedure 2 - Collect composite ids for each scenario of catalog from remaining projects
        // catalogAnalyzer.getCatalogFirstRecommendationResult (summarized-remaining-projects.json)
        // add composite ids in catalog table

        // Procedure 3
        // Collect ranking for each scenario in catalog like other time
    }




    public List<CompositeRefactoring> getCompositeFromJson(String compositePath)
    {
        ObjectMapper mapper = new ObjectMapper();
        List<CompositeRefactoring> compositeList = new ArrayList<>();
        try {

            CompositeRefactoring[] composites = mapper.readValue(new File(compositePath),
                    CompositeRefactoring[].class);

            compositeList = new ArrayList<CompositeRefactoring>(Arrays.asList(composites));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return compositeList;
    }
}
