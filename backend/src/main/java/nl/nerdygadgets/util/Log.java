package nl.nerdygadgets.util;

import nl.nerdygadgets.Main;

import java.util.logging.Logger;

public class Log {

    /*
    Heel simpel, we kunnen hiermee informatie van wat er gebeurd loggen.
    Voor meer info zoek op "Java logging"

    Gebruik dit in plaats van System.out.println();
     */
    public static Logger logger = Logger.getLogger(Main.class.getName());
}
