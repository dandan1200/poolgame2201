public class BallStrategyBlueBall implements BallPocketStrategy{
    public void think(Table t, Ball b) {
        if (b.sinkTally == 0) {

            b.setCoordinates(b.getOGX(), b.getOGY());
            for(Ball ball: t.getEnabledBallsList()) {
                if (t.checkCollision(b,ball)) {
                    b.sinkTally = 0;
                    b.setEnabled(false);
                    return;
                }
            }
            b.setVelocities(0,0);
            b.sinkTally++;
        } else {
            b.sinkTally = 0;
            b.setEnabled(false);
        }
    }
}
