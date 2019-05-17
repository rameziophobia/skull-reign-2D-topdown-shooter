package model.enemies;


public enum ProjectileControlType {

    pulse200rx120a(200,120,0,0,0),
    pulse400rx90a(400,90,0,0,0),
    pulse300rx90a(300,90,0,0,0),
    pulse200rx90a(200,90,0,0,0),
    pulse300rx60a(300,60,0,0,0),
    pulse500rx60a(500,60,0,0,0),
    pulse400rx45a(400,45,0,0,0),
    toPlayer100(0,0,100,0,0),
    toPlayer200(0,0,200,0,0),
    toPlayer300(0,0,300,0,0),
    toPlayer400(0,0,400,0,0),
    toPlayer500(0,0,500,0,0),
    toPlayer1000(0,0,1000,0,0),
    ring50rx5a(0,0,0,50,5),
    ring100rx5a(0,0,0,100,5),
    ring200rx5a(0,0,0,200,5),
    ring300rx5a(0,0,0,300,5),
    ;

    ProjectileControlType(long pulseRate, float pulseAngle, long toPlayerRate, long ring1by1Rate, float ring1by1Angle) {
        this.pulseRate = pulseRate;
        this.pulseAngle = pulseAngle;
        this.toPlayerRate = toPlayerRate;
        this.ring1by1Rate = ring1by1Rate;
        this.ring1by1Angle = ring1by1Angle;
    }

    private long pulseRate;
    private float pulseAngle;
    private long toPlayerRate;
    private long ring1by1Rate;
    private float ring1by1Angle;


    public long getPulseRate() {
        return pulseRate;
    }

    public float getPulseAngle() {
        return pulseAngle;
    }

    public long getToPlayerRate() {
        return toPlayerRate;
    }

    public long getRing1by1Rate() {
        return ring1by1Rate;
    }

    public float getRing1by1Angle() {
        return ring1by1Angle;
    }
}
