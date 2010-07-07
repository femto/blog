package template;

import ognl.ClassResolver;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class DrymlConfiguration {
    private ClassResolver classResolver;


    public ClassResolver getClassResolver() {
        return classResolver;
    }

    public void setClassResolver(ClassResolver classResolver) {
        this.classResolver = classResolver;
    }

    public static void main(String[] args) {
        DrymlConfiguration configuration = new DrymlConfiguration();
        configuration.setClassResolver(new MyClassResolver());
        DrymlTemplateHandler templateHandler = configuration.getDrymlTemplateHandler();

        StringBuilder result = new StringBuilder();
        templateHandler.handle("webapps\\blog\\WEB-INF\\views\\welcome\\sayit.dryml", result);
        System.out.println(result.toString());
    }

    public DrymlTemplateHandler getDrymlTemplateHandler() {
        DrymlTemplateHandler drymlTemplateHandler = new DrymlTemplateHandler(this);

        return drymlTemplateHandler;
    }
}
