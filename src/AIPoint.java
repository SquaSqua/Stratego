public class AIPoint {
    private Integer x, y;

    public AIPoint(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    public Integer x(){
        return x;
    }
    public Integer y(){
        return y;
    }
    public void x(Integer x) {
        this.x = x;
    }
    public void y(Integer y) {
        this.y = y;
    }
}
