package WordEditing;

import Word.WordPhrase;

public class PhraseNode extends WordSceneNode {
    private WordPhrase content;

    public WordPhrase getContent() {
        return content;
    }

    public void setContent(WordPhrase content) {
        this.content = content;
    }

    public PhraseNode() {
        super("Phrase");
        setOptions();
    }

    @Override
    protected void setOptions() {
        options.getOptions().getItems().addAll(options.getConnect(),options.getDelete(),options.getAddDef());
    }

    @Override
    public void addChild(WordSceneNode wordSceneNode) {

    }
}
