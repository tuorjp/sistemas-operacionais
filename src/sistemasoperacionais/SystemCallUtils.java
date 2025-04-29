package sistemasoperacionais;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SystemCallUtils {

    // Criação de arquivo dentro da pasta fornecida
    public static void criarArquivoNaPasta(File pasta) {
        Path caminhoArquivo = Path.of(pasta.getPath(), "exemplo.txt");

        String conteudo = "Este é um exemplo de escrita em arquivo.\n" +
                "A escrita foi feita com sucesso!\n";

        try {
            Files.writeString(caminhoArquivo, conteudo,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Arquivo 'exemplo.txt' criado e conteúdo escrito.");

            // Abrir o arquivo diretamente no Bloco de Notas
            new ProcessBuilder("notepad.exe", caminhoArquivo.toString()).start();
            System.out.println("Bloco de notas aberto com o arquivo.");
        } catch (IOException e) {
            System.out.println("Erro ao criar ou escrever no arquivo: " + e.getMessage());
        }
    }

    // Lista todos os arquivos e diretórios dentro do diretório fornecido
    public static void listaArquivos(String diretorio) {
        System.out.println("\nArquivos no diretório atual:");
        File dirAtual = new File(diretorio);
        File[] arquivos = dirAtual.listFiles();
        if (arquivos != null) {
            for (File f : arquivos) {
                System.out.println((f.isDirectory() ? "[DIR] " : "[ARQ] ") + f.getName());
            }
        }
    }
}
