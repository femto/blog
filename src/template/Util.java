package template;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class Util {
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
        return "<link href=\"/blog/static/stylesheets/"+ arg +".css\" rel=\"stylesheet\" type=\"text/css\"/>";
    }
}
