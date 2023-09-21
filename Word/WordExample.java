package Word;


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
        System.out.println("\t" + this.example);
        System.out.println("\t->" + this.exampleDefinition);
    }

    public String GetInfo()
    {
        String temp = "\t" + this.example + "\n\t->" + this.exampleDefinition;
        return temp;
    }
}
