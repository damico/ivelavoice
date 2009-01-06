/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.ivela.voice.teacher;

import java.util.ArrayList;
import java.util.List;

/**
 *An Exercise is a set of Phrase objects
 * @author jefferson
 */
public class Exercise {

    private List<Phrase> phrases;
    private String exerciseTitle;
    private String confJulianFile;
    private int number;

    public Exercise() {
        this.phrases = new ArrayList<Phrase>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getExerciseTitle() {
        return exerciseTitle;
    }

    public void setExerciseTitle(String exerciseTitle) {
        this.exerciseTitle = exerciseTitle;
    }

    public void addPhrase(Phrase phrase) {
        this.phrases.add(phrase);

    }

    public List<Phrase> getPhrases() {
        return phrases;
    }

    public String getConfJulianFile() {
        return confJulianFile;
    }

    public void setConfJulianFile(String confJulianFile) {
        this.confJulianFile = confJulianFile;
    }
}
