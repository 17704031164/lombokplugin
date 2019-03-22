package com.jschen.main;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.*;

/*
 *  LombokPlugin
 * @author jschen
 * @description
 * @date
 */
public class LombokPluginConfig extends PluginAdapter {
    private boolean data = true;
    private Map<String, String> map = new HashMap(20);
    private Set<String> importPackage = new HashSet();
    private static Map<String, String> packageMap = new HashMap(20);

    public LombokPluginConfig() {

    }

    public boolean validate(List<String> list) {
        return true;
    }

    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.processEntityClass(topLevelClass, introspectedTable);
        return true;
    }

    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.processEntityClass(topLevelClass, introspectedTable);
        return true;
    }

    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.processEntityClass(topLevelClass, introspectedTable);
        return true;
    }

    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    private void processEntityClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Iterator var3;
        String key;
        if (this.importPackage != null && this.importPackage.size() > 0) {
            var3 = this.importPackage.iterator();

            while(var3.hasNext()) {
                key = (String)var3.next();
                topLevelClass.addImportedType(key);
            }
        }

        if (this.map != null && this.map.size() > 0) {
            var3 = this.map.keySet().iterator();

            while(var3.hasNext()) {
                key = (String)var3.next();
                StringBuilder sb = new StringBuilder();
                sb.append(key);
                String config = (String)this.map.get(key);
                if (config != null && config.trim().length() > 0) {
                    sb.append("(").append(config).append(")");
                }

                String p = (String)packageMap.get(key);
                topLevelClass.addImportedType(p == null ? "lombok." + key.substring(1) : p + "." + key.substring(1));
                topLevelClass.addAnnotation(sb.toString());
            }
        }

    }

    public void setProperties(Properties properties) {
        super.setProperties(properties);
        Iterator var2 = properties.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<Object, Object> entry = (Map.Entry)var2.next();
            String key = (String)entry.getKey();
            if (key.startsWith("@")) {
                this.map.put(key, (String)entry.getValue());
            }

            if (key.startsWith("import.")) {
                this.importPackage.add(key.substring(7));
            }
        }

        if (this.data && !this.map.containsKey("@Data")) {
            this.map.put("@Data",null);
        }

    }

    static {
        packageMap.put("@Accessors", "lombok.experimental");
        packageMap.put("@Builder", "lombok.experimental");
        packageMap.put("@Delegate", "lombok.experimental");
        packageMap.put("@ExtensionMethod", "lombok.experimental");
        packageMap.put("@FieldDefaults", "lombok.experimental");
        packageMap.put("@Helper", "lombok.experimental");
        packageMap.put("@NonFinal", "lombok.experimental");
        packageMap.put("@PackagePrivate", "lombok.experimental");
        packageMap.put("@Tolerate", "lombok.experimental");
        packageMap.put("@UtilityClass", "lombok.experimental");
        packageMap.put("@Value", "lombok.experimental");
        packageMap.put("@var", "lombok.experimental");
        packageMap.put("@Wither", "lombok.experimental");
        packageMap.put("@CommonsLog", "lombok.extern.apachecommons");
        packageMap.put("@Log", "lombok.extern.java");
        packageMap.put("@Log4j", "lombok.extern.log4j");
        packageMap.put("@Log4j2", "lombok.extern.log4j");
        packageMap.put("@Slf4j", "lombok.extern.slf4j");
        packageMap.put("@XSlf4j", "lombok.extern.slf4j");
    }
}
