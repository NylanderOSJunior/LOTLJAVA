package com.example.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PerformanceModel {

    public static List<String> generateIndexes(String sqlQuery) {
        List<String> suggestedIndexes = new ArrayList<>();
        
        // Map para armazenar alias e seus respectivos nomes de tabela
        Map<String, String> tableAliases = new HashMap<>();
        
        // Regex para encontrar todas as tabelas com seus aliases
        Pattern tablePattern = Pattern.compile("(FROM|JOIN)\\s+([\\w\\d_]+)\\s+(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher tableMatcher = tablePattern.matcher(sqlQuery);
        
        // Preenche o mapa de alias -> nome da tabela
        while (tableMatcher.find()) {
            String tableName = tableMatcher.group(2).toUpperCase();  // Tabela
            String alias = tableMatcher.group(3).toUpperCase();     // Alias
            tableAliases.put(alias, tableName);  // Mapeia alias -> tabela
        }

        // Map para armazenar as condições de filtro
        Map<String, List<String>> tableColumns = new HashMap<>();
        
        // Regex para capturar as colunas nas cláusulas WHERE, JOIN, LIKE, etc.
        Pattern columnPattern = Pattern.compile("(\\w+)(?:\\s+\\w+)?\\s*(=|LIKE|BETWEEN|IN)\\s*(\\w+\\.?\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher columnMatcher = columnPattern.matcher(sqlQuery.toLowerCase());

        // Processa todas as condições
        while (columnMatcher.find()) {
            String operator = columnMatcher.group(2).toUpperCase();  // O operador (LIKE, =, BETWEEN, IN)
            String tableAliasColumn = columnMatcher.group(3); // Pega a coluna da condição (tabela.coluna)
            String[] parts = tableAliasColumn.split("\\."); // Separa a tabela e a coluna

            // Verifica se a condição contém uma tabela e coluna válidas
            if (parts.length == 2) {
                String alias = parts[0].toUpperCase();  // Alias da tabela
                String column = parts[1].toUpperCase(); // Nome da coluna

                // Verifica se o alias tem uma tabela associada
                String table = tableAliases.get(alias);
                
                if (table != null) {
                    // Adiciona as colunas ao mapa de tabelas
                    tableColumns.putIfAbsent(table, new ArrayList<>());
                    tableColumns.get(table).add(column);
                    
                    // Adiciona índice para colunas que usam o operador LIKE
                    if (operator.equals("LIKE")) {
                        String indexName = "CREATE INDEX idx_" + table + "_" + column + "_like ON " + table + " (LOWER(" + column + "));";
                        // Evitar duplicação
                        if (!suggestedIndexes.contains(indexName)) {
                            suggestedIndexes.add(indexName);
                        }
                    } else if (operator.equals("=")) {
                        String indexName = "CREATE INDEX idx_" + table + "_" + column + " ON " + table + " (" + column + ");";
                        // Evitar duplicação
                        if (!suggestedIndexes.contains(indexName)) {
                            suggestedIndexes.add(indexName);
                        }
                    } else if (operator.equals("BETWEEN") || operator.equals("IN")) {
                        // Cria índices para BETWEEN ou IN
                        String indexName = "CREATE INDEX idx_" + table + "_" + column + "_range ON " + table + " (" + column + ");";
                        // Evitar duplicação
                        if (!suggestedIndexes.contains(indexName)) {
                            suggestedIndexes.add(indexName);
                        }
                    }
                }
            }
        }

        // Geração de índices compostos: verifica colunas múltiplas na mesma tabela
        for (Map.Entry<String, List<String>> entry : tableColumns.entrySet()) {
            String table = entry.getKey();
            List<String> columns = entry.getValue();
            
            // Remover duplicatas da lista de colunas
            Set<String> uniqueColumns = new HashSet<>(columns);
            
            if (uniqueColumns.size() > 1) {
                // Cria um índice composto para as colunas
                StringBuilder indexName = new StringBuilder("CREATE INDEX idx_" + table + "_");
                StringBuilder indexColumns = new StringBuilder();

                // Adiciona as colunas únicas ao índice
                for (String column : uniqueColumns) {
                    indexColumns.append(column);
                    indexColumns.append(", ");
                }

                // Remove a última vírgula
                indexColumns.setLength(indexColumns.length() - 2);

                indexName.append(String.join("_", uniqueColumns));
                suggestedIndexes.add(indexName.toString() + " ON " + table + " (" + indexColumns + ");");
            }
        }

        // Adicionando índices para colunas no GROUP BY e ORDER BY
        // Regex para capturar as colunas no GROUP BY e ORDER BY
        Pattern groupByOrderPattern = Pattern.compile("(GROUP BY|ORDER BY)\\s+([\\w\\d_\\s,\\.]+)", Pattern.CASE_INSENSITIVE);
        Matcher groupByOrderMatcher = groupByOrderPattern.matcher(sqlQuery);

        while (groupByOrderMatcher.find()) {
            String columnsPart = groupByOrderMatcher.group(2); // Colunas no GROUP BY ou ORDER BY
            String[] columns = columnsPart.split(",");

            for (String column : columns) {
                String cleanedColumn = column.trim().toUpperCase();
                
                // Se a coluna estiver associada à tabela no alias
                if (cleanedColumn.contains(".")) {
                    String[] parts = cleanedColumn.split("\\.");
                    String alias = parts[0].toUpperCase();
                    String columnName = parts[1].toUpperCase();

                    String table = tableAliases.get(alias);
                    if (table != null) {
                        String indexName = "CREATE INDEX idx_" + table + "_" + columnName + "_order ON " + table + " (" + columnName + ");";
                        // Evitar duplicação
                        if (!suggestedIndexes.contains(indexName)) {
                            suggestedIndexes.add(indexName);
                        }
                    }
                }
            }
        }

        // Considerando funções agregadas (ex: MAX(), SUM(), etc.)
        Pattern aggregatePattern = Pattern.compile("(MAX|MIN|AVG|SUM)\\((\\w+\\.?\\w+)\\)", Pattern.CASE_INSENSITIVE);
        Matcher aggregateMatcher = aggregatePattern.matcher(sqlQuery);

        while (aggregateMatcher.find()) {
            String column = aggregateMatcher.group(2); // A coluna usada na função agregada
            String[] parts = column.split("\\."); // Separa a tabela e a coluna

            if (parts.length == 2) {
                String alias = parts[0].toUpperCase();  // Alias da tabela
                String columnName = parts[1].toUpperCase(); // Nome da coluna

                String table = tableAliases.get(alias);
                if (table != null) {
                    String indexName = "CREATE INDEX idx_" + table + "_" + columnName + "_agg ON " + table + " (" + columnName + ");";
                    // Evitar duplicação
                    if (!suggestedIndexes.contains(indexName)) {
                        suggestedIndexes.add(indexName);
                    }
                }
            }
        }

        return suggestedIndexes;
    }
}
