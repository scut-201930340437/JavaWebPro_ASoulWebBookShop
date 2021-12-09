package com.GeWei.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Db2CsvExporter {
    private BufferedWriter fileWriter;

    public String export(String table, String path) {
        String jdbcURL = "jdbc:mysql://127.0.0.1:3306/db1?useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "200748scPD!";
        //不包含路径
        String name=getFileName(table+"_export");
        //加上路径
        String csvFileName = path+name;

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            String sql = "select * from "+table;
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            fileWriter = new BufferedWriter(new FileWriter(csvFileName));
            int columnCount = writeHeaderLine(result);
            while (result.next()) {
                String line = "";
                for (int i = 2; i <= columnCount; i++) {
                    Object valueObject = result.getObject(i);
                    String valueString = "";
                    if (valueObject != null) valueString = valueObject.toString();
                    if (valueObject instanceof String) {
                        valueString = "\"" + escapeDoubleQuotes(valueString) + "\"";
                    }
                    line += valueString;
                    if (i != columnCount) {
                        line += ",";
                    }
                }
                fileWriter.newLine();
                fileWriter.write(line);
            }
            statement.close();
            fileWriter.close();
        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
        //返回不包含路径的文件名
        return name;
    }
    private String getFileName(String baseName) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String dateTimeStr = dateFormat.format(new Date());
        return baseName+String.format("_%s.csv", dateTimeStr);
    }
    private int writeHeaderLine(ResultSet result) throws SQLException, IOException {
        // write header line containing column names
        ResultSetMetaData metaData = result.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        String headerLine = "";
        // exclude the first column which is the ID field
        for (int i = 2; i <= numberOfColumns; i++) {
            String columnName = metaData.getColumnName(i);
            headerLine = headerLine+columnName+",";
        }
        fileWriter.write(headerLine.substring(0, headerLine.length() - 1));
        return numberOfColumns;
    }
    private String escapeDoubleQuotes(String value) {
        return value.replaceAll("\"", "\"\"");
    }
}
