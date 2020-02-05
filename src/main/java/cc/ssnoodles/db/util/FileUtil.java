package cc.ssnoodles.db.util;

import cc.ssnoodles.db.constant.DbType;
import cc.ssnoodles.db.constant.TemplateType;
import cc.ssnoodles.db.domain.Config;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/7/13 08:35
 */
public class FileUtil {

    public static final String DEFAULT_FILE = "app.properties";

    public static final Config PROPERTIES = readPropertiesFile(DEFAULT_FILE);

    private static final String CODE = "utf-8";

    private static final String SUFFIX = ".java";

    public static Config readPropertiesFile(String resources) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(DEFAULT_FILE));
        } catch (IOException e) {
            try {
                properties.load(Objects.requireNonNull(FileUtil.class.getClassLoader().getResourceAsStream(resources)));
            } catch (IOException e1) {
                throw new RuntimeException("Load configuration file failed");
            }
        }
        try {
            String url = properties.getProperty("url");
            Config config = new Config();
            config.setDbType(getDb(url));
            config.setUrl(url);
            config.setUsername(properties.getProperty("username"));
            config.setPassword(properties.getProperty("password"));
            config.setOutPath(properties.getProperty("out.path"));
            config.setTemplates(getTemplates(properties.getProperty("templates")));
            config.setAuthor(properties.getProperty("author"));
            config.setOverwriteFiles(!StringUtil.isEmpty(properties.getProperty("overwrite.files")) && Boolean.parseBoolean(properties.getProperty("overwrite.files")));
            config.setSingleTableName(properties.getProperty("single.table.name"));
            config.setSingleTableRename(properties.getProperty("single.table.rename"));
            config.setCustomTemplate(properties.getProperty("custom.template"));
            return config;
        } catch (Exception e) {
            throw new RuntimeException("Load configuration file failed");
        }
    }

    private static TemplateType[] getTemplates(String templates) {
        if (StringUtil.isEmpty(templates)) {
            return new TemplateType[]{TemplateType.POJO};
        }
        String[] split = templates.split(",");
        TemplateType[] templateTypes = new TemplateType[split.length];
        for (int i = 0; i < split.length; i++) {
            templateTypes[i] = TemplateType.get(split[i]);
        }
        return templateTypes;
    }

    private static DbType getDb(String url) {
        if (StringUtil.isEmpty(url)) {
            throw new RuntimeException("Load configuration file url property failed");
        }
        if (url.contains(DbType.ORACLE.getType())) {
            return DbType.ORACLE;
        } else if (url.contains(DbType.POSTGRESQL.getType())) {
            return DbType.POSTGRESQL;
        } else if (url.contains(DbType.MYSQL.getType())) {
            return DbType.MYSQL;
        } else {
            throw new RuntimeException("No database was found to be supported");
        }
    }

    public static void write2JavaFiles(String path, String str, boolean isOverwriteFiles) {
        File file = new File(path + SUFFIX);
        if (file.exists() && !isOverwriteFiles) {
            return;
        }
        mkdirs(file);
        try (FileOutputStream ops = new FileOutputStream(file);
             BufferedOutputStream buff = new BufferedOutputStream(ops)) {
            buff.write(str.getBytes(CODE));
            buff.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Write java file failed, Path: " + path + SUFFIX);
        }
    }

    private static void mkdirs(File file) {
        File fileParent = file.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
    }


    public static void write2IfExistFiles(String path, String noExistStr, String existStr) {
        File file = new File(path + SUFFIX);
        if (file.exists()) {
            String separator = System.getProperty("line.separator");

            try (FileReader reader = new FileReader(file);
                 BufferedReader br = new BufferedReader(reader)) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("}") && !line.contains("})")) {
                        break;
                    }
                    sb.append(line).append(separator);
                }
                sb.append(separator);
                sb.append(existStr);
                write2JavaFiles(path, sb.toString(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            write2JavaFiles(path, noExistStr, true);
        }
    }
}
