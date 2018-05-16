package idv.jindotw.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

public class ExerciseCollUtil {

    private HashMap<Integer, Exercise> maps;
    private int minId = Integer.MAX_VALUE;
    private int maxId = Integer.MIN_VALUE;

    private ExerciseCollUtil() {
        maps = new HashMap<>();
    }

    public int getMinId() {
        return minId;
    }

    private void setMinId(int minId) {
        this.minId = minId;
    }

    public int getMaxId() {
        return maxId;
    }

    private void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public Exercise getExercise(int no) {
        return maps.get(no);
    }

    public int getNumOfExercise() {
        return maps.size();
    }

    public static ExerciseCollUtil getInstance(String packageName) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, ExerciseIdException {
        Class<?>[] classes = getClasses(packageName);
        ExerciseCollUtil util = new ExerciseCollUtil();

        for (Class<?> cls : classes) {
            if (Exercise.class.isAssignableFrom(cls) && cls != Exercise.class) {
                Exercise ex = cls.asSubclass(Exercise.class).newInstance();
                int id = ex.getId();
                if (util.maps.containsKey(id)) {
                    throw new ExerciseIdException(ex.getClass().getName() + " is in conflict with " + util.maps.get(id).getClass().getName() + " (both having no #" + id + ")");
                }
                util.maps.put(id, ex);

                if (id > util.getMaxId()) {
                    util.setMaxId(id);
                }
                if (id < util.getMinId()) {
                    util.setMinId(id);
                }
                System.out.println("Added " + ex.getClass().getCanonicalName());
            }
        }

        return util;
    }

    private static Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert (classLoader != null);
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return (Class[]) classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
