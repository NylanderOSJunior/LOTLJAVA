package com.example.utils;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.kernel.geom.PageSize;
import java.io.File;
import java.io.FileOutputStream;

public class GeradorPDF {
    public static void gerarRelatorio(String conteudo) {
        String destino = "relatorio_infraestrutura.pdf";

        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(new File(destino)));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);

            document.add(new Paragraph("FORMULÁRIO DE VALIDAÇÃO DA INFRAESTRUTURA")
                    .setBold()
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            // Criando tabela para os dados
            float[] columnWidths = {200, 300};
            Table table = new Table(columnWidths);

            String[] linhas = conteudo.split("\n");
            for (String linha : linhas) {
                String[] partes = linha.split(": ");
                if (partes.length == 2) {
                    table.addCell(new Cell().add(new Paragraph(partes[0]).setBold()));
                    table.addCell(new Cell().add(new Paragraph(partes[1])));
                }
            }

            document.add(table);
            document.close();
            System.out.println("Relatório gerado com sucesso: " + destino);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
