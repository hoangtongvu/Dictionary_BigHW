package WordEditing;

import Word.WordDefinition;

public class DefinitionNode extends WordSceneNode {
    private WordDefinition content;

    public WordDefinition getContent() {
        return content;
    }

    public void setContent(WordDefinition content) {
        this.content = content;
    }

    public DefinitionNode() {
        super("Definition");
        content = new WordDefinition("<EMPTY>");
        setOptions();
    }

    @Override
    protected void setOptions() {
        options.getOptions().getItems().addAll(options.getConnect(),options.getDelete(),options.getAddEx());
    }

    @Override
    public void addChild(WordSceneNode wordSceneNode) {

    }
}
