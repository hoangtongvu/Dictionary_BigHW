

public class WordExample 
{
    private String example;
    private String exampleDefinition;

    public WordExample(String ex, String def)
    {
        this.example = ex;
        this.exampleDefinition = def;
    }

    public void PrintOut()
    {
        // System.out.println("example: " + this.example);
        // System.out.println("exampleDef: " + this.exampleDefinition);
        System.out.println("\t" + this.example);
        System.out.println("\t->" + this.exampleDefinition);
    }
}
