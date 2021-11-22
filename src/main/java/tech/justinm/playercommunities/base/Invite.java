package tech.justinm.playercommunities.base;

import org.bukkit.entity.Player;

public class Invite {
    private Player sender;
    private Player receiver;
    private Community community;
    private boolean confirmed;
    private boolean expired;

    public Invite(Player sender, Player receiver, Community community) {
        this.sender = sender;
        this.receiver = receiver;
        this.community = community;
        this.confirmed = false;
    }

    public boolean isExpired() {
        return expired;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
