/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.ivela.voice.teacher;

/**
 * A word is just a piece of String
 * @author jefferson
 */
public class Word extends Text {

    private String lexicalCategory;
    public static final int JUMP_LINE = 1;

    public Word(String content) {
        this.setContent(content);
    }

    public void setLexicalCategory(String lexicalCategory) {
        this.lexicalCategory = lexicalCategory;
    }

    public String getLexicalCategory() {
        return lexicalCategory;
    }
}
