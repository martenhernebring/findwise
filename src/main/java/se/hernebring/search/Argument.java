package se.hernebring.search;

public class Argument {

  public static final String USAGE = "Usage: java -jar " +
    "target/search-0.0.1-SNAPSHOT.jar default OR \"arg1\" \"arg2\" ...";

  public static final String MULTIPLE =
    " (must contain several documents containing non-white space)";

  public static void verify(String[] args) {
    if(args == null || args.length < 1)
      throw new IllegalArgumentException(USAGE);
    else if (args[0].equals("default")) {
      //use default values
    } else
      mustContainTwoTexts(args);
  }

  private static void mustContainTwoTexts(String[] args) {
    boolean first = true;
    boolean second = false;
    for(String arg: args) {
      if (first)
        first = arg.isBlank();
      else if (!second)
        second = !arg.isBlank();
      else
        break;
    }
    if(!second)
      throw new IllegalArgumentException(USAGE + MULTIPLE);
  }
}
