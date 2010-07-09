package template;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class ApplicationConfig {

    private static DrymlConfiguration drymlConfiguration;

    public static void init() {
        drymlConfiguration = new DrymlConfiguration();
        drymlConfiguration.setClassResolver(new MyClassResolver());
        //DrymlTemplateHandler templateHandler = drymlConfiguration.getDrymlTemplateHandler();
    }

    public static DrymlConfiguration getDrymlConfiguration() {
        return drymlConfiguration;
    }
}
