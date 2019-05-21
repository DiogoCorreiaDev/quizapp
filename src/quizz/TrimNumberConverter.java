package quizz;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("quizz.TrimNumberConverter")
public class TrimNumberConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        String trimmed = (submittedValue != null) ? submittedValue.trim() : null;       
        
        if (trimmed.length() < 1 || trimmed == null || trimmed.isEmpty()) {
        	return null;
        } else {
        	try {
        		int parsed = Integer.parseInt(trimmed);
        		if (parsed>0 && parsed<11) {
                	return trimmed;
                } else {
                	return null;
                }
        	} catch (Exception e) {
        		return null;
        	}
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        return (modelValue != null) ? modelValue.toString() : "";
    }

}