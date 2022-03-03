package inf.puc.rio.opus.composite.analysis.analysis.composite;

import com.fasterxml.jackson.databind.ObjectMapper;
import inf.puc.rio.opus.composite.analysis.utils.CompositeUtils;
import inf.puc.rio.opus.composite.model.CompositeRefactoring;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeAnalyzer {


    public static void main(String[] args) {

        //CompositeUtils.countElementsFromTextList(CompositeUtils.smellsValidacaoManualICSME21Before);
        //CompositeUtils.countElementsFromTextList(CompositeUtils.smellsValidacaoManualICSME21After);

        String pathComposites = "C:\\Users\\anaca\\OneDrive\\PUC-Rio\\OPUS\\CompositeRefactoring\\Dataset\\Composites\\";
        CompositeUtils compositeUtils = new CompositeUtils();
        List<CompositeRefactoring> composites = compositeUtils.filterCompositesByIds(pathComposites + "ant-composite-rangebased.json","1159,2991,644,560,1373,850,606,1417,732,593");

        ObjectMapper mapper = new ObjectMapper();

        // Java object to JSON file
        try {
            mapper.writeValue(new File("composites-ant-manual-validation-side-recommendation2.json"), composites);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
