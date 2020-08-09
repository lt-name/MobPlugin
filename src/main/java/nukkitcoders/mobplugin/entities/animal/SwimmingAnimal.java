package nukkitcoders.mobplugin.entities.animal;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.data.ShortEntityData;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.SwimmingEntity;

public abstract class SwimmingAnimal extends SwimmingEntity implements Animal {

    public SwimmingAnimal(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.route = null;
    }

    @Override
    public double getSpeed() {
        return 0.8;
    }

    @Override
    protected void initEntity() {
        super.initEntity();
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if (this.closed) {
            return false;
        }
        if (!this.isAlive()) {
            if (++this.deadTicks >= 23) {
                this.close();
                return false;
            }
            return true;
        }

        if (!MobPlugin.throttle && Server.getInstance().getTick() % 2 == 0) {
            int tickDiff = currentTick - this.lastUpdate;
            this.lastUpdate = currentTick;
            this.entityBaseTick(tickDiff);

            Vector3 target = this.updateMove(tickDiff);
            if (target instanceof Player) {
                if (this.distanceSquared(target) <= 2) {
                    this.pitch = 22;
                    this.x = this.lastX;
                    this.y = this.lastY;
                    this.z = this.lastZ;
                }
            }
        } else {
            int tickDiff = currentTick - this.lastUpdate;
            this.lastUpdate = currentTick;
            this.entityBaseTick(tickDiff);
        }
        return true;
    }
}
