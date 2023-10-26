package Word;


public class WordExample 
{
    private String prefixSymbol = "➥ ";
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

    public String GetInfo(String prefixSpace)
    {
        String temp = "<h3><pre>" + prefixSpace + this.example + "\n" + "</pre></h3>";
        temp += "<h4><pre>" + prefixSpace + this.prefixSymbol + this.exampleDefinition + "</pre><h4>";
        return temp;
    }
}
