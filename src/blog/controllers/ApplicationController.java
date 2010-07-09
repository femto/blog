package blog.controllers;

import com.scooterframework.common.logging.LogUtil;
import com.scooterframework.web.config.WebConfig;
import com.scooterframework.web.controller.ActionControl;
import template.ApplicationConfig;

/**
 * ApplicationController class has methods that are available to all subclass
 * controllers. This is a place to add application-wide action methods and filters.
 */
public class ApplicationController extends ActionControl {

    public void registerFilters() {
        //beforeFilter("validateInput", "only", "authenticate");
        beforeFilter("applicationInit");
    }

    public void applicationInit() {
        System.out.println("initing...");
        ApplicationConfig.init();
    }

    //
    // Add more application-wide methods/filters here.
    //

    /**
     * Declares a <tt>log</tt> instance that are available to all subclasses.
     */
    protected LogUtil log = LogUtil.getLogger(getClass().getName());

    protected String getViewURI(String controller, String action) {
        String uri = WebConfig.getViewURI(controller, action, getDefaultViewFilesDirectoryName());
        return uri;
    }

    /**
     * Returns default view file directory name.
     *
     * @return default view file directory name.
     */
    protected String getDefaultViewFilesDirectoryName() {
        return (WebConfig.getInstance().allowAutoCRUD()) ?
                WebConfig.getInstance().getDefaultViewFilesDirectory() : null;
    }

    public String drymlHandle(String controller, String action) {
        ApplicationConfig.init();
        StringBuilder result = new StringBuilder();
        String uri = getViewURI(controller, action);
        uri = WebConfig.getRealPath() + uri;

        ApplicationConfig.getDrymlConfiguration().getDrymlTemplateHandler().handle(uri, result, null);

        return html(result.toString());
    }

}
