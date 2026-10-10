package entities;

import enums.CardStatus;
import entities.user.Student;

public class Card {
    private int cardId;
    private String cardUid;
    private CardStatus status;
    private Student student;

    public Card(int cardId, String cardUid, Student student) {
        this.cardId = cardId;
        this.cardUid = cardUid;
        this.student = student;
        this.status = CardStatus.ACTIVE;
    }

    public int getCardId() {
        return cardId;
    }

    public String getCardUid() {
        return cardUid;
    }

    public CardStatus getStatus() {
        return status;
    }

    public Student getStudent() {
        return student;
    }

    public boolean isActive() {
        return status == CardStatus.ACTIVE;
    }

    public void markLost(){
        status = CardStatus.LOST;
    }

    public void markReplaced(){
        status = CardStatus.REPLACED;
    }

    public void markExpired(){
        status = CardStatus.EXPIRED;
    }


}
