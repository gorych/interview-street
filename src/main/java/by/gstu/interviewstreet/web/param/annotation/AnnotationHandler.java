package by.gstu.interviewstreet.web.param.annotation;


import java.lang.reflect.Method;

public final class AnnotationHandler {

    private static Range getAnnotationClass(String classFullName, String methodName)
            throws ClassNotFoundException, NoSuchMethodException {
        Class<?> clazz = Class.forName(classFullName);
        Method m = clazz.getMethod(methodName);
        return m.getAnnotation(Range.class);
    }

    public static int getMin(String classFullName, String methodName) {
        final int MIN = 1;
        try {
            Range range = getAnnotationClass(classFullName, methodName);
            return range.min();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            return MIN;
        }
    }

    public static int getMax(String classFullName, String methodName) {
        final int MAX = 255;
        try {
            Range range = getAnnotationClass(classFullName, methodName);
            return range.max();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            return MAX;
        }
    }

    private AnnotationHandler() {
    }

}
