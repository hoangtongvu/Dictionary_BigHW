package Word;


public class WordExample 
{
    private String example;
    private String exampleDefinition;

    public void setExampleDefinition(String exampleDefinition) {
        this.exampleDefinition = exampleDefinition;
    }

    public WordExample(String ex, String def)
    {
        this.example = ex;
        this.exampleDefinition = def;
    }

    public String GetInfo()
    {
        String temp = "\t" + this.example + "\n\t->" + this.exampleDefinition;
        return temp;
    }
}
