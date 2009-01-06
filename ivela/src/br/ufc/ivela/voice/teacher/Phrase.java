/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.ivela.voice.teacher;

import java.util.ArrayList;
import java.util.List;

/**
 * A phrase is a set of Word objects.
 * @author jefferson
 */
public class Phrase extends Text {

    private List<Word> words;
    private char endChar; //. , : ; -
    

    public Phrase() {
        this.words = new ArrayList<Word>(5);
        this.setContent("");
    }

    

    public char getEndChar() {
        return endChar;
    }

    public void setEndChar(char endChar) {
        this.endChar = endChar;
        this.setContent(this.getContent() + endChar);
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> content) {
        this.words = content;
    }

    public void addWord(Word word) {
        this.words.add(word);
        String temp = this.getContent();
        temp += " " + word.getContent();
        this.setContent(temp);
    }

    
    
}
