package controller.json.level;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import model.enemies.Enemy;
import model.enemies.EnemyType;
import model.enemies.ProjectileControlType;
import model.projectiles.ProjectileType;

public class EnemyData {
    @SerializedName("EnemyType")
    @Expose
    private EnemyType enemyType;
    @SerializedName("ProjectileType")
    @Expose
    private ProjectileType projectileType;
    @SerializedName("MoveMode")
    @Expose
    private Enemy.MoveMode moveMode;
    @SerializedName("ProjectileControlType")
    @Expose
    private ProjectileControlType projectileControlType;

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    public ProjectileType getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(ProjectileType projectileType) {
        this.projectileType = projectileType;
    }

    public Enemy.MoveMode getMoveMode() {
        return moveMode;
    }

    public void setMoveMode(Enemy.MoveMode moveMode) {
        this.moveMode = moveMode;
    }

    public void setProjectileControlType(ProjectileControlType projectileControlType) {
        this.projectileControlType = projectileControlType;
    }

    public ProjectileControlType getProjectileControlType() {
        return projectileControlType;
    }
}
