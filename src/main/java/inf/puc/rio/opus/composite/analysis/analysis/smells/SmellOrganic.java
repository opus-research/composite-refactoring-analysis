package inf.puc.rio.opus.composite.analysis.analysis.smells;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SmellOrganic {

    ProcessBuilder pb;

    // Teste: ant 96ca021c758da63ddfb00a7dff93f30417ebe616
    public void colectSmells(String commit, String output) {
        // ATUALIZA PARA O COMMIT EM QUESTÃO
        String commandGitCheckout[] = {"git", "checkout", "-f", commit};
        executeCommand(commandGitCheckout, Constants.FOLDER_CHECKOUT);

        // ADICIONA AS VARIÁVEIS DE AMBIENTE
        pb.environment().put("ECLIPSE_PATH", Constants.FOLDER_ECLIPSE);
        pb.environment().put("EQUINOX", Constants.FOLDER_ECLIPSE +"plugins/org.eclipse.equinox.launcher_1.5.500.v20190715-1310.jar");
        pb.environment().put("MAIN", "org.eclipse.core.launcher.Main");
        pb.environment().put("ORGANIC", "organic.Organic");

        // EXECUTA A ORGANIC
        String comando6[] = {"java", "-jar", "-XX:MaxPermSize=2560m", "-Xms40m", "-Xmx2500m", pb.environment().get("EQUINOX"),
                "-application", pb.environment().get("ORGANIC"), "-sf", commit+".json",
                "-src", output};
        executeCommand(comando6, Constants.FOLDER_CLONE);
    }

    public  void executeCommand(String[] command, String folder) {
        try {
            pb = new ProcessBuilder(command);
            pb.directory(new File(folder));
            pb.redirectErrorStream(true);
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String outString;
            while ((outString = br.readLine()) != null) {
                System.out.println(outString);
            }
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
