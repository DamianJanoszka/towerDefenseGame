package App.Player;

import App.GameSettings.Settings;

public class gamePlayer {
    public final static gamePlayer PLAYER = new gamePlayer();
    private int gold;
    private int waveAmount = Settings.WAVE_AMOUNT;
    private boolean isOver = false;
    private gamePlayer(){ }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getWaveAmount() {
        return waveAmount;
    }

    public void setWaveAmount(int waveAmount) {
        this.waveAmount = waveAmount;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public void consumeWave(){
        this.waveAmount--;
    }

    public void addGold(int amount){
        this.gold += amount;
    }
    public boolean buyCannon(){
        if(getGold()>=400){
            this.gold=this.gold-400;
            return true;
        }
        else
            return false;
    }
}
