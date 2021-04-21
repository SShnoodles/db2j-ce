package cc.ssnoodles.db.util;

import cc.ssnoodles.db.domain.ClassContext;
import cc.ssnoodles.db.constant.TemplateType;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.*;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

/**
 * 模板引擎工具
 * @author ssnoodles
 * @version 1.0
 * Create at 2020/1/31 19:10
 */
public class VelocityUtil {

    /**
     * velocity配置
     */
    private static final Properties INIT_PROP;

    static {
        INIT_PROP = new Properties();
        INIT_PROP.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, NullLogChute.class.getName());
        INIT_PROP.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
        INIT_PROP.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
        INIT_PROP.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
    }

    /**
     * 渲染模板
     *
     * @param template 模板字符串
     * @param classContext   参数集合
     * @return 渲染结果
     */
    public static String generate(String template, ClassContext classContext) {
        final ClassLoader oldContextClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(VelocityUtil.class.getClassLoader());
        StringWriter outWriter = new StringWriter();
        try {
            // 每次创建一个新实例，防止velocity缓存宏定义
            VelocityEngine velocityEngine = new VelocityEngine(INIT_PROP);
            velocityEngine.init();
            // 创建上下文对象
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("vm", classContext);
            // 生成代码
            velocityEngine.evaluate(velocityContext, outWriter, "Velocity Code Generate", template);
        } catch (Exception e) {
            // 将异常全部捕获，直接返回，用于写入模板
            StringBuilder builder = new StringBuilder("When generating code, the template has error：\n");
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            builder.append(writer.toString());
            return builder.toString().replace("\r", "");
        } finally {
            Thread.currentThread().setContextClassLoader(oldContextClassLoader);
        }
        // 返回结果
        return outWriter.toString();
    }

    public static String generateFromTemplate(String template, ClassContext classContext) {
        // 每次创建一个新实例，防止velocity缓存宏定义
        VelocityEngine velocityEngine = new VelocityEngine(INIT_PROP);
        velocityEngine.init();
        // 创建上下文对象
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("vm", classContext);
        StringWriter outWriter = new StringWriter();
        try {
            // 生成代码
            velocityEngine.evaluate(velocityContext, outWriter, "Velocity Code Generate", template);
        } catch (Exception e) {
            // 将异常全部捕获，直接返回，用于写入模板
            StringBuilder builder = new StringBuilder("When generating code, the template has error：\n");
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            builder.append(writer.toString());
            return builder.toString().replace("\r", "");
        }
        // 返回结果
        return outWriter.toString();
    }

    /**
     * 渲染模板
     *
     * @param templateType 默认模板类型
     * @param classContext   参数集合
     * @return 渲染结果
     */
    public static String generate(TemplateType templateType, ClassContext classContext) {
        // 每次创建一个新实例，防止velocity缓存宏定义
        VelocityEngine velocityEngine = new VelocityEngine(INIT_PROP);
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        // 创建上下文对象
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("vm", classContext);
        StringWriter outWriter = new StringWriter();
        // 获取模板文件
        Template template = velocityEngine.getTemplate("templates/" + templateType.getType() + ".vm");
        try {
            // 生成代码
            template.merge(velocityContext, outWriter);
        } catch (Exception e) {
            // 将异常全部捕获，直接返回，用于写入模板
            StringBuilder builder = new StringBuilder("When generating code, the template has error：\n");
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            builder.append(writer.toString());
            return builder.toString().replace("\r", "");
        }
        // 返回结果
        return outWriter.toString();
    }
}
