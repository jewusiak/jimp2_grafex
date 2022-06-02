package grafexExceptions;

public class IllegalGraphFormatException extends GraphException{
    private final int line;

    public IllegalGraphFormatException(String message, int line){
        super(message);
        this.line=line;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String getMessage(){
        return super.getMessage()+" "+getLine();
    }

}
