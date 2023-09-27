package edu.whu;

import edu.whu.framework.BootstrapException;
import edu.whu.framework.BootstrapService;

public class Application {
    public static void main(String[] args) {
        try {
            BootstrapService.start("/myapp.properties");
        } catch (BootstrapException e) {
            System.out.println(e.getErrorType()+":"+e.getMessage());
        }
    }

}