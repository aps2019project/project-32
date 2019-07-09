package client.controller;

import com.jniwrapper.Str;

import java.io.Serializable;

public class AnimationGIFSPack implements Serializable
{
    private String deathPhotoAddress;
    private String attackPhotoAddress;
    private String movePhotoAddress;
    private String breathingPhotoAddress;
    private String idlePhotoAddress;
    private String spawnPhotoAddress;
    private String deathSoundAddress;
    private String attackSoundAddress;
    private String moveSoundAddress;
    private String spawnSoundAddress;
    private String hitSoundAddress;
    private String impactSoundAddress;

    public AnimationGIFSPack(String breathingPhotoAddress, String deathPhotoAddress, String attackPhotoAddress, String movePhotoAddress, String idlePhotoAddress, String spawnPhotoAddress, String deathSoundAddress, String attackSoundAddress, String moveSoundAddress, String spawnSoundAddress, String hitSoundAddress, String impactSoundAddress)
    {
        this.attackPhotoAddress = attackPhotoAddress;
        this.movePhotoAddress = movePhotoAddress;
        this.breathingPhotoAddress = breathingPhotoAddress;
        this.spawnPhotoAddress = spawnPhotoAddress;
        this.deathSoundAddress = deathSoundAddress;
        this.attackSoundAddress = attackSoundAddress;
        this.moveSoundAddress = moveSoundAddress;
        this.spawnSoundAddress = spawnSoundAddress;
        this.hitSoundAddress = hitSoundAddress;
    }

    public AnimationGIFSPack(String breathingPhotoAddress)
    {
        this.breathingPhotoAddress = breathingPhotoAddress;
    }

    public AnimationGIFSPack(String breathingPhotoAddress, String attackPhotoAddress)
    {
        this.breathingPhotoAddress = breathingPhotoAddress;
        this.attackPhotoAddress = attackPhotoAddress;
    }

    public String getDeathPhotoAddress()
    {
        return deathPhotoAddress;
    }

    public void setDeathPhotoAddress(String deathPhotoAddress)
    {
        this.deathPhotoAddress = deathPhotoAddress;
    }

    public String getAttackPhotoAddress()
    {
        return attackPhotoAddress;
    }

    public void setAttackPhotoAddress(String attackPhotoAddress)
    {
        this.attackPhotoAddress = attackPhotoAddress;
    }

    public String getMovePhotoAddress()
    {
        return movePhotoAddress;
    }

    public void setMovePhotoAddress(String movePhotoAddress)
    {
        this.movePhotoAddress = movePhotoAddress;
    }

    public String getBreathingPhotoAddress()
    {
        return breathingPhotoAddress;
    }

    public void setBreathingPhotoAddress(String breathingPhotoAddress)
    {
        this.breathingPhotoAddress = breathingPhotoAddress;
    }

    public String getIdlePhotoAddress()
    {
        return idlePhotoAddress;
    }

    public void setIdlePhotoAddress(String idlePhotoAddress)
    {
        this.idlePhotoAddress = idlePhotoAddress;
    }

    public String getSpawnPhotoAddress()
    {
        return spawnPhotoAddress;
    }

    public void setSpawnPhotoAddress(String spawnPhotoAddress)
    {
        this.spawnPhotoAddress = spawnPhotoAddress;
    }

    public String getDeathSoundAddress()
    {
        return deathSoundAddress;
    }

    public void setDeathSoundAddress(String deathSoundAddress)
    {
        this.deathSoundAddress = deathSoundAddress;
    }

    public String getAttackSoundAddress()
    {
        return attackSoundAddress;
    }

    public void setAttackSoundAddress(String attackSoundAddress)
    {
        this.attackSoundAddress = attackSoundAddress;
    }

    public String getMoveSoundAddress()
    {
        return moveSoundAddress;
    }

    public void setMoveSoundAddress(String moveSoundAddress)
    {
        this.moveSoundAddress = moveSoundAddress;
    }

    public String getSpawnSoundAddress()
    {
        return spawnSoundAddress;
    }

    public void setSpawnSoundAddress(String spawnSoundAddress)
    {
        this.spawnSoundAddress = spawnSoundAddress;
    }

    public String getHitSoundAddress()
    {
        return hitSoundAddress;
    }

    public void setHitSoundAddress(String hitSoundAddress)
    {
        this.hitSoundAddress = hitSoundAddress;
    }

    public String getImpactSoundAddress()
    {
        return impactSoundAddress;
    }

    public void setImpactSoundAddress(String impactSoundAddress)
    {
        this.impactSoundAddress = impactSoundAddress;
    }
}
