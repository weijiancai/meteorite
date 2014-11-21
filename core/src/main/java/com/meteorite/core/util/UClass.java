package com.meteorite.core.util;

import com.meteorite.core.datasource.DataMap;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class工具类
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class UClass {
    private static final Logger log = Logger.getLogger(UClass.class);

    /**
     * 判定指定的 Class 对象是否表示一个基本类型。
     * 有九种预定义的 Class 对象，表示八个基本类型和 void。这些类对象由 Java 虚拟机创建，与其表示的基本类型同名，即 boolean、byte、char、short、int、long、float 和 double。
     * 另外再加上String
     *
     * @param clazz Class 对象
     * @return 返回此Class 对象是否是一个基本类型
     */
    public static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == String.class;
    }

    /**
     * 将类对象转化为字符串
     *
     * @param clazz 类对象
     * @return 返回类对象的字符串
     */
    public static String toString(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append(clazz.getName()).append("\r\n");
        sb.append("Fields:\r\n");
        for (Field field : clazz.getDeclaredFields()) {
            sb.append("    ").append(field.getName()).append("\r\n");
        }
        sb.append("Methods:\r\n");
        for (Method method : clazz.getDeclaredMethods()) {
            sb.append("    ").append(method.getName()).append("\r\n");
        }
        return sb.toString();
    }

    /**
     * 从包package中获取所有的Class
     *
     * @param pack
     * @return
     */
    public static Set<Class<?>> getClasses(String pack) {

        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(
                    packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    System.err.println("file类型的扫描");
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath,
                            recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    System.err.println("jar类型的扫描");
                    JarFile jar;
                    try {
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection())
                                .getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx)
                                            .replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class")
                                            && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(
                                                packageName.length() + 1, name
                                                        .length() - 6);
                                        try {
                                            // 添加到classes
                                            classes.add(Class
                                                    .forName(packageName + '.'
                                                            + className));
                                        } catch (ClassNotFoundException e) {
                                            // log
                                            // .error("添加用户自定义视图类错误 找不到此类的.class文件");
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        // log.error("在扫描用户定义视图时从jar包获取文件出错");
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName,
                                                        String packagePath, final boolean recursive, Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "."
                                + file.getName(), file.getAbsolutePath(), recursive,
                        classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    //classes.add(Class.forName(packageName + '.' + className));
                    //经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取类类型的所有Field包括父类中的Field
     *
     * @param clazz 类类型
     * @return 返回类类型的所有Field包括父类中的Field
     */
    public static Field[] getAllFields(Class clazz) {
        if (!clazz.getName().startsWith("com.fly")) {
            return new Field[0];
        }
        Map<String, Field> map = new HashMap<String, Field>();
        for (Field field : clazz.getDeclaredFields()) {
            map.put(field.getName(), field);
        }
        while (clazz.getSuperclass() != null) {
            clazz = clazz.getSuperclass();
            if (clazz == Object.class) {
                break;
            }

            for (Field field : clazz.getDeclaredFields()) {
                if (!map.containsKey(field.getName())) {
                    map.put(field.getName(), field);
                }
            }
        }
        return map.values().toArray(new Field[map.size()]);
    }

    /**
     * 将DataMap转换为类实例对象
     *
     * @param clazz 类
     * @param data DataMap
     * @return 返回类实例对象
     */
    public static <T> T convert(Class<T> clazz, DataMap data) {
        T t;
        try {
            t = clazz.newInstance();
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().startsWith("set")) {
                    String fieldName = method.getName().substring(3);
                    Object value = data.get(fieldName);
                    if (method.getParameterCount() == 1) {
                        Class<?> parameterType = method.getParameterTypes()[0];
                        if (parameterType == boolean.class) {
                            fieldName = "is" + fieldName;
                            value = UString.toBoolean(String.valueOf(data.get(fieldName)));
                        } else if (parameterType == Date.class && value instanceof String) {
                            value = UDate.toDate(UString.toString(value));
                        }
                    }

                    if (value != null) {
                        log.debug(method.getName() + " = " + value);
                        method.invoke(t, value);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("将DataMap转换为%s失败！", clazz.getName()), e);
        }

        return t;
    }

    /**
     * 根据类字段名，获得setter方法名
     *
     * @param fieldName 类字段名
     * @return 返回setter方法名
     */
    public static String getSetMethodName(String fieldName) {
        if (fieldName.startsWith("is")) {
            return "set" + fieldName.substring(2);
        } else {
            return "set" + UString.firstCharToUpper(fieldName);
        }
    }
}
