package br.ufc.ivela.voice.julius;

import br.ufc.ivela.voice.teacher.Exercise;

public interface IJuliusParent {

    public void setMessageOutput(String message);

    //public void setMessageOutputScndChance(String message);

    public boolean isFinished();

    public Exercise getCurrentExercise();
}
