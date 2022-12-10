package me.tapeline.hummingbird.expansions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader {
    public static Plugin loadFromJar(File file, String mainClass) throws IOException,
            ClassNotFoundException, InstantiationException, IllegalAccessException {
        JarFile jarFile = new JarFile(file);
        Enumeration<JarEntry> e = jarFile.entries();
        URL[] urls = new URL[] { new URL("jar:file:" + String.valueOf(file) + "!/") };
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = (JarEntry) e.nextElement();
            if (!je.isDirectory() && je.getName().endsWith(".class")) {
                String className = je.getName().substring(0, je.getName().length() - 6);
                className = className.replace('/', '.');
                Class c = cl.loadClass(className);
                if (className.equals(mainClass)) {
                    return (Plugin)c.newInstance();
                }

                if (Plugin.class.isAssignableFrom(c)) {
                    return (Plugin)c.newInstance();
                }
            }
        }

        return null;
    }
}
