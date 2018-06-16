public class AIPoint {
    public Integer x, y;

    public AIPoint(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }
    public AIPoint() {
        this.x = null;
        this.y = null;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    //    public Integer x(){
//        return x;
//    }
//    public Integer y(){
//        return y;
//    }
//    public void x(Integer x) {
//        this.x = x;
//    }
//    public void y(Integer y) {
//        this.y = y;
//    }
}
