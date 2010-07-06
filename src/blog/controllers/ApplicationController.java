package blog.controllers;

import com.scooterframework.common.logging.LogUtil;
import com.scooterframework.web.config.WebConfig;
import com.scooterframework.web.controller.ActionControl;

/**
 * ApplicationController class has methods that are available to all subclass
 * controllers. This is a place to add application-wide action methods and filters.
 */
public class ApplicationController extends ActionControl {
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
        return (WebConfig.getInstance().allowAutoCRUD())?
                WebConfig.getInstance().getDefaultViewFilesDirectory():null;
    }


}
