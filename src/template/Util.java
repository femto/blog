package template;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class Util {
    private static String baseUrl = "/blog";
    private static String currentTheme = "clean";


    public static String stylesheets(String arg) {
        StringBuilder result = new StringBuilder();
        result.append(_stylesheet(arg));

        return result.toString();
    }

    public static String stylesheets(String[] args) {
        StringBuilder result = new StringBuilder();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                result.append(_stylesheet(arg));
            }
        }
        return result.toString();
    }

    public static String _stylesheet(String arg) {
        //return "<link href=\"<%=request.getContextPath()%>/<%=dirName%>/stylesheets/"+ arg +".css\" rel=\"stylesheet\" type=\"text/css\"/>";
        return "<link href=\"" + baseUrl + "/static/stylesheets/" + arg + ".css\" rel=\"stylesheet\" type=\"text/css\"/>";
    }

    public static boolean isDevelopment() {
//        try {
//            return com.scooterframework.web.config.WebConfig.getInstance().isInDevelopmentEnvironment();
//        } catch (Exception e) {
//            return true;
//        }
        return true;
    }


    public static String themeStylesheets(String name) {
        if (name == null || "".equals(name.trim())) {
            name = currentTheme;
        }
        return "<link href=\"" + baseUrl + "/static/themes/" + name + "/stylesheets/" + name + ".css\" rel=\"stylesheet\" type=\"text/css\"/>";
    }

    public static String javascriptIncludeTag(String tag) {
        StringBuilder result = new StringBuilder();
        String[] tags = tag.split(",");
        for (int i = 0; i < tags.length; i++) {
            String tag1 = tags[i].trim();
            result.append("<script src=\"" + baseUrl + "/static/javascripts/" + tag1 + ".js\" type=\"text/javascript\"></script>");
        }
        return result.toString();
    }


    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        Util.baseUrl = baseUrl;
    }

    public static String getCurrentTheme() {
        return currentTheme;
    }

    public static void setCurrentTheme(String currentTheme) {
        Util.currentTheme = currentTheme;
    }
}
