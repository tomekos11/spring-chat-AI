package ts.myapp.validation;

import org.springframework.validation.BindingResult;

public class Validation {
    public static String checkValidation(BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("NIE ZAPISANO");
            return result.getFieldErrors().get(0).getDefaultMessage();
        }
        return null;
    }
}
