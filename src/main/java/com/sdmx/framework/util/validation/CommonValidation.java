package com.sdmx.framework.util.validation;


public class CommonValidation {/*

    private static List<String> toBeValidate = FastList.newInstance();

    static {
        toBeValidate.add(ValidationType.REQUIRED);
        toBeValidate.add(ValidationType.PATTERN);
        toBeValidate.add(ValidationType.RANGE);
        toBeValidate.add(ValidationType.LENGTH);
    }

    public static List<ValidationMessage> getValidation(String entityName) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Class<?> clazz = Class.forName(entityName);
        List<ValidationMessage> messages = FastList.newInstance();
        Method[] classMethods = clazz.getMethods();
        for (Method classMethod : classMethods) {
            String classMethodName = classMethod.getName();
            Annotation[] annotations = classMethod.getAnnotations();
            for (Annotation annotation : annotations) {
                String annotationName = annotation.annotationType().getSimpleName();
                if (toBeValidate.contains(annotationName)) {
                    ValidationMessage validationMessage = new ValidationMessage();
                    validationMessage.setFieldName(RefelctHelper.getFieldName(classMethodName));
                    validationMessage.setType(annotationName);
                    Method[] methods = annotation.annotationType().getMethods();
                    for (Method annotationMethod : methods) {
                        String methodName = annotationMethod.getName();
                        if (methodName.contains("regexp")) {
                            String regexp = annotationMethod.invoke(annotation).toString();
                            validationMessage.setRegexp(regexp);
                        }
                        if (methodName.contains("message")) {
                            String message = annotationMethod.invoke(annotation).toString();
                            validationMessage.setMessage(message);
                        }
                        if (methodName.contains("max")) {
                            int max = Integer.valueOf(annotationMethod.invoke(annotation).toString());
                            validationMessage.setMax(max);
                        }
                        if (methodName.contains("min")) {
                            int min = Integer.valueOf(annotationMethod.invoke(annotation).toString());
                            validationMessage.setMin(min);
                        }
                    }
                    messages.add(validationMessage);
                }
            }
        }
        return messages;
    }
*/}
