/**
* @author Fiedietsov V.
* @version 1.0
*/
package ua.nure.fiedietsov.payments;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
/**
* Support class to create and use custom JSTL tag.
* It get boolean flag
* @author Fiedietsov V.
* @version 1.0
* @return warning colored message.
*/
public class TagLock extends TagSupport{
	private static final long serialVersionUID = 1L;
	private boolean value;
	
	public void setValue(boolean value) {
        this.value = value;
    }	
	@Override
    public int doStartTag() throws JspException {
        try {
            if (this.value == true) pageContext.getOut().print("<font color='red'>You can not make transaction</font>");

            
        } catch(IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }       
        return SKIP_BODY;
    }
}
