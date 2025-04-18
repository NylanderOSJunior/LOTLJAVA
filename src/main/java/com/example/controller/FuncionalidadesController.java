package com.example.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FuncionalidadesController {

    public void ajustarFirewall(TextField txtPorta, TextField txtNomePorta) {
        String nome = txtNomePorta.getText().trim();
        String portas = txtPorta.getText().trim();

        if (nome.isEmpty() || portas.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos para liberar a porta.");
            return;
        }

        // Verifica se as portas são válidas (apenas números e vírgulas)
        String[] listaPortas = portas.split(",");
        for (String porta : listaPortas) {
            porta = porta.trim();
            if (!porta.matches("\\d+")) {  // Verifica se cada porta é um número
                mostrarAlerta("Erro", "Insira apenas números separados por vírgula.");
                return;
            }
        }

        try {
            // Libera para TCP
            executarComando("netsh advfirewall firewall add rule name=\"" + nome + "_TCP\" dir=in action=allow protocol=TCP localport=" + portas);

            // Libera para UDP
            executarComando("netsh advfirewall firewall add rule name=\"" + nome + "_UDP\" dir=in action=allow protocol=UDP localport=" + portas);

            mostrarAlerta("Sucesso", "As regras para as portas foram liberadas com sucesso!");

        } catch (IOException | InterruptedException e) {
            mostrarAlerta("Erro", "Falha ao liberar a porta: " + e.getMessage());
        }
    }

    private void executarComando(String comando) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", comando});
        process.waitFor();
    }

    public void selecionarDiretorio(TextField txtDiretorio) {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Selecione um Diretório");

    // Abre o diretório e obtém o caminho selecionado
    File diretorioSelecionado = directoryChooser.showDialog(new Stage());

    if (diretorioSelecionado != null) {
        txtDiretorio.setText(diretorioSelecionado.getAbsolutePath());
    }
}


public void criarBackup(TextField txtSchema, TextField txtService, TextField txtDiretorio, TextField txtDiretoriobk) {
    String schema = txtSchema.getText().trim();
    String service = txtService.getText().trim();
    String diretorio = txtDiretorio.getText().trim();
    String diretoriobk = txtDiretoriobk.getText().trim();
 

    if (schema.isEmpty() || service.isEmpty() || diretoriobk.isEmpty() || diretorio.isEmpty()) {
        mostrarAlerta("Erro", "Preencha todos os campos para criar o backup.");
        return;
    }

    String scriptBackup = "@echo off\n" +
            "FOR /F \"tokens=1,2,3 delims=/ \" %%a in (\"%DATE%\") do (\n" +
            "set DIA=%%a\n" +
            "set MES=%%b\n" +
            "set ANO=%%c\n" +
            ")\n" +
            "FOR /F \"tokens=1,2,3 delims=:, \" %%a in (\"%TIME%\") do (\n" +
            "set H=%%a\n" +
            "set M=%%b\n" +
            "set S=%%c\n" +
            ")\n" +
            "set H=0%H%\n" +
            "set H=%H:~-2%\n" +
            "set FORMATO=%ANO%_%MES%_%DIA%_%H%_%M%_%S%\n" +
            "@echo on\n\n" +
            "C:\n" +
            "cd " + diretorio + "\n" +
            "expdp BACKUPS/SAgr02435/" + service + " directory=BACKUP dumpfile=" + schema + "_%FORMATO%.dmp logfile=" + schema + "_%FORMATO%.log schemas=" + schema + "\n" +
            "zip -9 " + diretoriobk + "\\" + schema + "_%FORMATO%.zip " + diretoriobk + "\\" + schema + "_%FORMATO%.dmp " + diretoriobk + "\\" + schema + "_%FORMATO%.log\n\n" +
            "C:\n" +
            "cd " + diretoriobk + "\n" +
            "move *.log " + diretoriobk + "\\LOG\n" +
            "del *.dmp\n\n" +
            "@REM make var nowDay\n" +
            "FOR /F \"TOKENS=1* DELIMS=/\" %%A IN ('date/t') DO SET nowDay=%%A\n" +
            "CALL FORFILES /S /p " + diretoriobk + " /d -30 /C \"CMD /C echo @FILE @FDATE\" > " + diretoriobk + "\\LOG\\%nowDay%.log\n" +
            "CALL FORFILES /S /p " + diretoriobk + " /d -30 /c \"CMD /C DEL @FILE /Q\"";

    // Caminho do arquivo de script
    String scriptPath = diretorio + "\\BACKUP_ORACLE.bat";

    try {
        // Criar arquivo de script .bat
        File file = new File(scriptPath);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(scriptBackup);
        bufferedWriter.close();

        mostrarAlerta("Sucesso", "Backup criado com sucesso em: " + diretorio);

    } catch (IOException  e) {
        mostrarAlerta("Erro", "Falha ao criar backup: " + e.getMessage());
    }
}

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}