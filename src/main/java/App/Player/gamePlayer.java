package App.Player;

public class gamePlayer {
    private int gold;


    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
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
