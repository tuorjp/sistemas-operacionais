package sistemasoperacionais;

import java.io.File;
import java.lang.management.ManagementFactory;

public class SystemCallDemo {
    public static void main(String[] args) {
        // Recupera o PID do processo atual
        String processName = ManagementFactory.getRuntimeMXBean().getName();
        String pid = processName.split("@")[0];
        System.out.println("PID do processo atual: " + pid);

        // Recupera o diretório de trabalho atual
        String currentDir = System.getProperty("user.dir");
        System.out.println("Diretório de trabalho atual: " + currentDir);

        // Criação de uma nova pasta no diretório atual
        String nomePasta = "nova_pasta";
        File pasta = new File(currentDir + File.separator + nomePasta);

        // Se a pasta ainda NÃO existe, cria
        if (!pasta.exists()) {
            boolean criada = pasta.mkdir();
            if (criada) {
                System.out.println("Pasta '" + nomePasta + "' criada com sucesso!");
            } else {
                System.out.println("Falha ao criar pasta '" + nomePasta + "'!");
            }
        } else {
            System.out.println("A pasta já existe.");
        }
        sistemasoperacionais.SystemCallUtils.criarArquivoNaPasta(pasta);
        sistemasoperacionais.SystemCallUtils.listaArquivos(currentDir);
    }
}
