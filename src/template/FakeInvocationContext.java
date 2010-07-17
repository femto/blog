package template;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class FakeInvocationContext extends InvocationContext {

    private InvocationContext delegate;
    private LocalVariables local_variables; //adding some var to delegate

    public FakeInvocationContext(InvocationContext delegate) {
        this.delegate = delegate;
        local_variables = new LocalVariables(delegate.getLocal_variables());

    }

    public LocalVariables getLocal_variables() {
        return local_variables;
    }


}
