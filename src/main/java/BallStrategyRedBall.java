public class BallStrategyRedBall implements BallPocketStrategy{
    public void think(Table t, Ball b) {
        b.setEnabled(false);
    }
}
